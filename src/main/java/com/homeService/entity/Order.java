package com.homeService.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.TreeSet;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private Long userId;
    private Long statusId;

    /**
     * example : {"products" : [{"productId", "count"}],
     * "info",
     * }
     */
    private String infoJSON;

    @Transient
    private TreeSet<OrderDetails> orderDetails;

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

    public TreeSet<OrderDetails> getOrderDetails() {
        return orderDetails;
    }
    public void setOrderDetails(TreeSet<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }


}
