package com.example.apirest.apirest.controller;

import com.example.apirest.apirest.dto.OrderDTO;
import com.example.apirest.apirest.dto.OrderDetailDTO;
import com.example.apirest.apirest.form.OrderForm;
import com.example.apirest.apirest.form.OrderItemForm;
import com.example.apirest.apirest.model.Item;
import com.example.apirest.apirest.model.ItemType;
import com.example.apirest.apirest.model.Order;
import com.example.apirest.apirest.model.OrderStatus;
import com.example.apirest.apirest.repository.ItemRepository;
import com.example.apirest.apirest.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderItemController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/{order_id}")
    public ResponseEntity<OrderDetailDTO> list(@PathVariable UUID order_id) {
        Optional<Order> optional = orderRepository.findById(order_id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(new OrderDetailDTO(optional.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{order_id}")
    @Transactional
    public ResponseEntity<OrderDetailDTO> create(@PathVariable UUID order_id, @RequestBody OrderItemForm orderItemForm, UriComponentsBuilder uriBuilder) {
        Optional<Order> optional = orderRepository.findById(order_id);

        if (optional.isPresent()) {
            Order order = optional.get();
            Item item = orderItemForm.converter(itemRepository);
            if (item != null && item.getType().equals(ItemType.PRODUCT)) {
                order.getItems().add(item);
                orderRepository.save(order);

                URI uri = uriBuilder.path("/order/{order_id}").buildAndExpand(order.getId()).toUri();
                return ResponseEntity.created(uri).body(new OrderDetailDTO(order));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{order_id}")
    @Transactional
    public ResponseEntity<OrderDTO> create(@PathVariable UUID order_id, @RequestBody OrderForm orderForm, UriComponentsBuilder uriBuilder) {
        Optional<Order> optional = orderRepository.findById(order_id);

        if (optional.isPresent()) {
            Order order = optional.get();
            if (order.getStatus().equals(OrderStatus.OPEN)) {
                order.setDiscount(orderForm.getDiscount());
                orderRepository.save(order);
                return ResponseEntity.ok(new OrderDTO(order));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{order_id}/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable UUID order_id, @PathVariable UUID id) {
        Optional<Order> optional = orderRepository.findById(order_id);

        if (optional.isPresent()) {
            Order order = optional.get();
            Iterator<Item> itemIterator = order.getItems().iterator();

            while (itemIterator.hasNext()) {
                Item item = itemIterator.next();
                if (item.getId().equals(id)) {
                    itemIterator.remove();
                }
            }
            orderRepository.save(order);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }


}
