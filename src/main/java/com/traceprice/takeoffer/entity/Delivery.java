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
    private Long d_id;

    private Integer delivery_type;
    private Integer delivery_fee;

    @ManyToOne
    @JoinColumn(name = "i_id")
    private Item item;
}
