package br.com.cwi.shop.entities;

import br.com.cwi.shop.helpers.Constantes;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String texto;

    private String visibilidade;

    private String foto;

    @Column(name="criado_em")
    private Date criadoEm;

    @Column(name="atualizado_em")
    private Date atualizadoEm;

    @ManyToOne
    private Usuario usuario;

    @OneToMany
    @JoinColumn(name = "post_id")
    private List<Comentario> comentarios;

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

    public String getFoto() {
        return foto == null || foto.isEmpty() ? null : Constantes.getPrefixUploadPath() + this.foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getVisibilidade() {
        return visibilidade;
    }

    public void setVisibilidade(String visibilidade) {
        this.visibilidade = visibilidade;
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

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}