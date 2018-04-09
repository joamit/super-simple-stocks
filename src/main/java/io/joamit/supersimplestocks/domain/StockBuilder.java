package io.joamit.supersimplestocks.domain;

public final class StockBuilder {
    private String id;
    private StockType type;
    private Double lastDividend;
    private Double fixedDividend;
    private Double parValue;
    private Double price;

    private StockBuilder() {
    }

    public static StockBuilder aStock() {
        return new StockBuilder();
    }

    public StockBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public StockBuilder withType(StockType type) {
        this.type = type;
        return this;
    }

    public StockBuilder withLastDividend(Double lastDividend) {
        this.lastDividend = lastDividend;
        return this;
    }

    public StockBuilder withFixedDividend(Double fixedDividend) {
        this.fixedDividend = fixedDividend;
        return this;
    }

    public StockBuilder withParValue(Double parValue) {
        this.parValue = parValue;
        return this;
    }

    public StockBuilder withPrice(Double price) {
        this.price = price;
        return this;
    }

    public Stock build() {
        Stock stock = new Stock();
        stock.setId(id);
        stock.setType(type);
        stock.setLastDividend(lastDividend);
        stock.setFixedDividend(fixedDividend);
        stock.setParValue(parValue);
        stock.setPrice(price);
        return stock;
    }
}
