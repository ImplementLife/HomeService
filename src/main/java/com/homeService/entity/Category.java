package com.homeService.entity;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "categories")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parentId;
    @Transient private ArrayList<Category> doterCategories;
    @Transient private ArrayList<String> path;

    private String name;
    private String nameImage;
    private boolean isForProduct;

    /*====================================*/

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

    public String getNameImage() {
        return nameImage;
    }
    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }

    public boolean isForProduct() {
        return isForProduct;
    }
    public void setForProduct(boolean forProduct) {
        isForProduct = forProduct;
    }

    public ArrayList<String> getPath() {
        return path;
    }
    public void setPath(ArrayList<String> path) {
        this.path = path;
    }

    public ArrayList<Category> getDoterCategories() {
        if (doterCategories == null) doterCategories = new ArrayList<>();
        return doterCategories;
    }
    public void addDoterCategories(ArrayList<Category> doterCategories) {
        getDoterCategories().addAll(doterCategories);
    }
    public void addDoterCategory(Category doterCategory) {
        getDoterCategories().add(doterCategory);
    }
}
