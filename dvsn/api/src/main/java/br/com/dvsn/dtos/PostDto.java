package br.com.dvsn.dtos;

import br.com.dvsn.entities.Post;
import br.com.dvsn.helpers.Constantes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostDto {

    private long id;

    private String texto;

    private String visibilidade;

    private String foto;

    private Date criadoEm;

    private Date atualizadoEm;

    private UsuarioDto usuario;

    private List<ComentarioDto> comentarios;

    private boolean owner;

    public PostDto(){}

    public PostDto(Post post){
        id = post.getId();
        texto = post.getTexto();
        visibilidade = post.getVisibilidade();
        foto = post.getFoto();
        criadoEm = post.getCriadoEm();
        atualizadoEm = post.getAtualizadoEm();
        usuario = new UsuarioDto(post.getUsuario());
        comentarios = new ArrayList<>();
        for(var comentario : post.getComentarios())
            comentarios.add(new ComentarioDto(comentario));
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

    public String getVisibilidade() {
        return visibilidade;
    }

    public void setVisibilidade(String visibilidade) {
        this.visibilidade = visibilidade;
    }

    public String getFoto() {
        return this.foto == null || foto.isEmpty() ? null : Constantes.getPrefixUrlImage() + this.foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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

    public Date getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(Date atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public UsuarioDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDto usuario) {
        this.usuario = usuario;
    }

    public List<ComentarioDto> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioDto> comentarios) {
        this.comentarios = comentarios;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }
}
