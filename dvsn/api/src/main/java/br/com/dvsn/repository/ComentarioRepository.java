package br.com.dvsn.repository;

import br.com.dvsn.entities.Comentario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface ComentarioRepository extends CrudRepository<Comentario, Long> {

    void deleteByPostId(Long postId);
}
