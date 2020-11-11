package com.homeService.services;

import com.homeService.dao.OrderDao;
import com.homeService.entity.Order;
import com.homeService.entity.OrderDetails;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.TreeSet;

@Component
public class OrderService {

    @Autowired
    OrderDao orderDao;

    public OrderService() {}

    /*===========      JSON  OrderDetails    ==============*/

    /**
     * Метод для инициализации внутреннего объекта "c информацией по заказу" из JSON строки
     *
     * @param order
     * @return
     * @throws ParseException
     */
    public Order init(Order order) throws ParseException {
        TreeSet<OrderDetails> orderDetails = new TreeSet<>();
        JSONObject object = (JSONObject) new JSONParser().parse(order.getInfoJSON());
        JSONArray array = (JSONArray) object.get("orderDetails");
        for (Object o : array) {
            JSONObject temp = (JSONObject) o;
            OrderDetails value = new OrderDetails();
            value.setCount((int) temp.get("count"));
            value.setProductId((long) temp.get("productId"));
            orderDetails.add(value);
        }
        order.setOrderDetails(orderDetails);
        return order;
    }

    public Order set(Order order) throws ParseException {
        JSONArray array = new JSONArray();
        for (OrderDetails o : order.getOrderDetails()) {
            JSONObject temp = new JSONObject();
            temp.put("count", o.getCount());
            temp.put("productId", o.getProductId());
            array.add(temp);
        }
        JSONObject object = (JSONObject) new JSONParser().parse(order.getInfoJSON());

        object.put("orderDetails", array);

        order.setInfoJSON(object.toJSONString());
        return order;
    }

    /*======================================================*/

    public List<Order> findAll() throws ParseException {
        List<Order> list = orderDao.findAll();
        for (Order order : list) this.init(order);
        return list;
    }

    public Order findById(Long aLong) throws ParseException {
        return this.init(orderDao.findById(aLong).get());
    }

    public void save(Order order) {
        orderDao.save(order);
    }

    public void delete(Order order) {
        orderDao.delete(order);
    }
}
