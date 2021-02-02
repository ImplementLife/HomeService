package com.homeService.dao;

import com.homeService.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PromotionDao extends JpaRepository<Promotion, Long> {
}
