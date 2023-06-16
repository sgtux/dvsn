package br.com.dvsn.dtos;

import br.com.dvsn.entities.Usuario;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UsuarioExibicaoDto {

    private Long id;

    private String nome;

    private String sobrenome;

    private String foto;

    private Date criadoEm;

    private long quantidadePosts;

    public UsuarioExibicaoDto() { }

    public UsuarioExibicaoDto(Usuario usuario) {
        id = usuario.getId();
        nome = usuario.getNome();
        foto = usuario.getFoto();
        sobrenome = usuario.getSobrenome();
        criadoEm = usuario.getCriadoEm();
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Date getCriadoEm() {
        return criadoEm;
    }

    public String getCriadoEmFormatado() {
        return new SimpleDateFormat("dd/MM/yy").format(criadoEm);
    }

    public void setCriadoEm(Date criadoEm) {
        this.criadoEm = criadoEm;
    }

    public long getQuantidadePosts() {
        return quantidadePosts;
    }

    public void setQuantidadePosts(long quantidadePosts) {
        this.quantidadePosts = quantidadePosts;
    }
}
