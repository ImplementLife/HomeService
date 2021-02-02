package com.homeService.services;

import com.homeService.dao.OrderStatusDao;
import com.homeService.entity.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderStatusService {
    @Autowired private OrderStatusDao orderStatusDao;

    public List<OrderStatus> findAll() {
        return orderStatusDao.findAll();
    }
    public OrderStatus save(OrderStatus s) {
        return orderStatusDao.save(s);
    }
}
