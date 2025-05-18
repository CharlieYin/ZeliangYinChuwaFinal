package com.chuwa.item.service.impl;

import com.chuwa.item.controller.ItemController;
import com.chuwa.item.dao.ItemRepository;
import com.chuwa.item.entity.Item;
import com.chuwa.item.exception.ItemNotFoundException;
import com.chuwa.item.payload.ItemDto;
import com.chuwa.item.service.ItemService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ItemDto createItem(ItemDto itemDto) {
        Item item = modelMapper.map(itemDto, Item.class);
        Item savedItem = itemRepository.save(item);
        return modelMapper.map(savedItem, ItemDto.class);
    }

    @Override
    public ItemDto getItemById(String id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
        return modelMapper.map(item, ItemDto.class);
    }

    @Override
    public List<ItemDto> getItemsByName(String name) {
        List<Item> itemList = itemRepository.findByNameContaining(name);

        List<ItemDto> itemDtoList = new ArrayList<>();
        itemList.forEach(item -> itemDtoList.add(modelMapper.map(item, ItemDto.class)));
        return itemDtoList;
    }

    @Override
    public ItemDto updateItemById(String id, ItemDto itemDto) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
        if (itemDto.getName() != null) item.setName(itemDto.getName());
        if (itemDto.getPic_urls() != null) item.setPic_urls(itemDto.getPic_urls());
        if (itemDto.getUpc() != null) item.setUpc(itemDto.getUpc());
        if (itemDto.getMetadata() != null) item.setMetadata(itemDto.getMetadata());
        if (itemDto.getCount() != null) item.setCount(itemDto.getCount());
        Item savedItem = itemRepository.save(item);
        return modelMapper.map(savedItem, ItemDto.class);
    }

    @Override
    public void deleteItemById(String id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
        itemRepository.delete(item);
    }
}
