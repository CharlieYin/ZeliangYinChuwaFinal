package com.chuwa.order.payload;

public class KafkaItemCount {
    private String orderId;
    private Integer count;

    public KafkaItemCount(String orderId, Integer count) {
        this.orderId = orderId;
        this.count = count;
    }

    public KafkaItemCount() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
