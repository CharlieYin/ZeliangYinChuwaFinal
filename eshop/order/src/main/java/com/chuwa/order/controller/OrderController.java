package com.chuwa.order.controller;

import com.chuwa.order.payload.OrderDto;
import com.chuwa.order.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping()
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) throws JsonProcessingException {
        logger.info("Creating order (user: " + orderDto.getUserId() + ", item: " + orderDto.getItemId() + ", count: " + orderDto.getCount() + ").");

        OrderDto orderResponse = orderService.createOrder(orderDto);

        logger.info("Created order (" + orderResponse.getId() + ") successfully!");

        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        logger.info("Getting all orders.");

        List<OrderDto> orderResponse = orderService.getAllOrders();

        logger.info("Got all orders successfully!");

        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable String id) {
        logger.info("Getting order (" + id + ").");

        OrderDto orderResponse = orderService.getOrderById(id);

        logger.info("Got order (" + id + ") successfully!");

        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrderById(@PathVariable String id, @RequestBody OrderDto orderDto) {
        logger.info("Updating order (" + id + ")");

        OrderDto orderResponse = orderService.updateOrderById(id, orderDto);

        logger.info("Updated order (" + id + ") successfully!");

        return ResponseEntity.ok(orderResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable String id) {

        logger.info("Deleting order (" + id + ")");

        orderService.deleteOrderById(id);

        logger.info("Deleted order (" + id + ") successfully!");

        return new ResponseEntity<>("Deleted order (" + id + ") successfully!", HttpStatus.OK);
    }
}
