package com.homeService.controllers.admin;

import com.homeService.controllers.common.HeaderController;
import com.homeService.entity.OrderStatus;
import com.homeService.services.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class StatusLeadController {
    @Autowired private HeaderController headerController;
    @Autowired private OrderStatusService orderStatusService;
    @GetMapping("/admin/allStatuses")
    private String get(Principal principal, Model model) {
        headerController.init(model, principal);
        model.addAttribute("statuses", orderStatusService.findAll());
        return "admin/tableAllStatuses/index";
    }

    @PostMapping("/admin/createOrderStatus")
    private String post(
            Principal principal, Model model,
            @RequestParam(defaultValue = "") String name
    ) {
        OrderStatus status = new OrderStatus();
        status.setName(name);
        if (!name.isEmpty()) orderStatusService.save(status);
        else model.addAttribute("errorName",true);
        return get(principal, model);
    }

}
