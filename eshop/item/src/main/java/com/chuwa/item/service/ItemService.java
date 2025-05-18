package com.chuwa.item.service;

import com.chuwa.item.payload.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto createItem(ItemDto itemDto);
    ItemDto getItemById(String id);
    List<ItemDto> getItemsByName(String name);
    ItemDto updateItemById(String id, ItemDto itemDto);
    void deleteItemById(String id);
}
