package io.joamit.supersimplestocks.repository;

import io.joamit.supersimplestocks.domain.Stock;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StockRepository extends CrudRepository<Stock, String> {

    List<Stock> findByParValue(Double parValue);
}
