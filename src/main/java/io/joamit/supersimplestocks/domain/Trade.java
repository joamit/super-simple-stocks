package io.joamit.supersimplestocks.domain;

public class Trade {

    private String id;
    private Long quantity;
    private Direction direction;
    private Double price;

    public Trade(String id, Long quantity, Direction direction, Double price) {
        this.id = id;
        this.quantity = quantity;
        this.direction = direction;
        this.price = price;
    }

    public Trade() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
