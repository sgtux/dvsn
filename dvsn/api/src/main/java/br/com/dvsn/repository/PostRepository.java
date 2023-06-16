package br.com.dvsn.repository;

import br.com.dvsn.entities.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostRepository {

    @Autowired
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ComentarioRepository comentarioRepository;

    public Post buscarPorId(long id) {
        var jpqlString = "SELECT p FROM Post p join fetch p.usuario left join fetch p.comentarios where p.id = :id";
        var list = entityManager.createQuery(jpqlString, Post.class)
                .setParameter("id", id)
                .getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    public long quantidadePostPorUsuario(long userId) {
        var sqlString = "SELECT count(*) FROM Post where usuario_id = :userId";
        Query query = entityManager.createNativeQuery(sqlString, Long.class)
                .setParameter("userId", userId);
        return ((Number)query.getSingleResult()).longValue();
    }

    public List<Post> buscar(String filtro) {
        var jpqlString = "SELECT p FROM Post p join fetch p.usuario left join fetch p.comentarios c where p.texto like CONCAT('%', :filtro,'%') order by p.criadoEm desc, c.criadoEm asc";
        return entityManager.createQuery(jpqlString, Post.class)
                .setParameter("filtro", filtro)
                .getResultList();
    }

    @Transactional
    public void add(Post post) {
        var sqlString = "INSERT INTO Post (texto, foto, visibilidade, criado_em, usuario_id) VALUES (:texto, :foto, :visibilidade, :criadoEm, :usuarioId)";
        entityManager.createNativeQuery(sqlString)
                .setParameter("texto", post.getTexto())
                .setParameter("foto", post.getFoto())
                .setParameter("visibilidade", post.getVisibilidade())
                .setParameter("criadoEm", post.getCriadoEm())
                .setParameter("usuarioId", post.getUsuario().getId())
                .executeUpdate();
    }

    @Transactional
    public void deletarPorId(long id) {
        var sqlString = "DELETE FROM post WHERE id = :id";
        var list = entityManager.createNativeQuery(sqlString)
                .setParameter("id", id)
                .executeUpdate();
    }
}