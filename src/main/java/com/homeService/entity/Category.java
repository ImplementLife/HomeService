package com.homeService.entity;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parentId;
    private String name;
    /**
     * example : { "image", "info_text" }
     */
    private String infoJSON;

    public Category() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getInfoJSON() {
        return infoJSON;
    }
    public void setInfoJSON(String infoJSON) {
        this.infoJSON = infoJSON;
    }
}
