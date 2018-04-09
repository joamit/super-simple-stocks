package io.joamit.supersimplestocks.service;

import io.joamit.supersimplestocks.domain.Stock;
import io.joamit.supersimplestocks.domain.StockType;
import io.joamit.supersimplestocks.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Double calculateGBCEAllShareIndex() {
        Iterable<Stock> result = this.stockRepository.findAll();
        List<Stock> stocks = new ArrayList<>();
        result.forEach(stocks::add);
        Double total = 1D;
        for (Stock stock : stocks) {
            total *= stock.getPrice();
        }
        return Math.sqrt(total);
    }

    public Double calculatePERatio(String symbol, Double marketPrice) {
        Double peRatio;
        validateInputs(symbol, marketPrice);
        Optional<Stock> result = this.stockRepository.findById(symbol);
        if (result.isPresent()) {
            Stock stock = result.get();
            peRatio = marketPrice / stock.getLastDividend();
        } else {
            throw new IllegalStateException("Could not find stock with symbol [ " + symbol + " ]. Please try again with the correct symbol!!");
        }
        return peRatio;
    }

    public Double calculateDividendYield(String symbol, Double marketPrice) {
        Double dividendYield = 0D;
        validateInputs(symbol, marketPrice);
        Optional<Stock> result = this.stockRepository.findById(symbol);
        if (result.isPresent()) {
            Stock stock = result.get();
            dividendYield = calculateDividendYield(marketPrice, stock);
        } else {
            throw new IllegalStateException("Could not find stock with symbol [ " + symbol + " ]. Please try again with the correct symbol!!");
        }
        return dividendYield;
    }

    private Double calculateDividendYield(Double marketPrice, Stock stock) {
        Double dividendYield;
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
            throw new IllegalStateException("Market price is require for dividend yield and P/E ratio calculation!");
    }

    public List<Stock> fetchAllStocks() {
        return this.stockRepository.findAll();
    }
}
