package com.example.apirest.apirest.dto;

import com.example.apirest.apirest.model.Item;

import java.util.List;
import java.util.stream.Collectors;

public class OrderItemDTO {

    private String id;
    private String description;
    private Double value;

    public OrderItemDTO(Item item) {
        this.id = item.getId().toString();
        this.description = item.getDescription();
        this.value = item.getValue();
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

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public static List<OrderItemDTO> converter(List<Item> items) {
        return items.stream().map(OrderItemDTO::new).collect(Collectors.toList());
    }

}
