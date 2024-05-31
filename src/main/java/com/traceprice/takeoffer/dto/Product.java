package com.traceprice.takeoffer.dto;

import com.traceprice.takeoffer.entity.ProductInfoByDate;
import lombok.*;

import java.sql.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Product {
    private String marketName;
    private String productType;
    private Long productNumber;
    private String pname;
    private Long fixedPrice;
    private String itemImg;
    private Long itemNumber;
    private Date priceDate;
    private Long dailyPrice;
    private String deliveryType;
    private int discountRate;
    private String detailInfo;
    private String itemQuantity;
    private int deliveryFee;
    private Long venderNumber;

    private String address;

    private List<ProductInfoByDate> productInfoByDates;

}