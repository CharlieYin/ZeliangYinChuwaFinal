package com.chuwa.order.service;

import com.chuwa.order.payload.OrderDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto) throws JsonProcessingException;
    List<OrderDto> getAllOrders();
    OrderDto getOrderById(String id);
    OrderDto updateOrderById(String id, OrderDto orderDto);
    void deleteOrderById(String id);
}
