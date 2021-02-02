package com.homeService.services.transientInitiators;

import com.homeService.entity.Product;
import com.homeService.lib.myJSON.MyJsonArray;
import com.homeService.lib.myJSON.MyJsonObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.TreeMap;

@Component
public class ProductInitiator {
    public Product fullInit(Product product) {
        initPrices(product);
        initImages(product);
        return product;
    }
    public Product initImages(Product product) {
        ArrayList<String> images = new ArrayList<>();
        MyJsonArray myJsonArray = new MyJsonArray(product.getImagesJSON());
        for (String str : myJsonArray) images.add(str);
        product.setImages(images);
        return product;
    }
    public Product initPrices(Product product) {
        MyJsonObject myJsonObject = new MyJsonObject(product.getPriceListJSON());
        product.setDefaultPrice(myJsonObject.get("defaultPrice"));
        TreeMap<Integer, Product.Discount> optPrices = new TreeMap<>();
        for (MyJsonObject o : myJsonObject.getArray("discounts").getObjects()) {
            try {
                optPrices.put(
                        Integer.parseInt(o.get("count")),
                        new Product.Discount(
                                Integer.parseInt(o.get("count")),
                                Integer.parseInt(o.get("value"))
                        )
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        product.setOptPrices(optPrices);
        return product;
    }
}
