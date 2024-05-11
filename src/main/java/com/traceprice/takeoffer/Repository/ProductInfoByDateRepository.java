package com.traceprice.takeoffer.Repository;

import com.traceprice.takeoffer.entity.ProductInfoByDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductInfoByDateRepository extends JpaRepository<ProductInfoByDate, Long> {

    List<ProductInfoByDate> findByItemId(Long itemId);

    List<ProductInfoByDate> findByItemIdOrderByPriceDate(Long itemId);

    List<ProductInfoByDate> findByPriceDateOrderByDiscountRateDesc(Date currentDate);

    boolean existsByItemId(Long itemId);

    Long countByPriceDateAndItemId(Date priceDate, Long itemId);

    @Query("SELECT pibd FROM ProductInfoByDate pibd " +
            "WHERE pibd.item.product.productType = :productType " +
            "AND pibd.priceDate = :priceDate " +
            "ORDER BY pibd.discountRate DESC")
    List<ProductInfoByDate> findByProductTypeAndPriceDateOrderByDiscountRateDesc(String productType, Date priceDate);
}
