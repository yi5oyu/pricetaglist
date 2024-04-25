package com.traceprice.takeoffer.Repository;

import com.traceprice.takeoffer.entity.ProductInfoByDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInfoByDateRepository extends JpaRepository<ProductInfoByDate, Long> {
}
