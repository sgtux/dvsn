import styled from 'styled-components'

export const FooterContainer = styled.div`
    text-align: center;
    font-family: "Roboto","Helvetica","Arial",sans-serif;
    padding: 20px;
    font-size: 12px;
    color: #aaa;
`

export const SearchInput = styled.input`
    background-color: #fff;
    border: none;
    border-radius: 20px 0 0 20px;
    height: 40px;
    padding: 0 20px;
    font-size: 16px;
    margin-top: 4px;
    transition: 200ms;
    &:focus {
        outline: 0;
        box-shadow: 0 0 2px 2px #ddd;
    }
`

export const SearchBtn = styled.button`
    height: 40px;
    border-radius: 0 20px 20px 0;
    border: none;
    transition: 200ms;
    font-size: 16px;
    padding: 6px 12px;
    text-transform: uppercase;
    margin-bottom: 10px;
    &:hover {
        cursor: pointer;
        box-shadow: 0 0 1px 1px #ddd;
        background-color: #ddd;
    }
`

export const TextInput = styled.input`
    border: 1px solid #dddfe2;
    color: #1d2129;
    font-family: Helvetica, Arial, sans-serif;
    font-size: 12px;
    height: 50px;
    line-height: 16px;
    vertical-align: middle;
    border-radius: 6px;
    font-size: 20px;
    margin-bottom: 20px;
    padding-left: 6px;
    width: 100%;
`

export const ErrorMessage = styled.div`
    color: red;
    text-align: center;
    margin-top:10px;
`

export const SuccessMessage = styled.div`
    color: green;
    text-align: center;
    margin-top:10px;
`


export const CustomButton = styled.button`
    background-color: #1877f2;
    width: 100%;
    border: none;
    border-radius: 6px;
    color: white;
    height: 50px;
    font-size: 30px;
    transition: 200ms;
    &:hover {
        opacity: .8;
        cursor: pointer;
    }
`

export const SaveButton = styled(CustomButton)`
    width: 200px;
`

export const ResetButton = styled(CustomButton)`
    width: 200px;
    background-color: #ddd;
    color: #666;
    margin-left: 10px;
    margin-right: 10px;
`