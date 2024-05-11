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

    public Page<Product> search(String q, String option, Pageable pageable){
        List<Product> lists = new ArrayList<>();
        Date currentDate = new Date(System.currentTimeMillis()-(4 * 60 * 60 * 1000));
//        List<Item> items = itemRepository.findByPnameContaining(q);
        List<Item> items = itemRepository.findItemsByPnameAndPriceDate(q,currentDate);
        for(Item item : items){
            List<ProductInfoByDate> pro = productInfoByDateRepository.findByItemIdOrderByPriceDate(item.getId());
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
                    .priceDate(pro.get(pro.size() - 1).getPriceDate())
                    .dailyPrice(pro.get(pro.size() - 1).getDailyPrice())
                    .discountRate(pro.get(pro.size() - 1).getDiscountRate())
                    .itemQuantity(pro.get(pro.size() - 1).getItemQuantity())
                    .deliveryType(d.get().getDeliveryType())
                    .deliveryFee(d.get().getDeliveryFee())
                    .address("https://www.coupang.com/vp/products/"+item.getProduct().getProductNumber()+"?itemId="+item.getItemNumber())
                    .productInfoByDates(pro)
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

    public List<Product> homeSearch(){
        List<Product> products = new ArrayList<>();
        Date currentDate = new Date(System.currentTimeMillis()-(4 * 60 * 60 * 1000));
        // 할인률
        List<ProductInfoByDate> productInfoByDates = productInfoByDateRepository.findByPriceDateOrderByDiscountRateDesc(currentDate);
        for(int i = 0 ; i<30 ;i++){
            List<ProductInfoByDate> pro = productInfoByDateRepository.findByItemIdOrderByPriceDate(productInfoByDates.get(i).getItem().getId());
            Optional<Delivery> d = deliveryRepository.findByItemId(productInfoByDates.get(i).getItem().getId());
            Product p = Product.builder()
                    .marketName(productInfoByDates.get(i).getItem().getProduct().getMarketName())
                    .productNumber(productInfoByDates.get(i).getItem().getProduct().getProductNumber())
                    .productType(productInfoByDates.get(i).getItem().getProduct().getProductType())
                    .pname(productInfoByDates.get(i).getItem().getPname())
                    .itemImg(productInfoByDates.get(i).getItem().getItemImg())
                    .fixedPrice(productInfoByDates.get(i).getItem().getFixedPrice())
                    .detailInfo(productInfoByDates.get(i).getItem().getDetailInfo())
                    .itemNumber(productInfoByDates.get(i).getItem().getItemNumber())
                    .priceDate(productInfoByDates.get(i).getPriceDate())
                    .dailyPrice(productInfoByDates.get(i).getDailyPrice())
                    .discountRate(productInfoByDates.get(i).getDiscountRate())
                    .itemQuantity(productInfoByDates.get(i).getItemQuantity())
                    .deliveryType(d.get().getDeliveryType())
                    .deliveryFee(d.get().getDeliveryFee())
                    .address("https://www.coupang.com/vp/products/"+productInfoByDates.get(i).getItem().getProduct().getProductNumber()+"?itemId="+productInfoByDates.get(i).getItem().getItemNumber())
                    .productInfoByDates(pro)
                    .build();
            products.add(p);
        }
        return products;
    }


    public List<Product> appleSearch(){
        List<Product> apple = new ArrayList<>();
        Date currentDate = new Date(System.currentTimeMillis()-(4 * 60 * 60 * 1000));
        // 애플
        List<ProductInfoByDate> productInfoByDates = productInfoByDateRepository.findByProductTypeAndPriceDateOrderByDiscountRateDesc("애플", currentDate);
        System.err.println(productInfoByDates.size());
        for(int i = 0 ; i<30 ;i++){
            List<ProductInfoByDate> pro = productInfoByDateRepository.findByItemIdOrderByPriceDate(productInfoByDates.get(i).getItem().getId());
            Optional<Delivery> d = deliveryRepository.findByItemId(productInfoByDates.get(i).getItem().getId());
            Product p = Product.builder()
                    .marketName(productInfoByDates.get(i).getItem().getProduct().getMarketName())
                    .productNumber(productInfoByDates.get(i).getItem().getProduct().getProductNumber())
                    .productType(productInfoByDates.get(i).getItem().getProduct().getProductType())
                    .pname(productInfoByDates.get(i).getItem().getPname())
                    .itemImg(productInfoByDates.get(i).getItem().getItemImg())
                    .fixedPrice(productInfoByDates.get(i).getItem().getFixedPrice())
                    .detailInfo(productInfoByDates.get(i).getItem().getDetailInfo())
                    .itemNumber(productInfoByDates.get(i).getItem().getItemNumber())
                    .priceDate(productInfoByDates.get(i).getPriceDate())
                    .dailyPrice(productInfoByDates.get(i).getDailyPrice())
                    .discountRate(productInfoByDates.get(i).getDiscountRate())
                    .itemQuantity(productInfoByDates.get(i).getItemQuantity())
                    .deliveryType(d.get().getDeliveryType())
                    .deliveryFee(d.get().getDeliveryFee())
                    .address("https://www.coupang.com/vp/products/"+productInfoByDates.get(i).getItem().getProduct().getProductNumber()+"?itemId="+productInfoByDates.get(i).getItem().getItemNumber())
                    .productInfoByDates(pro)
                    .build();
            apple.add(p);
        }
        return apple;
    }

}
