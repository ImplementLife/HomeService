package com.homeService.services;

import com.homeService.dao.OrderDao;
import com.homeService.entity.Order;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class OrderService {
    @Autowired
    OrderDao orderDao;

    /*======================================================*/

    public List<Order> findAll() {
        List<Order> list = orderDao.findAll();
        for (Order order : list) order.initOrderDetails();
        return list;
    }

    public Order findById(Long aLong) {
        Order order = orderDao.findById(aLong).get();
        order.initOrderDetails();
        return order;
    }

    public void save(Order order) {
        order.setDate(new Date());
        orderDao.save(order);
    }
    public void update() {

    }

    public void delete(Order order) {
        orderDao.delete(order);
    }
    public void deleteAll() {
        for (Order o : findAll()){
            delete(o);
        }
    }
}
