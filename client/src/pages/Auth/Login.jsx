import React, { useState, useEffect } from 'react'
import { useDispatch } from 'react-redux'

import { FormContainer, TextInput, Button, ChangeScreenButton, ErrorMessage, Line } from './styles'

import { userChanged } from '../../store/actions'

import { accountService, storageService } from '../../services'

export function Login({ onChangeMode }) {


    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [errorMessage, setErrorMessage] = useState('')

    const dispatch = useDispatch()

    useEffect(() => setErrorMessage(''), [email, password])

    async function login() {
        try {
            const result = await accountService.login(email, password)
            const { token } = result
            storageService.setToken(token)
            const data = await accountService.getUserData()
            console.log(data)
            dispatch(userChanged(data))
        } catch (err) {
            if (typeof (err.toJSON) === 'function' && err.toJSON().status === 401)
                setErrorMessage('Email ou senha inválido.')
            else
                setErrorMessage('Erro desconhecido.')
        }
    }

    return (
        <FormContainer>
            <TextInput placeholder='Email' value={email} onChange={e => setEmail(e.target.value)} />
            <TextInput placeholder='Senha' type="password" value={password} onChange={e => setPassword(e.target.value)} />
            <Button onClick={() => login()}>Entrar</Button>
            <ErrorMessage>{errorMessage}</ErrorMessage>
            <Line />
            <ChangeScreenButton onClick={() => onChangeMode()}>Criar Conta</ChangeScreenButton>
        </FormContainer>
    )
}