package br.com.dvsn.entities;

import br.com.dvsn.helpers.Constantes;
import br.com.dvsn.helpers.StringHelper;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String sobrenome;

    private String email;

    private String senha;

    private String foto;

    private int funcao;

    @Column(name="criado_em")
    private Date criadoEm;

    @Column(name="atualizado_em")
    private Date atualizadoEm;

    @OneToMany
    @JoinColumn(name = "usuario_id")
    private List<Post> posts;

    @OneToMany
    @JoinColumn(name = "usuario_id")
    private List<Comentario> comentarios;

    public Usuario() { }

    public Usuario(long id) { this.id = id; }

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

    public String getFoto() {
        return Constantes.getPrefixUrlImage() + (StringHelper.isNullOrEmpty(foto) ? "profile-default.jpg" : this.foto);
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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}
