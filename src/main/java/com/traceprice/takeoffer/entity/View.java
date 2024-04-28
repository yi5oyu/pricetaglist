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
    @Column(name = "v_id")
    private Long id;

    @Column(name = "page_view")
    private Integer pageView;

    @Column(name = "link_view")
    private Integer linkView;

    @ManyToOne
    @JoinColumn(name = "i_id")
    private Item item;
}
