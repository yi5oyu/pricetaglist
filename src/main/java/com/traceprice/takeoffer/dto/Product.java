package com.traceprice.takeoffer.dto;

import io.netty.util.internal.IntegerHolder;
import lombok.*;

import java.sql.Date;


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

    private String address;
}