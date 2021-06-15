package com.example.apirest.apirest.form;


import com.example.apirest.apirest.model.Item;
import com.example.apirest.apirest.model.Order;
import com.example.apirest.apirest.model.OrderStatus;
import com.example.apirest.apirest.repository.ItemRepository;
import com.example.apirest.apirest.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderForm {

    private String description;
    private String status;
    private Double discount;
    private List<OrderItemForm> orderItems;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Order converter() {
        return new Order(description);
    }

    public Order update(UUID id, OrderRepository orderRepository) {
        Order order = orderRepository.getById(id);

        order.setDescription(this.description);
        order.setStatus(OrderStatus.valueOf(this.status));
        order.setUpdatedAt(LocalDateTime.now());

        return order;

    }
}
