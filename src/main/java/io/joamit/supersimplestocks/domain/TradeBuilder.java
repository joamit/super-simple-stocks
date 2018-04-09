package io.joamit.supersimplestocks.domain;

public final class TradeBuilder {
    private String id;
    private Long quantity;
    private Direction direction;
    private Double price;

    private TradeBuilder() {
    }

    public static TradeBuilder aTrade() {
        return new TradeBuilder();
    }

    public TradeBuilder withId(String id) {
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

    public Trade build() {
        Trade trade = new Trade();
        trade.setId(id);
        trade.setQuantity(quantity);
        trade.setDirection(direction);
        trade.setPrice(price);
        return trade;
    }
}
