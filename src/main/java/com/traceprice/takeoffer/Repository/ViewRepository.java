package com.traceprice.takeoffer.Repository;

import com.traceprice.takeoffer.entity.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewRepository extends JpaRepository<View, Long> {
}
