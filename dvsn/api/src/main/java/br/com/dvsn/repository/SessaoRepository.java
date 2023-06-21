package br.com.dvsn.repository;

import br.com.dvsn.entities.Sessao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessaoRepository extends CrudRepository<Sessao, Long> {
    Sessao findByToken(String token);
}
