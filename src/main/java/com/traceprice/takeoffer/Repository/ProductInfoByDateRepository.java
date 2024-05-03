package com.traceprice.takeoffer.Repository;

import com.traceprice.takeoffer.entity.ProductInfoByDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductInfoByDateRepository extends JpaRepository<ProductInfoByDate, Long> {

    List<ProductInfoByDate> findByItemId(Long itemId);

    List<ProductInfoByDate> findByItemIdOrderByPriceDateDesc(Long itemId);
    boolean existsByItemId(Long itemId);

    Long countByPriceDateAndItemId(Date priceDate, Long itemId);
}
