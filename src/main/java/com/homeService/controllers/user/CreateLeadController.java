package com.homeService.controllers.user;

import com.homeService.controllers.common.HeaderController;
import com.homeService.entity.Order;
import com.homeService.entity.Product;
import com.homeService.lib.myJSON.MyJsonArray;
import com.homeService.lib.myJSON.MyJsonObject;
import com.homeService.services.OrderService;
import com.homeService.services.ProductService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class CreateLeadController {
    @Autowired OrderService orderService;
    @Autowired ProductService productService;
    @Autowired HeaderController headerController;

    @GetMapping("/newOrderSingle")
    public String getOrderSingle(@RequestParam() String id, @RequestParam() String count, Model model, Principal principal) {
        headerController.init(model, principal);
        model.addAttribute("user", headerController.getCurrentUser());
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("count", count);
        array.add(object);
        return priceInit(array.toJSONString(), model);
    }

    @GetMapping("/newOrder")
    public String getOrder(@RequestParam() String productsJSON, Model model, Principal principal) {
        headerController.init(model, principal);
        model.addAttribute("user", headerController.getCurrentUser());
        return priceInit(productsJSON, model);
    }

    private String priceInit(String productsJSON, Model model) {
        MyJsonArray myJsonArray1 = new MyJsonArray(productsJSON);
        ArrayList<Info> info = new ArrayList<>();
        BigDecimal finalPrice = new BigDecimal(0);
        for (String s : myJsonArray1) {
            MyJsonObject myJsonObject = new MyJsonObject(s);
            Info i = new Info();
            i.id = Long.parseLong(myJsonObject.get("id"));
            i.count = Integer.parseInt(myJsonObject.get("count"));
            i.product = productService.findById(i.id);
            MyJsonObject priceList = new MyJsonObject(i.product.getPriceListJSON());
            BigDecimal price = new BigDecimal(priceList.get("defaultPrice"));
            i.finalPrice = price.multiply(BigDecimal.valueOf(i.count));
            BigDecimal discount = null;
            for (String v : new MyJsonArray(priceList.get("discounts"))) {
                MyJsonObject dis = new MyJsonObject(v);
                if (i.count >= new BigDecimal(dis.get("count")).doubleValue()) {
                    discount = new BigDecimal(dis.get("value"));
                }
            }
            if (discount != null) {
                BigDecimal f = i.finalPrice.divide(BigDecimal.valueOf(100));
                BigDecimal sub = f.multiply(discount);
                i.finalPrice = i.finalPrice.subtract(sub).setScale(0, RoundingMode.HALF_EVEN);
            }
            finalPrice = finalPrice.add(i.finalPrice);
            info.add(i);
        }

        model.addAttribute("finalPrice", finalPrice.longValue());
        model.addAttribute("info", info);
        return "createLead/create/index";
    }

    @PostMapping("/createOrder")
    public String setOrder(@RequestParam() String body, Model model, Principal principal) {
        headerController.init(model, principal);
        Order order = new Order();
        order.setUserId(headerController.getCurrentUser().getId());
        order.setInfoJSON(body);
        order.setStatusId(-1L);//-1L = new
        orderService.save(order);
        model.addAttribute("orderNum", order.getId());
        return "createLead/complete/index";
    }

    static class Info {
        Product product;
        long id;
        int count;
        BigDecimal finalPrice;

        public Info() {}

        public Product getProduct() {
            return product;
        }
        public void setProduct(Product product) {
            this.product = product;
        }

        public long getId() {
            return id;
        }
        public void setId(long id) {
            this.id = id;
        }

        public int getCount() {
            return count;
        }
        public void setCount(int count) {
            this.count = count;
        }

        public BigDecimal getFinalPrice() {
            return finalPrice;
        }
        public void setFinalPrice(BigDecimal finalPrice) {
            this.finalPrice = finalPrice;
        }
    }
}
