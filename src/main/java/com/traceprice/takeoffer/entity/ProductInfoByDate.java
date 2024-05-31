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
    @Column(name = "pp_id")
    private Long id;

    @Column(name = "price_date")
    private Date priceDate;

    @Column(name = "daliy_price")
    private Long dailyPrice;

    @Column(name = "item_quantity")
    private String itemQuantity;

    @Column(name = "discount_rate")
    private Integer discountRate;

    @ManyToOne
    @JoinColumn(name = "vi_id")
    private VenderItem venderItem;
}
