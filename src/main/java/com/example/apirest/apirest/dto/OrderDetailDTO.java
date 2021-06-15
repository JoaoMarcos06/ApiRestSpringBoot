package com.example.apirest.apirest.dto;

import com.example.apirest.apirest.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDetailDTO {

    private String id;
    private String description;
    private String status;
    private Double discount;
    private List<OrderItemDTO> items;

    public OrderDetailDTO(Order order) {
        this.id = order.getId().toString();
        this.description = order.getDescription();
        this.status = order.getStatus().toString();
        this.discount = order.getDiscount();
        this.items = new ArrayList<>();
        this.items.addAll(OrderItemDTO.converter(order.getItems()));
        this.applyDiscount();
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public Double getDiscount() {
        return discount;
    }

    public String getStatus() {
        return status;
    }

    public void applyDiscount(){
        if (this.discount > 0) {
            Double percentualDiscount = (100 - this.discount)  / 100;
            this.getItems().forEach(orderItemDTO -> orderItemDTO.setValue(orderItemDTO.getValue() * percentualDiscount));
        }
    }

    public static List<OrderDetailDTO> converter(List<Order> orders) {
        return orders.stream().map(OrderDetailDTO::new).collect(Collectors.toList());
    }
}
