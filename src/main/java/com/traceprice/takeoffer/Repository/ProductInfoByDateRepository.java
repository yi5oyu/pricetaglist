package com.traceprice.takeoffer.Repository;

import com.traceprice.takeoffer.entity.ProductInfoByDate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductInfoByDateRepository extends JpaRepository<ProductInfoByDate, Long> {

    List<ProductInfoByDate> findByVenderItemId(Long itemId);

    List<ProductInfoByDate> findByVenderItemIdOrderByPriceDate(Long itemId);

    List<ProductInfoByDate> findByPriceDateOrderByDiscountRateDesc(Date currentDate, Pageable pageable);

//    @Query("SELECT pibd FROM ProductInfoByDate pibd " +
//            "WHERE pibd.priceDate = :priceDate " +
//            "AND pibd.dailyPrice = (" +
//            "  SELECT MIN(subPibd.dailyPrice) " +
//            "  FROM ProductInfoByDate subPibd " +
//            "  WHERE subPibd.priceDate = :priceDate " +
//            "  AND subPibd.item.product.id = pibd.item.product.id" +
//            ") " +
//            "ORDER BY pibd.discountRate DESC")
//    List<ProductInfoByDate> findLowestPriceByProductAndDate(Date priceDate);


    List<ProductInfoByDate> findByPriceDate(Date priceDate);

    boolean existsByVenderItemId(Long venderItemId);

    Long countByPriceDateAndVenderItemId(Date priceDate, Long venderItemId);
 
    @Query("SELECT pibd FROM ProductInfoByDate pibd " +
            "WHERE pibd.venderItem.item.product.productType = :productType " +
            "AND pibd.priceDate = :priceDate " +
            "ORDER BY pibd.discountRate DESC")
    List<ProductInfoByDate> findByProductTypeAndPriceDateOrderByDiscountRateDesc(String productType, Date priceDate, Pageable pageable);

//    @Query("SELECT pidMain FROM ProductInfoByDate pidMain WHERE pidMain.priceDate = :currentDate AND pidMain.dailyPrice = " +
//            "(SELECT MIN(pidSub.dailyPrice) FROM ProductInfoByDate pidSub WHERE pidSub.priceDate = :currentDate AND pidSub.item.product.id = pidMain.item.product.id)")
//    List<ProductInfoByDate> findLowestPriceByDate(Date currentDate);

}
