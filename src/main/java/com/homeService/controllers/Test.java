package com.homeService.controllers;

import com.homeService.entity.Order;
import com.homeService.entity.OrderDetails;
import com.homeService.services.OrderService;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.TreeSet;

@Controller
public class Test {
    @GetMapping("/test")
    public void test() {
        OrderService orderService = new OrderService();

        Order order = new Order();
        order.setUserId(1L);
        order.setStatusId(1L);

        TreeSet<OrderDetails> orderDetails = new TreeSet<>();
        orderDetails.add(new OrderDetails(3, 1L));
        orderDetails.add(new OrderDetails(1, 2L));

        orderService.save(order);


        try {
            for (Order o : orderService.findAll()) {
                System.out.println(o.getInfoJSON());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
