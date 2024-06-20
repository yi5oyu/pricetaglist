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
@Table(name = "item", indexes = {
        @Index(name = "idx_pname", columnList = "pname")
})
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "i_id")
    private Long id;

    private String pname;
    @Column(name = "item_img")
    private String itemImg;
    @Column(name = "item_number")
    private Long itemNumber;

    @ManyToOne
    @JoinColumn(name = "p_id")
    private Product product;
}
