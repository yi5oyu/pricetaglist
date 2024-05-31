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
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "d_id")
    private Long id;

    @Column(name = "delivery_type")
    private String deliveryType;

    @Column(name = "delivery_fee")
    private Integer deliveryFee;

    @ManyToOne
    @JoinColumn(name = "vi_id")
    private VenderItem venderItem;
}
