package io.joamit.supersimplestocks.repository;

import io.joamit.supersimplestocks.domain.Trade;
import org.joda.time.DateTime;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TradeRepository extends CrudRepository<Trade, String> {

    List<Trade> findAllByCreatedAtIsAfterAndCreatedAtIsBefore(DateTime start, DateTime end);
}
