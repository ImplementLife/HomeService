package com.homeService.dao;

import com.homeService.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Long> {
    Category findByName(String name);
    List<Category> findAllByParentId(Long parentId);
    List<Category> findAllByIsForProduct(boolean isForProduct);
}
