package com.traceprice.takeoffer.Repository;

import com.traceprice.takeoffer.entity.Item;
import com.traceprice.takeoffer.entity.ProductInfoByDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByItemNumber(Long itemNumber);

    List<Item> findByPnameContaining(String q);

    @Query("SELECT p.item FROM ProductInfoByDate p WHERE p.item.pname LIKE %:pname% AND p.priceDate = :priceDate")
    List<Item> findItemsByPnameAndPriceDate(@Param("pname") String pname, @Param("priceDate") Date priceDate);

    @Query("SELECT p FROM ProductInfoByDate p WHERE p.item.pname LIKE CONCAT('%', :pname, '%') AND p.priceDate = :priceDate")
    List<ProductInfoByDate> findByItemNameContainingAndPriceDate(@Param("pname") String pname, @Param("priceDate") Date priceDate);

}
