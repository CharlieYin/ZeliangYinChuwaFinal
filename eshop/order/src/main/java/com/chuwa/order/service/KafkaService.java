package com.chuwa.order.service;

import com.chuwa.order.dao.OrderRepository;
import com.chuwa.order.entity.Order;
import com.chuwa.order.enums.OrderStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {
    @Value("${kafka.provide.topic.name}")
    private String provideTopic;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);
    @KafkaListener(topics = "${kafka.consume.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(ConsumerRecord<String, String> record) throws JsonProcessingException {
        logger.info("Received record (key: " + record.key() + ", value: " + record.value() + ").");
        String id = record.key();
        int count = Integer.parseInt(record.value());

        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            logger.error("Order id is wrong (" + id + ").");
            return;
        }
        if (count >= 0) {
            order.setOrderStatus(OrderStatus.CREATED);
            orderRepository.save(order);
            logger.info("Created new order: " + id + " with count: " + count + ".");
        } else {
            order.setOrderStatus(OrderStatus.INVALID);
            orderRepository.save(order);
            logger.error("The request count is too many, cannot create the order: " + id + " (count: " + count + ".");
        }
    }

}
