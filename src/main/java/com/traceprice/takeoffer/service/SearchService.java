package com.traceprice.takeoffer.service;

import com.traceprice.takeoffer.Repository.DeliveryRepository;
import com.traceprice.takeoffer.Repository.ItemRepository;
import com.traceprice.takeoffer.Repository.ProductInfoByDateRepository;
import com.traceprice.takeoffer.Repository.SearchRepository;
import com.traceprice.takeoffer.dto.Product;
import com.traceprice.takeoffer.entity.Delivery;
import com.traceprice.takeoffer.entity.Item;
import com.traceprice.takeoffer.entity.ProductInfoByDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class SearchService {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ProductInfoByDateRepository productInfoByDateRepository;

    @Autowired
    DeliveryRepository deliveryRepository;

    public Page<Product> Search(String q, String option, Pageable pageable){
        List<Product> lists = new ArrayList<>();
        Date currentDate = new Date(System.currentTimeMillis()-(4 * 60 * 60 * 1000));
//        List<Item> items = itemRepository.findByPnameContaining(q);
        List<Item> items = itemRepository.findItemsByPnameAndPriceDate(q,currentDate);
        for(Item item : items){
            List<ProductInfoByDate> pro = productInfoByDateRepository.findByItemIdOrderByPriceDateDesc(item.getId());
            Optional<Delivery> d = deliveryRepository.findByItemId(item.getId());
            Product p = Product.builder()
                    .marketName(item.getProduct().getMarketName())
                    .productNumber(item.getProduct().getProductNumber())
                    .productType(item.getProduct().getProductType())
                    .pname(item.getPname())
                    .itemImg(item.getItemImg())
                    .fixedPrice(item.getFixedPrice())
                    .detailInfo(item.getDetailInfo())
                    .itemNumber(item.getItemNumber())
                    .priceDate(pro.get(0).getPriceDate())
                    .dailyPrice(pro.get(0).getDailyPrice())
                    .discountRate(pro.get(0).getDiscountRate())
                    .itemQuantity(pro.get(0).getItemQuantity())
                    .deliveryType(d.get().getDeliveryType())
                    .deliveryFee(d.get().getDeliveryFee())
                    .address("https://www.coupang.com/vp/products/"+item.getProduct().getProductNumber()+"?itemId="+item.getItemNumber())
                    .build();
            lists.add(p);
        }

        switch (option){
            case "0":
                lists.sort(Comparator.comparing(Product::getDiscountRate).reversed()
                        .thenComparing(Product::getDailyPrice, Comparator.reverseOrder()));
                break;
            case "1":
                lists.sort(Comparator.comparing(Product::getDailyPrice));
                break;
            case "2":
                lists.sort(Comparator.comparing((Product p) -> p.getItemQuantity().isEmpty())
                        .thenComparing(Product::getDailyPrice, Comparator.reverseOrder()));
                break;
        }
        int fromIndex = pageable.getPageNumber() * pageable.getPageSize();
        int toIndex = Math.min(fromIndex + pageable.getPageSize(), lists.size());

        if (fromIndex >= lists.size()) {
            return new PageImpl<>(new ArrayList<>(), pageable, lists.size());
        }

        return new PageImpl<>(lists.subList(fromIndex, toIndex), pageable, lists.size());
    }

//    public List<Product> displayProduct(){
//
//    }
}
