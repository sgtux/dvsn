package br.com.dvsn.dtos;

import br.com.dvsn.entities.Usuario;

public class UsuarioLogadoDto {

    private Long id;

    private String nome;

    private String sobrenome;

    private String email;

    private String foto;

    private long funcao;

    private String nomeCompleto;

    private String token;

    public UsuarioLogadoDto() { }

    public UsuarioLogadoDto(Usuario usuario) {
        id = usuario.getId();
        nome = usuario.getNome();
        sobrenome = usuario.getSobrenome();
        email = usuario.getEmail();
        foto = usuario.getFoto();
        funcao = usuario.getFuncao();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeCompleto() {
        return String.format("%s %s", nome, sobrenome);
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public long getFuncao() {
        return funcao;
    }

    public void setFuncao(long funcao) {
        this.funcao = funcao;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
