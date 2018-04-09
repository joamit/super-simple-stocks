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

    /**
     * Calculate the GBCE All Share Index using the geometric mean of prices for all stocks
     * using √𝑝1𝑝2𝑝3…𝑝𝑛 to calculate geometric mean
     *
     * @return GBCE All Share Index
     */
    public Double calculateGBCEAllShareIndex() {
        List<Stock> stocks = new ArrayList<>();
        this.stockRepository.findAll().forEach(stocks::add);
        Double total = 1D;
        for (Stock stock : stocks) {
            total *= stock.getPrice();
        }
        return Math.sqrt(total);
    }

    /**
     * Given a market price as input, calculate the P/E Ratio
     * 𝑀𝑎𝑟𝑘𝑒𝑡 𝑃𝑟𝑖𝑐𝑒 / 𝐷𝑖𝑣𝑖𝑑𝑒𝑛𝑑
     *
     * @param symbol      of the stock
     * @param marketPrice of the stock
     * @return PE Ratio for the market price
     */
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

    /**
     * Given a market price as input, calculate the dividend yield
     * For Common stocks : 𝐿𝑎𝑠𝑡 𝐷𝑖𝑣𝑖𝑑𝑒𝑛𝑑 / 𝑀𝑎𝑟𝑘𝑒𝑡 𝑃𝑟𝑖𝑐𝑒
     * For Preferred stocks : 𝐹𝑖𝑥𝑒𝑑 𝐷𝑖𝑣𝑖𝑑𝑒𝑛𝑑 * 𝑃𝑎𝑟 𝑉𝑎𝑙𝑢𝑒 / 𝑀𝑎𝑟𝑘𝑒𝑡 𝑃𝑟𝑖𝑐𝑒
     *
     * @param symbol      of the stock
     * @param marketPrice of the stock
     * @return Dividend Yield for the market price
     */
    public Double calculateDividendYield(String symbol, Double marketPrice) {
        Double dividendYield;
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

    /**
     * Helper method to calculate the dividend yield based on the stock type.
     *
     * @param marketPrice of the stock
     * @param stock       object
     * @return dividend yield
     */
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

    /**
     * Fetch all the stocks saved in In-Memory database
     *
     * @return list of stocks
     */
    public List<Stock> fetchAllStocks() {
        return this.stockRepository.findAll();
    }
}
