package com.traceprice.takeoffer.Repository;

import com.traceprice.takeoffer.entity.SearchForRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchForRankingRepository extends JpaRepository<SearchForRanking, Long> {
}
