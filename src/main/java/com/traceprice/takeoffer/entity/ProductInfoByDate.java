package com.traceprice.takeoffer.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ProductInfoByDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pp_id;

    private Date price_date;
    private Long daliy_price;
    private Integer item_quantity;
    private Integer discount_rate;

    @ManyToOne
    @JoinColumn(name = "i_id")
    private Item item;
}