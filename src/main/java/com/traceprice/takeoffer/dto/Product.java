package com.traceprice.takeoffer.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Product {
    private String p_id;
    private String i_id;
    private String market_name;
    private String product_type;
    private String pname;
    private int fixed_price;
    private String item_img;
}