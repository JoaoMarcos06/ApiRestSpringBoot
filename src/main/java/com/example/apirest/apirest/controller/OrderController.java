package com.example.apirest.apirest.controller;

import com.example.apirest.apirest.dto.OrderDTO;
import com.example.apirest.apirest.form.OrderForm;
import com.example.apirest.apirest.model.Order;
import com.example.apirest.apirest.repository.ItemRepository;
import com.example.apirest.apirest.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public List<OrderDTO> list() {
        List<Order> orders = orderRepository.findAll();
        return  OrderDTO.converter(orders);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<OrderDTO> create(@RequestBody OrderForm orderForm, UriComponentsBuilder uriBuilder) {
        Order order = orderForm.converter();
        orderRepository.save(order);

        URI uri = uriBuilder.path("/orders/{id}").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(uri).body(new OrderDTO(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> read(@PathVariable UUID id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return ResponseEntity.ok(new OrderDTO(order.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<OrderDTO> update(@PathVariable UUID id, @RequestBody  OrderForm orderForm) {
        Optional<Order> optional = orderRepository.findById(id);
        if (optional.isPresent()) {
            Order order = orderForm.update(id, orderRepository);
            return ResponseEntity.ok(new OrderDTO(order));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Optional<Order> optional = orderRepository.findById(id);

        if (optional.isPresent()) {
            orderRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

}
