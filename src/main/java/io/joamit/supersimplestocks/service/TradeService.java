package io.joamit.supersimplestocks.service;

import io.joamit.supersimplestocks.domain.Trade;
import io.joamit.supersimplestocks.repository.TradeRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {

    public static final DateTime INFINITY_DATE = new DateTime(9999, 12, 31, 11, 59, 59);
    private TradeRepository tradeRepository;

    @Autowired
    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public Trade recordTrade(Trade trade) {
        validateTrade(trade);
        trade.setCreatedAt(new DateTime());
        trade.setClosedAt(INFINITY_DATE);
        return this.tradeRepository.save(trade);
    }

    public Double calculateVolumeWeightedStockPrice(Integer minutes) {
        DateTime now = new DateTime();
        List<Trade> recentTrades = this.tradeRepository.findAllByCreatedAtIsAfterAndCreatedAtIsBefore(now.minusMinutes(minutes), now);
        Long denominator = recentTrades.stream().mapToLong(trade -> trade.getQuantity()).sum();
        Double numerator = recentTrades.stream().mapToDouble(trade -> trade.getQuantity() * trade.getPrice()).sum();
        return numerator / denominator;

    }

    private void validateTrade(Trade trade) {
        if (trade.getPrice() == null) throw new IllegalStateException("Trade must have a price!");
        if (trade.getQuantity() == null) throw new IllegalStateException("Trade must have a quantity!");
        if (trade.getDirection() == null) throw new IllegalStateException("Trade must have a direction!");
    }

    public List<Trade> fetchAllTrades() {
        return this.tradeRepository.findAll();
    }
}
