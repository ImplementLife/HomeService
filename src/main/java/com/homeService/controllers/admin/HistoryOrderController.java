package com.homeService.controllers.admin;

import com.homeService.controllers.common.HeaderController;
import com.homeService.entity.Order;
import com.homeService.services.OrderService;
import com.homeService.services.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class HistoryOrderController {
    @Autowired private OrderService orderService;
    @Autowired private OrderStatusService orderStatusService;
    @Autowired private HeaderController headerController;

    @GetMapping("/admin/tableOrders/{id}")
    public String getHistory(Model model, Principal principal, @PathVariable("id") String id) {
        headerController.init(model, principal);
        Order order = orderService.findById(Long.parseLong(id));

        model.addAttribute("order", order);
        model.addAttribute("statuses", orderStatusService.findAll());
        return "admin/orderInfo/index";
    }
    @PostMapping("/admin/updateOrder/{id}")
    public String updateStatusOrder(Model model, Principal principal, @PathVariable("id") String id, @RequestParam String statusId) {
        headerController.init(model, principal);
        Order order = orderService.findById(Long.parseLong(id));
        order.setStatusId(Long.parseLong(statusId));
        orderService.save(order);
        model.addAttribute("order", order);
        model.addAttribute("statuses", orderStatusService.findAll());
        return "admin/orderInfo/index";
    }

}
