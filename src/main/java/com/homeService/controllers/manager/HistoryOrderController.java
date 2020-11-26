package com.homeService.controllers.manager;

import com.homeService.entity.Order;
import com.homeService.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HistoryOrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/admin/tableOrders/{id}")
    public String getHistory(Model model, @PathVariable("id") String id) {
        Order order = orderService.findById(Long.parseLong(id));
        model.addAttribute("order", order);
        return "orderInfo/index";
    }
}
