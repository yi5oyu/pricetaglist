package com.traceprice.takeoffer.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Product {
    private String img;
    private String pName;
    private String price;
    private String pID;
}