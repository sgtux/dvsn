package br.com.dvsn.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "comentario")
    @SequenceGenerator(name = "comentario", sequenceName = "comentario_id_seq", allocationSize = 1)
    private long id;

    private String texto;

    @Column(name = "criado_em")
    private Date criadoEm;

    @ManyToOne
    private Post post;

    @ManyToOne
    private Usuario usuario;

    public Comentario(){}

    public Comentario(long id) { this.id = id; }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Date criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
