package com.homeService.entity;

import javax.persistence.*;

@Entity
@Table(name = "promotions")
public class Promotion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String link;
    private String namePhoto;

    public Promotion() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }

    public String getNamePhoto() {
        return namePhoto;
    }
    public void setNamePhoto(String namePhoto) {
        this.namePhoto = namePhoto;
    }
}
