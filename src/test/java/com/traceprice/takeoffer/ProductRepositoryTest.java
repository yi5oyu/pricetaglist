package com.traceprice.takeoffer;

import com.traceprice.takeoffer.Repository.ItemRepository;
import com.traceprice.takeoffer.Repository.ProductInfoByDateRepository;
import com.traceprice.takeoffer.Repository.ProductRepository;
import com.traceprice.takeoffer.entity.Item;
import com.traceprice.takeoffer.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ProductInfoByDateRepository repository;

//    @Test
//    public void testCreateAndRetrieveProduct() {
//        // Product 생성 및 저장
//    }

//    @Test
//    public void testCountByPriceDate() {
//        Item item = itemRepository.findByItemNumber(21180843873L).orElse(null);

//        if(repository.existsByItemId(item.getId())){
//            Date currentDate = new Date(System.currentTimeMillis());
//            Long c = repository.countByPriceDate(currentDate);
//            assertEquals(1, c); // expectedCount는 예상되는 카운트 값
//        }
//    }
}
