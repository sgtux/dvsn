import { useEffect, useState } from 'react'
import { useSelector, useDispatch } from 'react-redux'

import DOMPurify from 'dompurify'

import { userChanged } from '../../store/actions'

import { postService } from '../../services'
import { SearchBtn, SearchInput } from '../../components'

import {
    PostListContainer,
    PostCard,
    PostCardHeader,
    PostUserBox,
    PostProfileImage,
    PostFooter,
    PostCardBody,
    CommentBox,
    NewCommentBox,
    BtnSendComment,
    NewPostButtonBox,
    BtnRemoveComment,
    BtnRemove,
    Line,
    PostText,
    NewPostButton
} from './styles'

import { NewPostModal } from './NewPostModal/NewPostModal'

export function PostList() {

    const [posts, setPosts] = useState([])
    const [showNewPost, setShowNewPost] = useState(false)
    const [filtro, setFiltro] = useState('')
    const [filtroResult, setFiltroResult] = useState('')
    const [comentarios, setComentarios] = useState({})

    const { user, securityConfig } = useSelector(state => state.appState)

    const dispatch = useDispatch()

    useEffect(() => {
        const qsParam = new URLSearchParams(document.location.search).get('filtro')
        setFiltro(qsParam || '')
        atualizar(qsParam || '')
    }, [])

    function atualizar(qsParam) {
        setShowNewPost(false)
        postService.obterTodos(filtro || qsParam)
            .then(res => {
                setPosts(res)
                setFiltroResult(securityConfig.xssPreventionEnabled ? DOMPurify.sanitize(filtro || qsParam) : filtro)
            })
            .catch(err => {
                if ((err.response || {}).status === 401) {
                    dispatch(userChanged(null))
                    return
                }
                console.log(err)
            })
    }

    function enviarComentario(postId) {
        postService.adicionarComentario({ usuarioId: user.id, postId, texto: comentarios[postId] })
            .then(res => {
                const keyPair = { ...comentarios }
                keyPair[postId] = ''
                setComentarios(keyPair)
                atualizar()
            }).catch(err => console.log(err))

    }

    function deletarComentario(id) {
        postService.deletarComentario(id)
            .then(res => atualizar())
            .catch(err => console.log(err))
    }

    function deletarPost(id) {
        postService.deletarPost(id)
            .then(res => atualizar())
            .catch(err => console.log(err))
    }

    function buscar() {
        window.location.href = `${window.location.href.split('?')[0]}?filtro=${filtro}`
    }

    return (
        <PostListContainer>
            <SearchInput value={filtro} onChange={e => setFiltro(e.target.value)} placeholder='Conteúdo ...' />
            <SearchBtn onClick={() => buscar()}>Buscar</SearchBtn>
            {!!filtroResult && <div>Resultado para: <span dangerouslySetInnerHTML={{ __html: filtroResult }} /></div>}
            {!!posts.length && posts.map(p =>
                <PostCard key={p.id}>
                    <PostCardHeader>
                        <PostUserBox>
                            <PostProfileImage alt="" src={p.usuario.foto} />
                            <div>
                                <h4>{p.usuario.nomeCompleto}</h4>
                                <span>{p.criadoEmFormatado}</span>
                            </div>
                        </PostUserBox>
                        {
                            p.owner &&
                            <div>
                                <BtnRemove onClick={() => deletarPost(p.id)}>
                                    <em className="fa fa-trash"></em>
                                </BtnRemove>
                            </div>
                        }
                    </PostCardHeader>
                    <PostCardBody>
                        <PostText>{p.texto}</PostText>
                        <div>
                            <img style={{ maxWidth: '300px', maxHeight: '300px' }} src={p.foto} alt="" />
                        </div>
                    </PostCardBody>
                    <Line />
                    <PostFooter>
                        Comentários:
                        {!!p.comentarios.length && p.comentarios.map(x =>
                            <CommentBox key={x.id}>
                                <PostProfileImage alt="" src={x.usuario.foto} />
                                <div style={{ marginLeft: 10 }}>
                                    <div>
                                        <span style={{ fontSize: 20, fontWeight: 'bold' }}>{x.usuario.nomeCompleto}</span>
                                        <span style={{ fontSize: 12 }}>{x.criadoEmFormatado}</span>
                                        {x.owner &&
                                            <BtnRemoveComment onClick={() => deletarComentario(x.id)}>
                                                <em className="fa fa-trash"></em>
                                            </BtnRemoveComment>
                                        }
                                    </div>
                                    <div dangerouslySetInnerHTML={{ __html: securityConfig.xssPreventionEnabled ? DOMPurify.sanitize(x.texto) : x.texto }} />
                                </div>
                            </CommentBox>)
                        }
                        <NewCommentBox>
                            <input placeholder="Escreva um comentário..."
                                value={comentarios[p.id] || ''}
                                onChange={e => setComentarios({ ...comentarios, ...{ [p.id]: e.target.value, } })}
                            />
                            <BtnSendComment onClick={() => enviarComentario(p.id)}>Enviar</BtnSendComment>
                        </NewCommentBox>
                    </PostFooter>
                </PostCard>)
            }
            {showNewPost && <NewPostModal onSuccess={() => atualizar()} onClose={() => setShowNewPost(false)} />}
            <NewPostButtonBox>
                <NewPostButton onClick={() => setShowNewPost(true)}>Novo Post</NewPostButton>
            </NewPostButtonBox>
        </PostListContainer >
    )
}