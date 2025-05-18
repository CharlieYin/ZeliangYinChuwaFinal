package com.chuwa.item.controller;

import com.chuwa.item.payload.ItemDto;
import com.chuwa.item.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @PostMapping()
    public ResponseEntity<ItemDto> createItem(@RequestBody ItemDto itemDto) {
        logger.info("Creating item (name: " + itemDto.getName() + ")");

        ItemDto itemResponse = itemService.createItem(itemDto);

        logger.info("Created item (name: " + itemDto.getName() + ") successfully!");

        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable String id) {
        logger.info("Getting item (" + id + ")");

        ItemDto itemResponse = itemService.getItemById(id);

        logger.info("Got item (" + id + ") successfully!");

        return ResponseEntity.ok(itemResponse);
    }

    @GetMapping()
    public ResponseEntity<List<ItemDto>> getItemsByName(@RequestParam String name) {
        logger.info("Getting item (name: " + name + ")");

        List<ItemDto> itemDtoList = itemService.getItemsByName(name);

        logger.info("Got item (name: " + name + ") successfully!");

        return ResponseEntity.ok(itemDtoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDto> updateItemById(@PathVariable String id, @RequestBody ItemDto itemDto) {
        logger.info("Updating item (" + id + ")");

        ItemDto itemResponse = itemService.updateItemById(id, itemDto);

        logger.info("Updated item (" + id + ") successfully!");

        return ResponseEntity.ok(itemResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItemById(@PathVariable String id) {

        logger.info("Deleting item (" + id + ")");

        itemService.deleteItemById(id);

        logger.info("Deleted item (" + id + ") successfully!");

        return new ResponseEntity<>("Deleted item (" + id + ") successfully!", HttpStatus.OK);
    }
}
