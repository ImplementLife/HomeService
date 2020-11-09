package com.homeService.entity;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;
    private Long userId;
    private Long statusId;

    /**
     *
     * example : {
     *             "products" : [
     *                 "product_$number" : {
     *                     "productId" : @id;
     *                     "count" : val;
     *                     ...
     *                 }
     *             ],
     *             "info" : {...}
     *         }
     */
    private String infoJSON;

    public Order() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
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
}
