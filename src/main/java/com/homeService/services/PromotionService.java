package com.homeService.services;

import com.homeService.dao.PromotionDao;
import com.homeService.entity.Promotion;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PromotionService {
    @Autowired private PromotionDao promotionDao;
    public Promotion save(Promotion promotion) {
        return promotionDao.save(promotion);
    }
    public void delete(Promotion promotion) {
        promotionDao.delete(promotion);
    }
    public List<Promotion> getAll() {
        return promotionDao.findAll();
    }
}
