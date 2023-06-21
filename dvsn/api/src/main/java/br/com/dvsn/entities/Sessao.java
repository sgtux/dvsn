package br.com.dvsn.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "sessao")
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sessao")
    @SequenceGenerator(name = "sessao", sequenceName = "sessao_id_seq", allocationSize = 1)
    private Long id;

    private String token;

    @Column(name="expira_em")
    private Date expiraEm;

    private boolean ativo;

    @Column(name="usuario_id")
    private long usuarioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiraEm() {
        return expiraEm;
    }

    public void setExpiraEm(Date expiraEm) {
        this.expiraEm = expiraEm;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
