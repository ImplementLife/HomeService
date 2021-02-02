package com.homeService.services;

import com.homeService.dao.OrderDao;
import com.homeService.dao.OrderStatusDao;
import com.homeService.entity.Order;
import com.homeService.entity.OrderStatus;
import com.homeService.entity.User;
import com.homeService.lib.myJSON.MyJsonArray;
import com.homeService.lib.myJSON.MyJsonObject;
import com.homeService.services.transientInitiators.OrderInitiator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class OrderService {
    @Autowired private OrderDao orderDao;
    @Autowired private OrderInitiator initiator;
    /*======================================================*/

    public List<Order> findAllOrdersForUser(User user) {
        return findAllOrdersForUser(user.getId());
    }
    public List<Order> findAllOrdersForUser(Long id) {
        return orderDao.findAllByUserId(id);
    }

    public List<Order> findAll() {
        return initiator.fullInitAll(orderDao.findAll());
    }
    public List<Order> findAllByStatusId(Long statusId) {
        return initiator.fullInitAll(orderDao.findAllByStatusId(statusId));
    }

    public Order findById(Long id) {
        Order order = orderDao.findById(id).get();
        return initiator.fullInit(order);
    }

    public void save(Order order) {
        order.setDate(new Date());
        orderDao.save(order);
    }

    public void delete(Order order) {
        orderDao.delete(order);
    }
    public void deleteAll() {
        for (Order o : findAll())delete(o);
    }
}
