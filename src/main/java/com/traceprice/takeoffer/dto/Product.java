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
    private String market_name;
    private String product_type;
    private Long product_number;
    private String pname;
    private Long fixed_price;
    private String item_img;
    private Long item_number;
    private Date price_date;
    private Long daily_price;
    private String delivery_type;
    private int discount_rate;
    private String detail_info;
    private int item_quantity;

    private String address;
}