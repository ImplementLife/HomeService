package com.homeService.controllers.admin;

import com.homeService.entity.Order;
import com.homeService.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class Test {
    @Autowired
    OrderService orderService;

    public void test() {
        Order order = new Order();
        order.setUserId(1L);
        order.setStatusId(1L);

        ArrayList<Order.OrderDetails> orderDetails = new ArrayList<>();
        orderDetails.add(new Order.OrderDetails(3, 1L));
        orderDetails.add(new Order.OrderDetails(1, 2L));
        order.setOrderDetails(orderDetails);
        orderService.create(order);

        for (Order o : orderService.findAll()) {
            System.out.println(o.getInfoJSON());
        }
    }
}
