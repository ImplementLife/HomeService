package com.homeService.services;

import com.homeService.dao.ProductDao;
import com.homeService.entity.Product;
import com.homeService.entity.prices.OptPrice;
import com.homeService.entity.prices.Price;
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
public class ProductServices {
    @Autowired
    ProductDao productDao;

    public ProductServices() {}

    /*===========      JSON  Images    ==============*/

    /**
     * Возвращает все названия изображений относящихся к данному товару
     *
     * @param product
     * @return
     * @throws ParseException
     */
    public List<String> getNamesImages(Product product) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(product.getInfoJSON());
        return (ArrayList) object.get("images");
    }

    /**
     * Заменяет существующие именя изображений относящихся
     * к данному товару на те что будут переданы в коллекции
     *
     * @param product
     * @param names
     * @return
     * @throws ParseException
     */
    public Product setNamesImages(Product product, List<String> names) throws ParseException {
        JSONObject object = (JSONObject) new JSONParser().parse(product.getInfoJSON());
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
    public Product addNameImage(Product product, String name) throws ParseException {
        JSONObject object = (JSONObject) new JSONParser().parse(product.getInfoJSON());
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
        TreeMap<Integer, OptPrice> optPrices = new TreeMap<>();
        JSONObject object = (JSONObject) new JSONParser().parse(product.getInfoJSON());
        JSONArray array = (JSONArray) object.get("optPrices");
        for (Object o : array) {
            JSONObject temp = (JSONObject) o;
            Integer key = (Integer) temp.get("count");
            Integer money = (Integer) temp.get("money");
            Integer penny = (Integer) temp.get("penny");
            String currency = (String) temp.get("currency");
            OptPrice value = new OptPrice(key, new Price(money, penny, currency));
            optPrices.put(key, value);
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
     * @throws ParseException
     */
    public Product setOptPrices(Product product) throws ParseException {
        JSONObject object = (JSONObject) new JSONParser().parse(product.getInfoJSON());
        JSONArray array = new JSONArray();
        for (OptPrice o : product.getOptPrices().values()) {
            JSONObject temp = new JSONObject();
            temp.put("count", o.getMinCount());
            temp.put("money", o.getMoney());
            temp.put("penny", o.getPenny());
            temp.put("currency", o.getCurrency());
            array.add(temp);
        }
        object.put("optPrices", array);
        product.setInfoJSON(object.toJSONString());
        return product;
    }

    /*===============================================*/

    public Product findProductById(Long id) throws Exception {
        Optional<Product> userFromDb = productDao.findById(id);
        Product product = userFromDb.get();
        this.initOptPrices(product);
        return product;
    }

    public Collection<Product> allProducts() throws Exception {
        Collection<Product> products = productDao.findAll();
        for (Product p : products) {
            this.initOptPrices(p);
        }
        return products;
    }

    public boolean saveProduct(Product product) throws ParseException {
//        Set<Product> temp = productDao.findAllByName(product.getName());
//        if (temp != null && !temp.isEmpty()) {
//            return "Товар с таким именем уже существует, продолжить?";
//        }
        this.setOptPrices(product);
        productDao.saveAndFlush(product);
        return true;
    }

    public boolean delete(Long id) {
        if (productDao.findById(id).isPresent()) {
            productDao.deleteById(id);
            return true;
        }
        return false;
    }

}
