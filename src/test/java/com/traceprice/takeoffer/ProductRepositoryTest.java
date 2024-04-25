package com.traceprice.takeoffer;

import com.traceprice.takeoffer.Repository.ProductRepository;
import com.traceprice.takeoffer.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testCreateAndRetrieveProduct() {
        // Product 생성 및 저장
        Product product = Product.builder()
                .market_name("Example Market")
                .product_type("Electronics")
                .product_number(12345L)
                .build();
        product = productRepository.save(product);

        // Product 조회
        Product foundProduct = productRepository.findById(product.getP_id()).orElse(null);

        System.out.println(foundProduct.getProduct_number());

        // 검증
        assertNotNull(foundProduct);
        assertEquals("Example Market", foundProduct.getMarket_name());
        assertEquals("Electronics", foundProduct.getProduct_type());
        assertEquals(12345L, foundProduct.getProduct_number());

    }
}
