package io.joamit.supersimplestocks.repository;

import io.joamit.supersimplestocks.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.Optional;

@RepositoryRestController
public interface StockRepository extends JpaRepository<Stock, String> {

    Optional<Stock> findById(String symbol);
}
