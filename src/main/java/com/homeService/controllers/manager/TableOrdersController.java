package com.homeService.controllers.manager;

import com.homeService.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TableOrdersController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/manager/tableOrders")
    public String getOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "ordersTable/index";
    }
}
