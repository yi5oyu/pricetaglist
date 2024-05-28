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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.StyledEditorKit;
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
        Long a = System.currentTimeMillis();
        List<String> keyword = new ArrayList<>(List.of(
                "TV","휴대폰","태블릿PC","노트북","모니터","데스크탑","스마트워치","이어폰","헤드폰","마우스","키보드","Apple"
        ));

        Date currentDate = new Date(System.currentTimeMillis()-(4 * 60 * 60 * 1000));
        Page<Item> items = keyword.stream().noneMatch(q::contains) ? itemRepository.findItemsByPnameAndPriceDate(q,currentDate, pageable) : itemRepository.findItemsByProductTypeAndPriceDate(q, currentDate, pageable);

        List<Product> lists = new ArrayList<>();
        long dd = System.currentTimeMillis();
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
        long b = System.currentTimeMillis();
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
            case "3":
                lists.sort(Comparator.comparing((Product p) -> !p.getDeliveryType().isEmpty())
                        .thenComparing(Product::getDailyPrice, Comparator.reverseOrder()));
                break;
        }
//        int fromIndex = pageable.getPageNumber() * pageable.getPageSize();
//        int toIndex = Math.min(fromIndex + pageable.getPageSize(), lists.size());
//        if (fromIndex >= lists.size()) {
//            return new PageImpl<>(new ArrayList<>(), pageable, lists.size());
//        }
        long c = System.currentTimeMillis();
        System.err.println("총 검색시간: "+(c-a) + " 정렬: " + (c-b) + " 루프인서트: "+(b-dd)+" 순수 쿼리: " + (dd-a));
//        return new PageImpl<>(lists.subList(fromIndex, toIndex), pageable, lists.size());
        return new PageImpl<>(lists, pageable, items.getTotalElements());
    }

    public List<Product> loop(List<ProductInfoByDate> productInfoByDates){
        Long a = System.currentTimeMillis();
        List<Product> products = new ArrayList<>();
        for(int i = 0 ; i<15 ;i++){
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
        Long b = System.currentTimeMillis();
        System.out.println(b-a);
        return products;
    }

    public List<Product> homeSearch(){
        long a = System.currentTimeMillis();
        List<Product> products = new ArrayList<>();
        Date currentDate = new Date(System.currentTimeMillis()-(4 * 60 * 60 * 1000));
        // 할인률
        List<ProductInfoByDate> productInfoByDates = productInfoByDateRepository.findByPriceDateOrderByDiscountRateDesc(currentDate, PageRequest.of(0, 15));
        long b = System.currentTimeMillis();

        return productInfoByDates.size() < 15 ? null : loop(productInfoByDates);

//        for(int i = 0 ; i<30 ;i++){
//            List<ProductInfoByDate> pro = productInfoByDateRepository.findByItemIdOrderByPriceDate(productInfoByDates.get(i).getItem().getId());
//            Optional<Delivery> d = deliveryRepository.findByItemId(productInfoByDates.get(i).getItem().getId());
//            Product p = Product.builder()
//                    .marketName(productInfoByDates.get(i).getItem().getProduct().getMarketName())
//                    .productNumber(productInfoByDates.get(i).getItem().getProduct().getProductNumber())
//                    .productType(productInfoByDates.get(i).getItem().getProduct().getProductType())
//                    .pname(productInfoByDates.get(i).getItem().getPname())
//                    .itemImg(productInfoByDates.get(i).getItem().getItemImg())
//                    .fixedPrice(productInfoByDates.get(i).getItem().getFixedPrice())
//                    .detailInfo(productInfoByDates.get(i).getItem().getDetailInfo())
//                    .itemNumber(productInfoByDates.get(i).getItem().getItemNumber())
//                    .priceDate(productInfoByDates.get(i).getPriceDate())
//                    .dailyPrice(productInfoByDates.get(i).getDailyPrice())
//                    .discountRate(productInfoByDates.get(i).getDiscountRate())
//                    .itemQuantity(productInfoByDates.get(i).getItemQuantity())
//                    .deliveryType(d.get().getDeliveryType())
//                    .deliveryFee(d.get().getDeliveryFee())
//                    .address("https://www.coupang.com/vp/products/"+productInfoByDates.get(i).getItem().getProduct().getProductNumber()+"?itemId="+productInfoByDates.get(i).getItem().getItemNumber())
//                    .productInfoByDates(pro)
//                    .build();
//            products.add(p);
//        }
//        return !products.isEmpty() ? products : null;
    }

    public List<Product> appleSearch(){
        long a = System.currentTimeMillis();
        List<Product> apple = new ArrayList<>();
        Date currentDate = new Date(System.currentTimeMillis()-(4 * 60 * 60 * 1000));
        // 애플
        List<ProductInfoByDate> productInfoByDates = productInfoByDateRepository.findByProductTypeAndPriceDateOrderByDiscountRateDesc("Apple", currentDate, PageRequest.of(0, 15));
        long b = System.currentTimeMillis();
        System.err.println("애플: " + (b-a));
        return productInfoByDates.size() < 15 ? null : loop(productInfoByDates);

//        for(int i = 0 ; i<30 ;i++){
//            List<ProductInfoByDate> pro = productInfoByDateRepository.findByItemIdOrderByPriceDate(productInfoByDates.get(i).getItem().getId());
//            Optional<Delivery> d = deliveryRepository.findByItemId(productInfoByDates.get(i).getItem().getId());
//            Product p = Product.builder()
//                    .marketName(productInfoByDates.get(i).getItem().getProduct().getMarketName())
//                    .productNumber(productInfoByDates.get(i).getItem().getProduct().getProductNumber())
//                    .productType(productInfoByDates.get(i).getItem().getProduct().getProductType())
//                    .pname(productInfoByDates.get(i).getItem().getPname())
//                    .itemImg(productInfoByDates.get(i).getItem().getItemImg())
//                    .fixedPrice(productInfoByDates.get(i).getItem().getFixedPrice())
//                    .detailInfo(productInfoByDates.get(i).getItem().getDetailInfo())
//                    .itemNumber(productInfoByDates.get(i).getItem().getItemNumber())
//                    .priceDate(productInfoByDates.get(i).getPriceDate())
//                    .dailyPrice(productInfoByDates.get(i).getDailyPrice())
//                    .discountRate(productInfoByDates.get(i).getDiscountRate())
//                    .itemQuantity(productInfoByDates.get(i).getItemQuantity())
//                    .deliveryType(d.get().getDeliveryType())
//                    .deliveryFee(d.get().getDeliveryFee())
//                    .address("https://www.coupang.com/vp/products/"+productInfoByDates.get(i).getItem().getProduct().getProductNumber()+"?itemId="+productInfoByDates.get(i).getItem().getItemNumber())
//                    .productInfoByDates(pro)
//                    .build();
//            apple.add(p);
//        }
//        return !apple.isEmpty() ? apple : null;
    }

    public List<Product> allSearch(){
        List<Product> all = new ArrayList<>();
        Date currentDate = new Date(System.currentTimeMillis()-(4 * 60 * 60 * 1000));
        List<ProductInfoByDate> productInfoByDates = productInfoByDateRepository.findByPriceDate(currentDate);
        System.err.println(productInfoByDates.size());
        for(int i = 0 ; i<productInfoByDates.size() ;i++){
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
            all.add(p);
        }
        return all;
    }

}
