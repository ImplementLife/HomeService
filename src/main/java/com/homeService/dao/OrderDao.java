package com.homeService.dao;

import com.homeService.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDao extends JpaRepository<Order, Long> {
    List<Order> findAllByStatusId(Long statusId);
    List<Order> findAllByUserId(Long userId);
}
