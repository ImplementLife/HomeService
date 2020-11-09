package com.homeService.entity;

import com.homeService.entity.prices.OptPrice;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long categoryId;
    private String name;
    private String description;
    private boolean isPublic;

    /**
     * example : {
     *    "images" : [ name1, name2, ...];
     *    "optPrices" : [{"count", "money", "penny", "currency"}, ...];
     *    ...
     * }
     */
    private String infoJSON;

    @Transient
    private TreeMap<Integer, OptPrice> optPrices;

    /*===================================*/

    @Transient
    @ManyToMany(mappedBy = "favoriteProducts")
    private Set<User> favorite;

    @Transient
    @ManyToMany(mappedBy = "cartProducts")
    private Set<User> cart;

    /*===================================*/

    public Product() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublic() {
        return isPublic;
    }
    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getInfoJSON() {
        return infoJSON;
    }
    public void setInfoJSON(String infoJSON) {
        this.infoJSON = infoJSON;
    }

    public TreeMap<Integer, OptPrice> getOptPrices() {
        return optPrices;
    }
    public void setOptPrices(TreeMap<Integer, OptPrice> optPrices) {
        this.optPrices = optPrices;
    }

}
