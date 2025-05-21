package com.chuwa.order.service.impl;

import com.chuwa.order.dao.OrderRepository;
import com.chuwa.order.entity.Order;
import com.chuwa.order.enums.OrderStatus;
import com.chuwa.order.exception.OrderNotFoundException;
import com.chuwa.order.payload.KafkaItemCount;
import com.chuwa.order.payload.OrderDto;
import com.chuwa.order.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Value("${kafka.provide.topic.name}")
    private String provideTopic;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Override
    public OrderDto createOrder(OrderDto orderDto) throws JsonProcessingException {
        Order order = modelMapper.map(orderDto, Order.class);
        order.setId(UUID.randomUUID().toString());
        order.setOrderStatus(OrderStatus.TRY_TO_CREATE);
        Order savedOrder = orderRepository.save(order);
        String value = objectMapper.writeValueAsString(new KafkaItemCount(order.getId(), order.getCount()));
        kafkaTemplate.send(provideTopic, order.getItemId(), value);
        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        List<OrderDto> orderDtoList = new ArrayList<>();
        orderList.forEach(order -> orderDtoList.add(modelMapper.map(order, OrderDto.class)));
        return orderDtoList;
    }

    @Override
    public OrderDto getOrderById(String id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public OrderDto updateOrderById(String id, OrderDto orderDto) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        if (orderDto.getUserId() != null) order.setUserId(orderDto.getUserId());
        if (orderDto.getItemId() != null) order.setItemId(orderDto.getItemId());
        if (orderDto.getCount() != null) order.setCount(orderDto.getCount());
        if (orderDto.getPrice() != null) order.setPrice(orderDto.getPrice());
        if (orderDto.getTotalPrice() != null) order.setTotalPrice(orderDto.getTotalPrice());
        if (orderDto.getOrderStatus() != null) order.setOrderStatus(orderDto.getOrderStatus());
        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public void deleteOrderById(String id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        orderRepository.delete(order);
    }


}
