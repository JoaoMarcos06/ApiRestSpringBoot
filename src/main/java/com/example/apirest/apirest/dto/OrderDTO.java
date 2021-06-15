package com.example.apirest.apirest.dto;

import com.example.apirest.apirest.model.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO {

    private String id;
    private String description;
    private String status;
    private Double discount;

    public OrderDTO(Order order) {
        this.id = order.getId().toString();
        this.description = order.getDescription();
        this.status = order.getStatus().toString();
        this.discount = order.getDiscount();
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Double getDiscount() {
        return discount;
    }

    public String getStatus() {
        return status;
    }

    public static List<OrderDTO> converter(List<Order> orders) {
        return orders.stream().map(OrderDTO::new).collect(Collectors.toList());
    }
}
