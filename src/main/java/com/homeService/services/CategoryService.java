package com.homeService.services;

import com.homeService.dao.CategoryDao;
import com.homeService.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryService {
    @Autowired
    CategoryDao categoryDao;

    public Optional<Category> findById(Long aLong) {
        return categoryDao.findById(aLong);
    }

    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    public <S extends Category> S save(S s) {
        return categoryDao.saveAndFlush(s);
    }
}
