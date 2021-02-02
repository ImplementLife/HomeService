package com.homeService.entity;

import javax.persistence.*;

@Entity
@Table(name = "order_status")
public class OrderStatus {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public OrderStatus() {}
    public OrderStatus(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
