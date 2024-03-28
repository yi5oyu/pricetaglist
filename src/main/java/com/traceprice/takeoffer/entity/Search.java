package com.traceprice.takeoffer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long s_id;

    private Date search_date;
    private String search_type;
}
