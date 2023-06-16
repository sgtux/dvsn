import React from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { userChanged, menuChanged } from '../../store/actions'
import { usuarioService } from '../../services'
import { MenuStates } from '../../utils'

import {
    BtnLogout,
    PostProfileImage,
    Container,
    MenuProfile,
    ContainerMenu,
    Line,
    ActionBox,
    BtnMenu,
    MenuItem
} from './styles'

export function Toolbar() {

    const { user, menu } = useSelector(state => state.appState)

    const dispatch = useDispatch()

    function logout() {
        usuarioService.logout()
            .then(() => {
                dispatch(menuChanged(MenuStates.POSTS))
                dispatch(userChanged(null))
            })
    }

    function menuAlterado(novoMenu) {
        if (novoMenu !== menu)
            dispatch(menuChanged(novoMenu))
    }

    return (
        <Container>
            <img src="favicon.ico" height={50} />
            <ActionBox>
                <BtnMenu onClick={() => menuAlterado(MenuStates.USUARIOS)} selected={menu === MenuStates.USUARIOS}>Usuários</BtnMenu>
                <BtnMenu onClick={() => menuAlterado(MenuStates.POSTS)} selected={menu === MenuStates.POSTS}>Posts</BtnMenu>
                {(user || {}).funcao === 1 && <BtnMenu onClick={() => menuAlterado(MenuStates.HASH)} selected={menu === MenuStates.HASH}>Hash</BtnMenu>}
                {(user || {}).funcao === 1 && <BtnMenu onClick={() => menuAlterado(MenuStates.SECURITY_CONFIG)} selected={menu === MenuStates.SECURITY_CONFIG}>Segurança</BtnMenu>}
            </ActionBox>
            <ContainerMenu>
                <PostProfileImage alt="" src={user.foto} />
                <MenuProfile>
                    <MenuItem onClick={() => menuAlterado(MenuStates.EDIT_PROFILE)}>{user.nomeCompleto}</MenuItem>
                    <Line />
                    <MenuItem onClick={() => menuAlterado(MenuStates.CHANGE_PASSWORD)}>alterar senha</MenuItem>
                    <Line />
                    <BtnLogout onClick={() => logout()}>Sair</BtnLogout>
                </MenuProfile>
            </ContainerMenu>
        </Container>
    )
}