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
public class VenderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vi_id")
    private Long id;

    @Column(name = "fixed_price")
    private Long fixedPrice;

    @Column(name = "detail_info")
    private String detailInfo;

    @Column(name = "vender_number")
    private Long venderNumber;

    @ManyToOne
    @JoinColumn(name = "i_id")
    private Item item;
}
