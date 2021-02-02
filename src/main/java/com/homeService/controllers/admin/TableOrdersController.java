package com.homeService.controllers.admin;

import com.homeService.controllers.common.HeaderController;
import com.homeService.entity.Order;
import com.homeService.entity.OrderStatus;
import com.homeService.services.OrderService;
import com.homeService.services.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TableOrdersController {
    @Autowired private OrderService orderService;
    @Autowired private OrderStatusService orderStatusService;
    @Autowired private HeaderController headerController;

    @GetMapping("/admin/tableOrders")
    public String getOrders(@RequestParam String sortId, Model model, Principal principal) {
        headerController.init(model, principal);
        List<Order> orders = new ArrayList<>();
        if (sortId != null) {
            if (sortId.equals("-2")) orders = orderService.findAll();
            else orders = orderService.findAllByStatusId(Long.parseLong(sortId));
        }
        model.addAttribute("orders", orders);
        model.addAttribute("statuses", orderStatusService.findAll());
        return "admin/ordersTable/index";
    }
}
