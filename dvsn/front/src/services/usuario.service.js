import axios from 'axios'

import { storageService } from './storage.service'

const getUserId = () => (storageService.getUser() || {}).id || 0

const getUserData = () => axios.get(`/usuario/${getUserId()}`, storageService.getAuthHeaders()).then(p => p.data)

const getUserLogado = () => axios.get('/usuario', storageService.getAuthHeaders()).then(p => p.data)

const buscar = filtro => axios.get(`/usuarios?filtro=${encodeURI(filtro) || ''}`, storageService.getAuthHeaders()).then(p => p.data)

const login = (email, senha) => axios.post('/auth/login', { email, senha }, storageService.getAuthHeaders()).then(p => p.data)

const create = user => axios.post('/auth/criarConta', user, storageService.getAuthHeaders())

const logout = () => axios.get('/auth/logout', storageService.getAuthHeaders())

export const usuarioService = {
    getUserData,
    buscar,
    login,
    logout,
    create,
    getUserLogado
}