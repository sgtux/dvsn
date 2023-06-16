package br.com.dvsn.dtos;

import br.com.dvsn.entities.Comentario;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ComentarioDto {

    private long id;

    private String texto;

    private Date criadoEm;

    private long usuarioId;

    private long postId;

    private UsuarioDto usuario;

    private boolean owner;

    public ComentarioDto () { }

    public ComentarioDto (Comentario comentario){
        id = comentario.getId();
        texto = comentario.getTexto();
        criadoEm = comentario.getCriadoEm();
        usuarioId = comentario.getUsuario().getId();
        postId = comentario.getPost().getId();
        usuario = new UsuarioDto(comentario.getUsuario());
    }

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

    public String getCriadoEmFormatado() {
        return new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(criadoEm);
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public UsuarioDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDto usuario) {
        this.usuario = usuario;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }
}
