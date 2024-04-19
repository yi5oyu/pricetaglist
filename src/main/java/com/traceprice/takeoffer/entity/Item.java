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
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long i_id;

    private String pname;
    private Long fixed_price;
    private String item_img;
    private Long item_number;
    private String detail_info;

    @ManyToOne
    @JoinColumn(name = "p_id")
    private Product product;
}
