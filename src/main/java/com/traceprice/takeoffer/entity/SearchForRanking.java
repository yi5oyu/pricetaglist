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
public class SearchForRanking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sr_id")
    private Long id;

    @Column(name = "search_title")
    private String searchTitle;

    @Column(name = "ranking")
    private Integer ranking;

    @ManyToOne
    @JoinColumn(name = "s_id")
    private Search search;
}
