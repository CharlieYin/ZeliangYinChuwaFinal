package com.chuwa.item.service;


import com.chuwa.item.dao.ItemRepository;
import com.chuwa.item.entity.Item;
import com.chuwa.item.exception.ItemNotFoundException;
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

import java.util.Map;


@Service
public class KafkaService {
    @Value("${kafka.provide.topic.name}")
    private String provideTopic;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);
    @KafkaListener(topics = "${kafka.consume.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(ConsumerRecord<String, String> record) throws JsonProcessingException {
        logger.info("Received record (key: " + record.key() + ", value: " + record.value() + ").");
        String id = record.key();
        Map<String, Object> map = objectMapper.readValue(record.value(), Map.class);
        int count = (int) map.get("count");
        String orderId = (String) map.get("orderId");
        Item item = itemRepository.findById(id).orElse(null);
        if (item == null) {
            logger.error("Item id is wrong (" + id + ").");
            return;
        }
        int newCount = item.getCount() - count;
        if (newCount >= 0) {
            item.setCount(newCount);
            itemRepository.save(item);
            kafkaTemplate.send(provideTopic, orderId, String.valueOf(count));
            logger.info("Generated new count: " + newCount + " for item (" + id + ").");
        } else {
            kafkaTemplate.send(provideTopic, orderId, "-1");
            logger.error("Item is not enough: request: " + count + ", left: " + item.getCount() + ".");
        }
    }

}
