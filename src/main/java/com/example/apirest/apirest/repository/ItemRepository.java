package com.example.apirest.apirest.repository;

import com.example.apirest.apirest.model.Item;
import com.example.apirest.apirest.model.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {

    List<Item> findByType(ItemType type);
    Optional<Item> findByIdAndType(UUID id, ItemType type);
    Item getByIdAndActive(UUID id, Boolean active);

}
