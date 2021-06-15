package com.example.apirest.apirest.controller;

import com.example.apirest.apirest.dto.ItemDTO;
import com.example.apirest.apirest.form.ItemForm;
import com.example.apirest.apirest.model.Item;
import com.example.apirest.apirest.model.ItemType;
import com.example.apirest.apirest.repository.ItemRepository;
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
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public List<ItemDTO> list() {
        List<Item> items = itemRepository.findByType(ItemType.SERVICE);
        return  ItemDTO.converter(items);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ItemDTO> create(@RequestBody ItemForm itemForm, UriComponentsBuilder uriBuilder) {
        Item item = itemForm.converter();
        item.setType(ItemType.SERVICE);
        itemRepository.save(item);

        URI uri = uriBuilder.path("/products/{id}").buildAndExpand(item.getId()).toUri();
        return ResponseEntity.created(uri).body(new ItemDTO(item));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> read(@PathVariable UUID id) {
        Optional<Item> item = itemRepository.findByIdAndType(id, ItemType.SERVICE);
        if (item.isPresent()) {
            return ResponseEntity.ok(new ItemDTO(item.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ItemDTO> update(@PathVariable UUID id, @RequestBody  ItemForm itemForm) {
        Optional<Item> optional = itemRepository.findByIdAndType(id, ItemType.SERVICE);
        if (optional.isPresent()) {
            Item item = itemForm.update(id, itemRepository);
            return ResponseEntity.ok(new ItemDTO(item));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Optional<Item> optional = itemRepository.findByIdAndType(id, ItemType.SERVICE);

        if (optional.isPresent()) {
            itemRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
