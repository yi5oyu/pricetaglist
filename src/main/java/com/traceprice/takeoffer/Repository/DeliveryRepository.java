package com.traceprice.takeoffer.Repository;

import com.traceprice.takeoffer.entity.Delivery;
import com.traceprice.takeoffer.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findByVenderItemId(Long enderItemId);
}
