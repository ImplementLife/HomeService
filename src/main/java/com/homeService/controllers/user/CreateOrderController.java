package com.homeService.controllers.user;

import com.homeService.entity.Order;
import com.homeService.entity.OrderStatus;
import com.homeService.entity.Product;
import com.homeService.services.OrderService;
import com.homeService.services.ProductService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class CreateOrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;

    @PostMapping("/newOrderSingle")
    public String getOrderSingle(
    @RequestParam("id") String id,
    @RequestParam("count") String count,
    Model model
    ) throws Exception {
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("count", count);
        array.add(object);
        return temp(array.toJSONString(), model);
    }

    @PostMapping("/newOrder")
    public String getOrder(
    @RequestParam("productsJSON") String productsJSON,
    Model model) throws Exception {
        return temp(productsJSON, model);
    }

    private String temp(String productsJSON, Model model) throws Exception {
        JSONArray array = (JSONArray) new JSONParser().parse(productsJSON);
        ArrayList<Info> info = new ArrayList<>();
        int finalPrice = 0;
        for (Object o : array) {
            JSONObject object = (JSONObject) o;
            Info i = new Info();
            i.id = Long.parseLong((String) object.get("id"));
            i.count = Integer.parseInt((String) object.get("count"));
            i.product = productService.findProductById(i.id);

            for (Product.OptPrice op : i.product.getOptPrices().values()) {
                if (op.getMinCount() <= i.count) {
                    i.finalPrice = i.count * op.getMoney();
                }
            }
            finalPrice += i.finalPrice;
            info.add(i);
        }
        model.addAttribute("finalPrice", finalPrice);
        model.addAttribute("info", info);
        return "createOrder/index";
    }


    @PostMapping("/createOrder")
    public String createOrder(
    @RequestParam("body") String body,
    Model model) throws ParseException {
        JSONObject object = (JSONObject) new JSONParser().parse(body);
//        ArrayList<Info> info = parseOrderInfo(((JSONArray) object.get("products")).toJSONString());

        Order order = new Order();
        order.setInfoJSON(body);
        order.setStatusId(1L);//1L = new
        orderService.save(order);

        return "orderComplete/index";
    }
//
//    @PostMapping("/updateOrder")
//    public String updateOrder(
//    @RequestParam("infoJSON") String infoJSON,
//    Model model) throws ParseException {
//        JSONObject object = (JSONObject) new JSONParser().parse(infoJSON);
//
//        Order order = new Order();
//        order.setInfoJSON(infoJSON);
//        order.setStatusId(1L); //1L = new
//        orderService.create(order);
//
//        return "orderComplete/index";
//    }

    static class Info {
        Product product;
        long id;
        int count;
        int finalPrice;

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

        public int getFinalPrice() {
            return finalPrice;
        }
        public void setFinalPrice(int finalPrice) {
            this.finalPrice = finalPrice;
        }
    }
}
