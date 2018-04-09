package io.joamit.supersimplestocks.domain;

import org.joda.time.DateTime;

public class Trade {

    private String id;
    private Long quantity;
    private Direction direction;
    private Double price;
    private DateTime createdAt;
    private DateTime closedAt;

    public Trade(String id, Long quantity, Direction direction, Double price, DateTime createdAt, DateTime closedAt) {
        this.id = id;
        this.quantity = quantity;
        this.direction = direction;
        this.price = price;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
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

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(DateTime closedAt) {
        this.closedAt = closedAt;
    }
}
