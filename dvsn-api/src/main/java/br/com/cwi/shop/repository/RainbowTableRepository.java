package br.com.cwi.shop.repository;

import br.com.cwi.shop.entities.RainbowTableHash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface RainbowTableRepository extends JpaRepository<RainbowTableHash, Long> {

    RainbowTableHash findBySha1(String hash);

    RainbowTableHash findByMd5(String hash);
}
