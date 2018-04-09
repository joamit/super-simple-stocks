package io.joamit.supersimplestocks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.joamit.supersimplestocks.domain.Stock;
import io.joamit.supersimplestocks.domain.Trade;
import io.joamit.supersimplestocks.repository.StockRepository;
import io.joamit.supersimplestocks.repository.TradeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class SuperSimpleStocksApplication {


    public static void main(String[] args) {
        SpringApplication.run(SuperSimpleStocksApplication.class, args);
    }


    /**
     * Initialize the application with some dummy trade and stock values.
     *
     * @param tradeRepository to save dummy trades
     * @param stockRepository to save dummy stocks
     * @return cmd runner instance
     * @throws IOException
     */
    @Bean
    CommandLineRunner init(TradeRepository tradeRepository, StockRepository stockRepository) throws IOException {
        return evt -> {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
            List<Stock> stocks = objectMapper.readValue(this.getClass().getClassLoader().getResourceAsStream("dummy-stocks.json"), new TypeReference<List<Stock>>() {
            });
            stocks.forEach(stockRepository::save);

            List<Trade> trades = objectMapper.readValue(this.getClass().getClassLoader().getResourceAsStream("dummy-trades.json"), new TypeReference<List<Trade>>() {
            });
            trades.forEach(tradeRepository::save);
        };
    }
}
