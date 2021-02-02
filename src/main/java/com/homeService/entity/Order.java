package com.homeService.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private Long statusId;
    private Long userId;

    /**
     * example : {"products" : [{"productId", "count"}], commentsId:[...]}
     */
    @Type(type = "text")
    private String infoJSON;

    @Transient private OrderStatus orderStatus;
    @Transient private ArrayList<Comment> idComments;
    @Transient private ArrayList<OrderDetails> orderDetails;
    @Transient private UserData userData;
    @Transient private Delivery delivery;
    @Transient private String payMethod;
    @Transient private String userComment;
    @Transient private User user;
    /*==================================*/
    public Order() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFormattedDate() {
        return new SimpleDateFormat("[yyyy.MM.dd HH:mm]").format(date);
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Long getStatusId() {
        return statusId;
    }
    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getUserComment() {
        return userComment;
    }
    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getInfoJSON() {
        return infoJSON;
    }
    public void setInfoJSON(String infoJSON) {
        this.infoJSON = infoJSON;
    }

    public ArrayList<OrderDetails> getOrderDetails() {
        return orderDetails;
    }
    public void setOrderDetails(ArrayList<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public ArrayList<Comment> getIdComments() {
        return idComments;
    }
    public void setIdComments(ArrayList<Comment> idComments) {
        this.idComments = idComments;
    }

    public UserData getUserData() {
        return userData;
    }
    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public Delivery getDelivery() {
        return delivery;
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public String getPayMethod() {
        return payMethod;
    }
    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    /*=======================================================*/
    public static class OrderDetails {
        private String count;
        private String productId;

        public OrderDetails() {}
        public OrderDetails(String count, String productId) {
            this.count = count;
            this.productId = productId;
        }

        public String getCount() {
            return count;
        }
        public void setCount(String count) {
            this.count = count;
        }

        public String getProductId() {
            return productId;
        }
        public void setProductId(String productId) {
            this.productId = productId;
        }
    }
    public static class UserData {
        private String firstName;
        private String lastName;
        private String phone;
        private String email;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
    public static class Delivery {
        private String method;
        private String address;

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    /*===========             JSON             ==============
    private JSONObject getJSONObject(String JSON) {
        try { return (JSONObject) new JSONParser().parse(JSON);
        } catch (Exception e) { return new JSONObject(); }
    }
    public boolean initInfoJSON(ArrayList<OrderDetails> orderDetails) {
        if (orderDetails == null) return false;
        if (orderDetails.size() < 1) return false;
        this.orderDetails = orderDetails;
        return initInfoJSON();
    }
    public boolean initInfoJSON() {
        if (orderDetails == null) return false;
        JSONArray array = new JSONArray();
        for (OrderDetails o : orderDetails) {
            JSONObject temp = new JSONObject();
            temp.put("count", o.getCount());
            temp.put("productId", o.getProductId());
            array.add(temp);
        }
        JSONObject object = getJSONObject(infoJSON);
        object.put("orderDetails", array);
        setInfoJSON(object.toJSONString());
        return true;
    }*/
}
