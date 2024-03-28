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
public class View {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long v_id;

    private Integer page_view;
    private Integer link_view;

    @ManyToOne
    @JoinColumn(name = "i_id")
    private Item item;
}
