package com.homeService.dao;

import com.homeService.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusDao extends JpaRepository<OrderStatus, Long> {
}
