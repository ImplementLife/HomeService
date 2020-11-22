package com.homeService.dao;

import com.homeService.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CategoryDao extends JpaRepository<Category, Long> {
    Category findByName(String name);
    Collection<Category> findAllByParentId(Long parentId);
}
