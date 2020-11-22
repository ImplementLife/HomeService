package com.homeService.services;

import com.homeService.dao.ProductDao;
import com.homeService.entity.Product;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Во время реализации не забывать про необходимость преобразовывать JSON в объекты
 */
@Component
public class ProductService {
    @Autowired
    ProductDao productDao;

    public ProductService() {}

    /*===========      JSON  Images    ==============*/

    private JSONObject getJSONObject(String JSON) {
        JSONObject object;
        try {
            object = (JSONObject) new JSONParser().parse(JSON);
            if (object == null) object = new JSONObject();
        } catch (Exception e) {
            object = new JSONObject();
        }
        return object;
    }

    /**
     * Возвращает все названия изображений относящихся к данному товару
     *
     * @param product
     * @return
     * @throws ParseException
     */
    public ArrayList<String> getNamesImages(Product product) {
        JSONObject object = getJSONObject(product.getInfoJSON());
        return (ArrayList) object.get("images");
    }

    /**
     * Заменяет существующие имена изображений относящихся
     * к данному товару на те что будут переданы в коллекции
     *
     * @param product
     * @param names
     * @return
     * @throws ParseException
     */
    public Product setNamesImages(Product product, List<String> names) {
        JSONObject object = getJSONObject(product.getInfoJSON());
        JSONArray array = new JSONArray();
        array.addAll(names);
        object.put("images", array);
        product.setInfoJSON(object.toJSONString());
        return product;
    }

    /**
     * Добавляет к существующиим именам изображений относящихся
     * к данному товару переданное имя
     *
     * @param product
     * @param name
     * @return
     * @throws ParseException
     */
    public Product addNameImage(Product product, String name) {
        JSONObject object = getJSONObject(product.getInfoJSON());
        JSONArray array = (JSONArray) object.get("images");
        array.add(name);
        object.put("images", array);
        product.setInfoJSON(object.toJSONString());
        return product;
    }

    /*===========      JSON  Prices    ==============*/

    /**
     * Метод для инициализации внутреннего объекта "Оптовых цен" из JSON строки
     *
     * @param product
     * @return
     * @throws Exception
     */
    public Product initOptPrices(Product product) throws Exception {
        JSONObject object = getJSONObject(product.getInfoJSON());
        TreeMap<Integer, Product.OptPrice> optPrices = new TreeMap<>();
        JSONArray array = (JSONArray) object.get("optPrices");
        for (Object o : array) {
            JSONObject temp = (JSONObject) o;
            int count = Integer.parseInt(temp.get("count").toString());
            int money = Integer.parseInt(temp.get("money").toString());
            String currency = (String) temp.get("currency");
            Product.OptPrice value = new Product.OptPrice(count, money, currency);
            optPrices.put(count, value);
        }
        product.setOptPrices(optPrices);
        return product;
    }

    /**
     * Метод для преобразования внутреннего объекта "Оптовых цен" в JSON строку
     *
     *
     * @param product
     * @return
     */
    public Product setOptPrices(Product product) {
        JSONObject object = getJSONObject(product.getInfoJSON());
        JSONArray array = new JSONArray();
        for (Product.OptPrice o : product.getOptPrices().values()) {
            JSONObject temp = new JSONObject();
            temp.put("count", o.getMinCount());
            temp.put("money", o.getMoney());
            temp.put("currency", o.getCurrency());
            array.add(temp);
        }
        object.put("optPrices", array);
        product.setInfoJSON(object.toJSONString());
        return product;
    }

    /*===============================================*/

    public Collection<Product> isPublicFilter(Collection<Product> products, boolean isPublic) {
        Collection<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.isPublic() == isPublic) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    public Product findProductById(Long id) throws Exception {
        Optional<Product> userFromDb = productDao.findById(id);
        Product product = userFromDb.get();
        this.initOptPrices(product);
        product.setImages(this.getNamesImages(product));
        return product;
    }

    public Collection<Product> findAllByCategoryId(Long id) throws Exception {
        Collection<Product> products = productDao.findAllByCategoryId(id);
        for (Product p : products) {
            this.initOptPrices(p);
            p.setImages(this.getNamesImages(p));
        }
        return products;
    }

    public Collection<Product> allProducts() throws Exception {
        Collection<Product> products = productDao.findAll();
        for (Product p : products) {
            this.initOptPrices(p);
            p.setImages(this.getNamesImages(p));
        }
        return products;
    }

    public Product saveProduct(Product product) {
        this.setOptPrices(product);
        this.setNamesImages(product, product.getImages());
        return productDao.saveAndFlush(product);
    }

    public boolean delete(Long id) {
        if (productDao.findById(id).isPresent()) {
            productDao.deleteById(id);
            return true;
        }
        return false;
    }

}
