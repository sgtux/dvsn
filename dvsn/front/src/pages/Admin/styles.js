import styled from 'styled-components'

export const Container = styled.div`
    margin: 0 auto;
    width: 600px;
    margin-top: 60px;
    padding: 20px;
    border-radius: 20px;
    box-shadow: 0 0 5px 5px #eee;
    background-color: #fff;
`

export const UserBox = styled.div`
    margin-top: 20px;
    border-radius: 20px;
    height: 100px;
    box-shadow: 1px 1px 3px 0px #bbb;
    display: flex;
    flex-direction: row;
    background-color: white;
    padding: 4px;
`

export const UserImage = styled.img`
    width: 100px;
    border-radius: 20px;
`

export const FieldName = styled.span`
    font-size: 20px;    
    margin-right: 10px;
`

export const GroupField = styled.fieldset`
    border-radius: 10px;
    border: 2px solid #777;
    margin: 20px;
`

export const FieldBox = styled.div`
    margin-top: 10px;
`