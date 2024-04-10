package com.traceprice.takeoffer.dto;

import io.netty.util.internal.IntegerHolder;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Product {
    private String market_name;
    private String product_type;
    private int product_number;
    private String pname;
    private int fixed_price;
    private String item_img;
    private int item_number;
}