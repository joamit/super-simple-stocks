package io.joamit.supersimplestocks.domain;

import java.sql.Timestamp;

public final class TradeBuilder {
    private Long id;
    private Long quantity;
    private Direction direction;
    private Double price;
    private Timestamp createdAt;
    private Timestamp closedAt;

    private TradeBuilder() {
    }

    public static TradeBuilder aTrade() {
        return new TradeBuilder();
    }

    public TradeBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public TradeBuilder withQuantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public TradeBuilder withDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    public TradeBuilder withPrice(Double price) {
        this.price = price;
        return this;
    }

    public TradeBuilder withCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public TradeBuilder withClosedAt(Timestamp closedAt) {
        this.closedAt = closedAt;
        return this;
    }

    public Trade build() {
        Trade trade = new Trade();
        trade.setId(id);
        trade.setQuantity(quantity);
        trade.setDirection(direction);
        trade.setPrice(price);
        trade.setCreatedAt(createdAt);
        trade.setClosedAt(closedAt);
        return trade;
    }
}
