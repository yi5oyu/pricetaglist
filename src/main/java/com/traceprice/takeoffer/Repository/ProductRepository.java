package com.traceprice.takeoffer.Repository;

import com.traceprice.takeoffer.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository extends JpaRepository<Product, Long> {

}
