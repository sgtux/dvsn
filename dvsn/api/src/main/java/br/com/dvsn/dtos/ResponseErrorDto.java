package br.com.dvsn.dtos;

public class ResponseErrorDto {

    private String erro;

    public ResponseErrorDto(String erro) {
        this.erro = erro;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }
}
