package br.com.dvsn.dtos;

import br.com.dvsn.entities.Usuario;

import java.util.Date;

public class UsuarioDto {

    private Long id;

    private String nome;

    private String sobrenome;

    private String email;

    private String foto;

    private int funcao;

    private String senha;

    private Date criadoEm;

    private Date atualizadoEm;

    public UsuarioDto(){}

    public UsuarioDto(Usuario usuario){
        id = usuario.getId();
        nome = usuario.getNome();
        sobrenome = usuario.getSobrenome();
        email = usuario.getEmail();
        foto = usuario.getFoto();
        funcao = usuario.getFuncao();
        senha = usuario.getSenha();
        criadoEm = usuario.getCriadoEm();
        atualizadoEm = usuario.getAtualizadoEm();
    }

    public UsuarioDto(long id, String nome, String sobrenome) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
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

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getNomeCompleto() {
        return nome + " " + sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Date criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Date getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(Date atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getFuncao() {
        return funcao;
    }

    public void setFuncao(int funcao) {
        this.funcao = funcao;
    }
}
