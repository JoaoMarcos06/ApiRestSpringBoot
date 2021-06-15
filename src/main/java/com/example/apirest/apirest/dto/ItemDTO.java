package com.example.apirest.apirest.dto;

import com.example.apirest.apirest.model.Item;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ItemDTO {

    private String id;
    private String description;
    private Double value;
    private Boolean active;

    public ItemDTO(Item item) {
        this.id = item.getId().toString();
        this.description = item.getDescription();
        this.value = item.getValue();
        this.active = item.getActive();
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Double getValue() {
        return value;
    }

    public Boolean getActive() {
        return active;
    }

    public static List<ItemDTO> converter(List<Item> items) {
        return items.stream().map(ItemDTO::new).collect(Collectors.toList());
    }

}
