package io.joamit.supersimplestocks.service;

import io.joamit.supersimplestocks.domain.Trade;
import io.joamit.supersimplestocks.repository.TradeRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TradeService {

    private TradeRepository tradeRepository;

    @Autowired
    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public Trade recordTrade(Trade trade) {
        validateTrade(trade);
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
}
