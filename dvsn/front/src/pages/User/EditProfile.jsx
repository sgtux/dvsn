import { useEffect, useState } from 'react'
import { useSelector, useDispatch } from 'react-redux'

import { usuarioService, storageService } from '../../services'

import { TextInput, ErrorMessage, CustomButton, SuccessMessage } from '../../components'
import { Container, FieldName } from './styles'

import { userChanged } from '../../store/actions'

export function EditProfile() {

    const [nome, setNome] = useState('')
    const [sobrenome, setSobrenome] = useState('')
    const [file, setFile] = useState(null)
    const [errorMessage, setErrorMessage] = useState('')
    const [successMessage, setSuccessMessage] = useState('')

    const { user } = useSelector(state => state.appState)
    const dispatch = useDispatch()

    useEffect(() => {
        setErrorMessage('')
        setSuccessMessage('')
    }, [nome, sobrenome])

    useEffect(() => {
        setNome(user.nome || '')
        setSobrenome(user.sobrenome || '')
    }, [user])

    function save() {
        const formData = new FormData()
        formData.append('nome', nome)
        formData.append('sobrenome', sobrenome)
        formData.append('imagem', file)
        const headers = (storageService.getAuthHeaders() || {}).headers
        fetch(`/usuario/${user.id}`, { method: 'put', body: formData, headers })
            .then(async res => {
                if (res.status === 200) {
                    setSuccessMessage('Salvo com sucesso.')
                    usuarioService.getUserData()
                        .then(res => {
                            const user = res
                            user.token = storageService.getUser().token
                            dispatch(userChanged(user))
                        })
                        .catch(err => console.log(err))
                } else if (res.status === 400) {
                    const json = await res.json()
                    setErrorMessage(json.erro)
                    console.log(json.erro)
                } else {
                    setErrorMessage('Não foi possível salvar o usuário.')
                }
            })
            .catch(err => {
                if (typeof (err.toJSON) === 'function' && err.toJSON().status === 400) {
                    setErrorMessage(err.response.data.erro)
                }
                console.error(err.toJSON)
            })
    }

    return (
        <Container>
            <TextInput style={{ width: 200 }} value={nome} placeholder="Nome" onChange={e => setNome(e.target.value)} />
            <TextInput style={{ width: 200 }} value={sobrenome} placeholder="Sobrenome" onChange={e => setSobrenome(e.target.value)} />
            <div>
                <FieldName>Foto:</FieldName>
                <input type='file' style={{ marginTop: 20 }} onChange={e => setFile(e.target.files[0])} />
            </div>
            <br /><br />
            <CustomButton onClick={() => save()}>SALVAR</CustomButton>
            {errorMessage && <ErrorMessage>{errorMessage}</ErrorMessage>}
            {successMessage && <SuccessMessage>{successMessage}</SuccessMessage>}
        </Container>
    )
}