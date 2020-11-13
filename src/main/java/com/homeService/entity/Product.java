package com.homeService.entity;

import javax.persistence.*;
import java.util.ArrayList;
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
    private String countStatus;

    /**
     * example : {
     *    "images" : [ name1, name2, ...];
     *    "optPrices" : [{"count", "money", "currency"}, ...];
     *    ...
     * }
     */
    private String infoJSON;

    @Transient
    private TreeMap<Integer, OptPrice> optPrices;
    @Transient
    private ArrayList<String> images;

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

    public String getCountStatus() {
        return countStatus;
    }
    public void setCountStatus(String countStatus) {
        this.countStatus = countStatus;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    /*===================================*/
    public static class OptPrice {
        private final int minCount;
        private final int money;
        private final String currency;

        public OptPrice(int minCount, int money, String currency) throws Exception {
            if (minCount < 1) throw new Exception("minCount can have value is >= 1;");
            if (money < 0) throw new Exception("Money diapason: [0 - large], received value in money: " + money + ';');
            if (currency == null || currency.isEmpty()) throw new Exception("currency not can empty or null");

            this.currency = currency;
            this.minCount = minCount;
            this.money = money;
        }

        public int getMinCount() {
            return minCount;
        }
        public int getMoney() {
            return money;
        }
        public String getCurrency() {
            return currency;
        }
    }

    /*================      other            ===================*/
    public final String getMainImg() {
        if (images != null) return images.get(0);
        return "none";
    }
    public final ArrayList<String> getImages() {
        if (images != null) return images;
        return new ArrayList<String>();
    }
    public final OptPrice getSinglePrice() {
        return optPrices.get(1);
    }

    /*================      to override      ===================*/
    public boolean isFavorite() {
        return false;
    }

}