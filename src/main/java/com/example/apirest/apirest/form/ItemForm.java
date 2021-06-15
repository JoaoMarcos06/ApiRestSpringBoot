package com.example.apirest.apirest.form;

import com.example.apirest.apirest.model.Item;
import com.example.apirest.apirest.repository.ItemRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class ItemForm {

    private String description;
    private Double value;
    private String type;
    private Boolean active;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Item converter() {
        return new Item(description, value);
    }

    public Item update(UUID id, ItemRepository itemRepository) {
        Item item = itemRepository.getById(id);

        item.setDescription(this.description);
        item.setValue(this.value);
        item.setUpdatedAt(LocalDateTime.now());
        item.setActive(this.active);

        return item;
    }
}
