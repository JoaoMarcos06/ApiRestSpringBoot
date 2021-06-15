package com.example.apirest.apirest.repository;

import com.example.apirest.apirest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
