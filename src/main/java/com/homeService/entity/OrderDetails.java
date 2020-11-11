package com.homeService.entity;

public class OrderDetails {
    private int count;
    private long productId;

    public OrderDetails() {}

    public OrderDetails(int count, long productId) {
        this.count = count;
        this.productId = productId;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public long getProductId() {
        return productId;
    }
    public void setProductId(long productId) {
        this.productId = productId;
    }
}
