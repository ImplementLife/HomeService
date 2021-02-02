package com.homeService.services.transientInitiators;

import com.homeService.dao.OrderStatusDao;
import com.homeService.entity.Order;
import com.homeService.entity.OrderStatus;
import com.homeService.lib.myJSON.MyJsonArray;
import com.homeService.lib.myJSON.MyJsonObject;
import com.homeService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderInitiator {
    @Autowired OrderStatusDao orderStatusDao;
    @Autowired UserService userService;

    public List<Order> fullInitAll(List<Order> list) {
        for (Order order : list) fullInit(order);
        return list;
    }

    public Order fullInit(Order order) {
        initOrderStatus(order);
        initOrderDetails(order);
        initUserData(order);
        initPayMethod(order);
        initDelivery(order);
        initUserComment(order);
        initUser(order);
        return order;
    }

    public void initUser(Order order) {
        order.setUser(userService.findUserById(order.getUserId()));
    }
    private void initOrderStatus(Order order) {
        Optional<OrderStatus> optionalStatus = orderStatusDao.findById(order.getStatusId());
        OrderStatus status = optionalStatus.orElse(new OrderStatus("Новый"));
        order.setOrderStatus(status);
    }
    public void initUserData(Order order) {
        MyJsonObject myJsonObject = new MyJsonObject(new MyJsonObject(order.getInfoJSON()).get("user"));

        Order.UserData userData = new Order.UserData();
        userData.setFirstName(myJsonObject.get("firstName"));
        userData.setLastName(myJsonObject.get("lastName"));
        userData.setPhone(myJsonObject.get("phone"));
        userData.setEmail(myJsonObject.get("email"));
        order.setUserData(userData);
    }
    public void initDelivery(Order order) {
        MyJsonObject myJsonObject = new MyJsonObject(new MyJsonObject(order.getInfoJSON()).get("delivery"));

        Order.Delivery delivery = new Order.Delivery();
        delivery.setMethod(myJsonObject.get("method"));
        delivery.setAddress(myJsonObject.get("address"));
        order.setDelivery(delivery);
    }
    public void initPayMethod(Order order) {
        MyJsonObject myJsonObject = new MyJsonObject(order.getInfoJSON());
        order.setPayMethod(myJsonObject.get("payMethod"));
    }
    public void initUserComment(Order order) {
        MyJsonObject myJsonObject = new MyJsonObject(order.getInfoJSON());
        order.setUserComment(myJsonObject.get("comment"));
    }
    public void initOrderDetails(Order order) {
        MyJsonArray arr = new MyJsonArray(new MyJsonObject(order.getInfoJSON()).get("ProductsInfo"));
        ArrayList<Order.OrderDetails> tempArray = new ArrayList<>();
        for (String str : arr) {
            MyJsonObject myJsonObject = new MyJsonObject(str);
            tempArray.add(new Order.OrderDetails(myJsonObject.get("count"), myJsonObject.get("id")));
        }
        order.setOrderDetails(tempArray);
    }
}
