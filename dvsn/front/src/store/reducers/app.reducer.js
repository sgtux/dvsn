import { MenuStates } from '../../utils'
import { ActionTypes } from '../actions'
import { storageService } from '../../services'

const initialState = {
    user: storageService.getUser(),
    menu: storageService.getCurrentMenu() || MenuStates.POSTS
}

export const appReducer = (state = initialState, action) => {
    switch (action.type) {
        case ActionTypes.USER_CHANGED:
            storageService.setUser(action.payload || null)
            return { ...state, user: action.payload }
        case ActionTypes.MENU_CHANGED:
            storageService.setCurrentMenu(action.payload)
            if (window.location.search !== '')
                window.location.search = ''
            return { ...state, menu: action.payload }
        default:
            return state
    }
}