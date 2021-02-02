package com.homeService.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.TreeMap;

@Entity
@Table(name = "products")
public class Product implements Comparable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String article;
    private Long categoryId;
    private boolean isPublic;

    @Type(type = "text") private String description;
    @Type(type = "text") private String characteristicsJSON;
    @Type(type = "text") private String priceListJSON;
    @Type(type = "text") private String imagesJSON;

    /*===================================*/
    @Transient private TreeMap<Integer, Discount> optPrices;
    @Transient private ArrayList<String> images;
    @Transient private boolean isFavorite;
    @Transient private boolean isInCart;
    @Transient private String defaultPrice;
    /*===================================*/
    public Product() {}

    /*===   Not @Transient   ===*/

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

    public String getArticle() {
        return article;
    }
    public void setArticle(String article) {
        this.article = article;
    }

    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isPublic() {
        return isPublic;
    }
    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getCharacteristicsJSON() {
        return characteristicsJSON;
    }
    public void setCharacteristicsJSON(String characteristicsJSON) {
        this.characteristicsJSON = characteristicsJSON;
    }

    public String getPriceListJSON() {
        return priceListJSON;
    }
    public void setPriceListJSON(String priceListJSON) {
        this.priceListJSON = priceListJSON;
    }

    public String getImagesJSON() {
        return imagesJSON;
    }
    public void setImagesJSON(String imagesJSON) {
        this.imagesJSON = imagesJSON;
    }

    public String getDefaultPrice() {
        return defaultPrice;
    }
    public void setDefaultPrice(String defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    /*===   @Transient   ===*/
    public TreeMap<Integer, Discount> getOptPrices() {
        return optPrices;
    }
    public void setOptPrices(TreeMap<Integer, Discount> optPrices) {
        this.optPrices = optPrices;
    }

    public final ArrayList<String> getImages() {
        if (images != null) return images;
        return new ArrayList<String>();
    }
    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isInCart() {
        return isInCart;
    }
    public void setInCart(boolean inCart) {
        isInCart = inCart;
    }

    /*===   |   ===*/
    @Override
    public int compareTo(Object o) {
        Product product = (Product) o;
        return (int) (this.id - product.id);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Product) {
            Product product = (Product) o;
            return this.id.equals(product.id);
        } else return false;
    }

    /*===================================*/
    public static class Discount {
        private final int minCount;
        private final int percent;

        public Discount(int minCount, int money) throws Exception {
            if (minCount < 1) throw new Exception("minCount can have value is >= 1;");
            if (money < 0) throw new Exception("Money diapason: [0 - large], received value in money: " + money + ';');

            this.minCount = minCount;
            this.percent = money;
        }

        public int getMinCount() {
            return minCount;
        }
        public int getPercent() {
            return percent;
        }
    }

    /*================      other       ===================*/
    public final String getMainImg() {
        if (images != null) return images.get(0);
        return "none";
    }

}