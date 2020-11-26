package com.homeService.dao;

import com.homeService.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Set;

public interface ProductDao extends JpaRepository<Product, Long> {
    Set<Product> findAllByCategoryId(Long categoryId);
    Set<Product> findAllByName(String name);
    Collection<Product> findAllByIsPublic(boolean isPublic);
}
