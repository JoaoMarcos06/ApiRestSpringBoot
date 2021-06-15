package com.example.apirest.apirest.form;

import com.example.apirest.apirest.model.Item;
import com.example.apirest.apirest.repository.ItemRepository;

import java.util.UUID;

public class OrderItemForm {

    private String id;
    private String description;
    private Double value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Double getValue() {
        return value;
    }

    public Item converter(ItemRepository itemRepository) {
        Item item = itemRepository.getByIdAndActive(UUID.fromString(id), true);
        return item;
    }
}
