package com.chuwa.item.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "items")
public class Item {
    @Id
    private String id;
    private String name;
    private String[] pic_urls;
    private String upc;
    private Map<String, Object> metadata;
    private Integer count;

    public Item() {
    }

    public Item(String id, String name, String[] pic_urls, String upc, Map<String, Object> metadata, Integer count) {
        this.id = id;
        this.name = name;
        this.pic_urls = pic_urls;
        this.upc = upc;
        this.metadata = metadata;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getPic_urls() {
        return pic_urls;
    }

    public void setPic_urls(String[] pic_urls) {
        this.pic_urls = pic_urls;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
