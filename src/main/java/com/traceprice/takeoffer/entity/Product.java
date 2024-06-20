package com.traceprice.takeoffer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "product", indexes = {
        @Index(name = "idx_product_type", columnList = "product_type")
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_id")
    private Long id;

    @Column(name = "market_name")
    private String marketName;
    @Column(name = "product_type")
    private String productType;
    @Column(name = "product_number")
    private Long productNumber;
}
