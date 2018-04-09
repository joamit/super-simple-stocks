package io.joamit.supersimplestocks.service;

import io.joamit.supersimplestocks.domain.Trade;
import io.joamit.supersimplestocks.repository.TradeRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TradeService {

    public static final DateTime INFINITY_DATE = new DateTime(9999, 12, 31, 11, 59, 59);
    private TradeRepository tradeRepository;

    @Autowired
    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    /**
     * Record a trade, with timestamp, quantity of shares, buy or sell indicator and trade price
     *
     * @param trade to be recorded
     * @return recorded trade
     */
    public Trade recordTrade(Trade trade) {
        validateTrade(trade);
        trade.setCreatedAt(new Timestamp(new DateTime().getMillis()));
        trade.setClosedAt(new Timestamp(INFINITY_DATE.getMillis()));
        return this.tradeRepository.save(trade);
    }

    /**
     * Calculate Volume Weighted Stock Price based on trades in past 15 minutes using
     * Σi 𝑇𝑟𝑎𝑑𝑒 𝑃𝑟𝑖𝑐𝑒 𝑖 × 𝑄𝑢𝑎𝑛𝑡𝑖𝑡𝑦 𝑖 / Σi 𝑄𝑢𝑎𝑛𝑡𝑖𝑡𝑦i
     *
     * @param minutes for which VWSP has to be calculated
     * @return volume weighted stock price
     */
    public Double calculateVolumeWeightedStockPrice(Integer minutes) {
        DateTime now = new DateTime();
        List<Trade> recentTrades = this.tradeRepository.findAllByCreatedAtIsAfterAndCreatedAtIsBefore(new Timestamp(now.minusMinutes(minutes).getMillis()), new Timestamp(now.getMillis()));
        Long denominator = recentTrades.stream().mapToLong(trade -> trade.getQuantity()).sum();
        Double numerator = recentTrades.stream().mapToDouble(trade -> trade.getQuantity() * trade.getPrice()).sum();
        return numerator / denominator;

    }

    private void validateTrade(Trade trade) {
        if (trade.getPrice() == null) throw new IllegalStateException("Trade must have a price!");
        if (trade.getQuantity() == null) throw new IllegalStateException("Trade must have a quantity!");
        if (trade.getDirection() == null) throw new IllegalStateException("Trade must have a direction!");
    }

    /**
     * Fetch all trades saved in In-Memory Database
     *
     * @return list of trades
     */
    public List<Trade> fetchAllTrades() {
        return this.tradeRepository.findAll();
    }
}
