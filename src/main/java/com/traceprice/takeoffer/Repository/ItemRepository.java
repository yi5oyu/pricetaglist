package com.traceprice.takeoffer.Repository;

import com.traceprice.takeoffer.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByItemNumber(Long itemNumber);
}
