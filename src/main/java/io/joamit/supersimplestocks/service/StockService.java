package io.joamit.supersimplestocks.service;

import io.joamit.supersimplestocks.domain.Stock;
import io.joamit.supersimplestocks.domain.StockType;
import io.joamit.supersimplestocks.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class StockService {

    private StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    Double calculateDividendYield(String symbol, Double marketPrice) {
        Double dividendYield = 0D;
        validateInputs(symbol, marketPrice);
        Optional<Stock> result = this.stockRepository.findById(symbol);
        if (result.isPresent()) {
            Stock stock = result.get();
            dividendYield = calculateDividendYield(marketPrice, dividendYield, stock);
        } else {
            throw new IllegalStateException("Could not find stock with symbol [ " + symbol + " ]. Please try again with the correct symbol!!");
        }
        return dividendYield;
    }

    private Double calculateDividendYield(Double marketPrice, Double dividendYield, Stock stock) {
        if (stock.getType() == StockType.COMMON) {
            dividendYield = stock.getLastDividend() / marketPrice;
        } else if (stock.getType() == StockType.PREFERRED) {
            dividendYield = (stock.getFixedDividend() * stock.getParValue()) / marketPrice;
        } else {
            throw new IllegalStateException("Unknown stock type!!, no formula is set for calculating dividend yield for stock type " + stock.getType());
        }
        return dividendYield;
    }

    private void validateInputs(String symbol, Double marketPrice) {
        if (StringUtils.isEmpty(symbol)) throw new IllegalStateException("Stock symbol can not be empty!");
        if (marketPrice == null)
            throw new IllegalStateException("Market price is require for dividend yield calculation!");
    }
}