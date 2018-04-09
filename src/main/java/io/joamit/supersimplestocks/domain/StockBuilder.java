package io.joamit.supersimplestocks.domain;

public final class StockBuilder {
    private String symbol;
    private StockType type;
    private Double lastDividend;
    private Double fixedDividend;
    private Double parValue;

    private StockBuilder() {
    }

    public static StockBuilder aStock() {
        return new StockBuilder();
    }

    public StockBuilder withSymbol(String symbol) {
        this.symbol = symbol;
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

    public Stock build() {
        Stock stock = new Stock();
        stock.setSymbol(symbol);
        stock.setType(type);
        stock.setLastDividend(lastDividend);
        stock.setFixedDividend(fixedDividend);
        stock.setParValue(parValue);
        return stock;
    }
}
