package io.joamit.supersimplestocks.repository;

import io.joamit.supersimplestocks.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.sql.Timestamp;
import java.util.List;

@RepositoryRestController
public interface TradeRepository extends JpaRepository<Trade, Long> {

    List<Trade> findAllByCreatedAtIsAfterAndCreatedAtIsBefore(Timestamp start, Timestamp end);
}
