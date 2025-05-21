package com.chuwa.order.entity;

import com.chuwa.order.enums.OrderStatus;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("orders")
public class Order {

    @PrimaryKey
    private String id;
    private Long userId;
    private String itemId;
    private Integer count;
    private Double price;
    private Double totalPrice;
    private OrderStatus orderStatus;

    public Order() {
    }

    public Order(String id, Long userId, String itemId, Integer count, Double price, Double totalPrice, OrderStatus orderStatus) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
