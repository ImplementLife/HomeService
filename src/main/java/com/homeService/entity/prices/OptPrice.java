package com.homeService.entity.prices;

public class OptPrice {
    private Integer minCount;
    private Price price;

    public OptPrice(Integer minCount, Price price) {
        this.minCount = minCount;
        this.price = price;
    }

    public Integer getMinCount() {
        return minCount;
    }
    public void setMinCount(Integer minCount) {
        this.minCount = minCount;
    }

    public Price getPrice() {
        return price;
    }
    public void setPrice(Price price) {
        this.price = price;
    }

    public Integer getMoney() {
        return price.getMoney();
    }
    public Integer getPenny() {
        return price.getPenny();
    }
    public String getCurrency() {
        return price.getCurrency();
    }
}
