package br.com.dvsn.repository;

import br.com.dvsn.dtos.UsuarioDto;
import br.com.dvsn.entities.Usuario;
import br.com.dvsn.helpers.StringHelper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class UsuarioRepository {

    @Autowired
    private EntityManager entityManager;

    public Usuario buscarPorId(long id) {
        var query = entityManager.createQuery("select u from Usuario u where u.id = :id", Usuario.class);
        query.setParameter("id", id);
        var list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Usuario> buscar(String filtro) {
        var sqlString = "select * from usuario u where nome like '%" + filtro + "%'";
        var query = entityManager.createNativeQuery(sqlString, Usuario.class);
        return query.getResultList();
    }

    public Usuario buscarPorEmail(String email) {
        var query = entityManager.createQuery("select u from Usuario u where u.email = :email", Usuario.class);
        query.setParameter("email", email);
        var list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    public Usuario login(UsuarioDto usuario) {
        var jpqlquery = String.format("select u from Usuario u where u.email = '%s' and u.senha = '%s'", usuario.getEmail(), StringHelper.md5(usuario.getSenha()));
        var list = entityManager.createQuery(jpqlquery, Usuario.class).getResultList();
        if(list.isEmpty())
            return null;
        else
            return list.get(0);
    }

    @Transactional
    public void adicionar(UsuarioDto u) {
        String hashSenha = StringHelper.md5(u.getSenha());
        var sqlString = "INSERT INTO usuario (nome, sobrenome, email, senha, foto, funcao, criado_em) VALUES ('%s', '%s', '%s', '%s', '%s', %s, '%s')";
        sqlString = String.format(sqlString, u.getNome(), u.getSobrenome(), u.getEmail(), hashSenha, "", 2, new Date());
        entityManager.createNativeQuery(sqlString).executeUpdate();
    }

    @Transactional
    public void atualizar(UsuarioDto u) {
        if(StringHelper.isNullOrEmpty(u.getFoto())) {
            var sqlString = "UPDATE usuario SET nome = :nome, sobrenome = :sobrenome where id = :id";
            entityManager.createNativeQuery(sqlString)
                    .setParameter("nome", u.getNome())
                    .setParameter("sobrenome", u.getSobrenome())
                    .setParameter("id", u.getId())
                    .executeUpdate();
        } else {
            var sqlString = "UPDATE usuario SET nome = :nome, sobrenome = :sobrenome, foto = :foto where id = :id";
            entityManager.createNativeQuery(sqlString)
                    .setParameter("nome", u.getNome())
                    .setParameter("sobrenome", u.getSobrenome())
                    .setParameter("foto", u.getFoto())
                    .setParameter("id", u.getId())
                    .executeUpdate();
        }
    }
}