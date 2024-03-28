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
    private Long sr_id;

    private String search_title;
    private Integer ranking;

    @ManyToOne
    @JoinColumn(name = "s_id")
    private Search search;
}
