package com.homeService.entity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private Long userId;
    private Long statusId;
    private String userComment;

    @Transient
    private ArrayList<Comment> idComments;

    /**
     * example : {"products" : [{"productId", "count"}], commentsId:[...]}
     */
    private String infoJSON;

    @Transient
    private ArrayList<OrderDetails> orderDetails;

    public Order() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStatusId() {
        return statusId;
    }
    public void setStatusId(Long statusId) {
        this.statusId = statusId;
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

    public String getUserComment() {
        return userComment;
    }
    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public ArrayList<Comment> getIdComments() {
        return idComments;
    }
    public void setIdComments(ArrayList<Comment> idComments) {
        this.idComments = idComments;
    }

    /*===========             JSON             ==============*/

    private JSONObject getJSONObject(String JSON) {
        try {
            return (JSONObject) new JSONParser().parse(JSON);
        } catch (Exception e) {
            return new JSONObject();
        }
    }

    /**
     * Метод для инициализации внутреннего объекта "c информацией по заказу" из JSON строки
     */
    public void initOrderDetails() {
        ArrayList<OrderDetails> tempArray = new ArrayList<>();
        JSONObject object = getJSONObject(infoJSON);
        JSONArray array = (JSONArray) object.get("orderDetails");
        if (array == null) return;
        for (Object o : array) {
            try {
                JSONObject temp = (JSONObject) o;
                OrderDetails value = new OrderDetails();
                value.setCount((long) temp.get("count"));
                value.setProductId((long) temp.get("productId"));
                tempArray.add(value);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        setOrderDetails(tempArray);
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
    }

    public static class OrderDetails {
        private long count;
        private long productId;

        public OrderDetails() {}
        public OrderDetails(int count, long productId) {
            this.count = count;
            this.productId = productId;
        }

        public long getCount() {
            return count;
        }
        public void setCount(long count) {
            this.count = count;
        }

        public long getProductId() {
            return productId;
        }
        public void setProductId(long productId) {
            this.productId = productId;
        }
    }
}
