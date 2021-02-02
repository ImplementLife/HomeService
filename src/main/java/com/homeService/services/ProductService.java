package com.homeService.services;

import com.homeService.dao.ProductDao;
import com.homeService.entity.Product;
import com.homeService.lib.myJSON.MyJsonArray;
import com.homeService.lib.myJSON.MyJsonObject;
import com.homeService.services.transientInitiators.ProductInitiator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Во время реализации не забывать про необходимость преобразовывать JSON в объекты
 */
@Component
public class ProductService {
    @Autowired ProductDao productDao;
    @Autowired ProductInitiator initiator;

    public Product findById(Long id) {
        Optional<Product> userFromDb = productDao.findById(id);
        return initiator.fullInit(userFromDb.get());
    }

    public List<Product> getAllProducts() {
        List<Product> result = productDao.findAll();
        for (Product product : result) initiator.fullInit(product);
        return result;
    }
    public List<Product> findAllById(Iterable<Long> iterable) {
        List<Product> result = productDao.findAllById(iterable);
        for (Product product : result) initiator.fullInit(product);
        return result;
    }
    public List<Product> findAllByCategoryId(Long id) {
        List<Product> result = productDao.findAllByCategoryId(id);
        for (Product product : result) initiator.fullInit(product);
        return result;
    }
    public List<Product> findAllByIsPublic(boolean isPublic) {
        List<Product> result = productDao.findAllByIsPublic(isPublic);
        for (Product product : result) initiator.fullInit(product);
        return result;
    }
    public List<Product> filterIsPublic(List<Product> products, boolean isPublic) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) if (p.isPublic() == isPublic) result.add(p);
        return result;
    }

    public Product saveProduct(Product product) {
        return productDao.save(product);
    }
    public boolean delete(Long id) {
        if (productDao.findById(id).isPresent()) {
            productDao.deleteById(id);
            return true;
        }
        return false;
    }

    /*=================*/
    public List<Product> searchByArticle(String article) {
        TreeMap<String, Product> sortResult = new TreeMap<>();
        for (Product p : getAllProducts()) {
            if (p.getArticle().equals(article)) {
                sortResult.put(p.getArticle(), p);
            } else if (p.getArticle().contains(article)) {
                sortResult.put(p.getArticle(), p);
            }
        }
        return new ArrayList<Product>(sortResult.values());
    }

    /*=================*/
    public void initForUpdate(Product product, Model model) {
        parseCharacteristics(product, model);
        parsePrices(product, model);
    }
    public void parseCharacteristics(Product product, Model model) {
        ArrayList<ArrayList<String>> table = new ArrayList<>();
        for (String tr : new MyJsonArray(product.getCharacteristicsJSON())) {
            ArrayList<String> arrayTr = new ArrayList<>();
            for (String td : new MyJsonArray(tr)) arrayTr.add(td);
            table.add(arrayTr);
        }
        model.addAttribute("table", table);
    }
    public void parsePrices(Product product, Model model) {
        MyJsonObject object = new MyJsonObject(product.getPriceListJSON());
        model.addAttribute("defaultPrice", object.get("defaultPrice"));
        ArrayList<Prices> prices = new ArrayList<>();
        for (String s : object.getArray("discounts")) {
            Prices p = new Prices();
            MyJsonObject temp = new MyJsonObject(s);
            p.count = temp.get("count");
            p.value = temp.get("value");
            prices.add(p);
        }
        model.addAttribute("prices", prices);
    }
    static class Prices {
        String count;
        String value;

        public String getCount() {
            return count;
        }
        public void setCount(String count) {
            this.count = count;
        }

        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
    }
}
