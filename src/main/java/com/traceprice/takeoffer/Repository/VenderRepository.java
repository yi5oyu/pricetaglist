package com.traceprice.takeoffer.Repository;

import com.traceprice.takeoffer.entity.Item;
import com.traceprice.takeoffer.entity.Search;
import com.traceprice.takeoffer.entity.VenderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface VenderRepository extends JpaRepository<VenderItem, Long> {
    Optional<VenderItem> findByVenderNumber(Long venderNumber);

    @Query("SELECT p.venderItem " +
            "FROM ProductInfoByDate p " +
            "WHERE p.venderItem.item.pname LIKE %:pname% " +
            "AND p.priceDate = :priceDate")
    Page<VenderItem> findItemsByPnameAndPriceDate(String pname, Date priceDate, Pageable pageable); //

    @Query("SELECT vi " +
            "FROM VenderItem vi " +
            "JOIN vi.item i " +
            "JOIN i.product p " +
            "JOIN ProductInfoByDate pid ON pid.venderItem = vi " +
            "WHERE p.productType = :productType AND pid.priceDate = :priceDate")
    Page<VenderItem> findItemsByProductTypeAndPriceDate(String productType, Date priceDate, Pageable pageable); //

}
