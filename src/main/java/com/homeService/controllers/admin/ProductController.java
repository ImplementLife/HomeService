package com.homeService.controllers.admin;

import com.homeService.entity.Category;
import com.homeService.entity.Product;
import com.homeService.services.CategoryService;
import com.homeService.services.FileService;
import com.homeService.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

@Controller
public class ProductController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    FileService fileService;

    @PostMapping("/admin/updateProduct/{id}")
    public String updateProduct(
            @PathVariable("id") String id,
            @RequestParam("vis") String vis
    ) {
        try {
            Product product = productService.findProductById(Long.parseLong(id));
            product.setPublic(Boolean.parseBoolean(vis));
            productService.saveProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/product/" + id;
    }

    @GetMapping("/admin/createProduct")
    public String get(Model model) {
        getCategories(model);
        return "createProduct/index";
    }

    private void getCategories(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categoryService.filterIsForProduct(categories, true));
    }

    @PostMapping("/admin/createProduct")
    public String post(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("categoryId") String categoryId,
            @RequestParam("isPublic") String isPublic,
            @RequestParam("count_1") String count_1,
            @RequestParam("price_1") String price_1,
            @RequestParam("count_2") String count_2,
            @RequestParam("price_2") String price_2,
            @RequestParam("count_3") String count_3,
            @RequestParam("price_3") String price_3,
            @RequestParam("images[]") MultipartFile[] images,
            Model model
    ) {
        //Перезаполнение формы
        {
            model.addAttribute("name", name);
            model.addAttribute("description", description);
            model.addAttribute("categoryId", categoryId);
            model.addAttribute("count_1", count_1);
            model.addAttribute("price_1", price_1);
            model.addAttribute("count_2", count_2);
            model.addAttribute("price_2", price_2);
            model.addAttribute("count_3", count_3);
            model.addAttribute("price_3", price_3);
            getCategories(model);
        }

        Product product = new Product();
        //Валидация
        {
            if (name == null || name.isEmpty()) return "createProduct/index";
            else product.setName(name);

            if (categoryId == null || categoryId.isEmpty()) return "createProduct/index";
            else product.setCategoryId(Long.parseLong(categoryId));

            if (description == null || description.isEmpty()) return "createProduct/index";
            else product.setDescription(description);

            if (images.length == 0) {
                model.addAttribute("fileError", "Добавте изображений");
                return "createProduct/index";
            } else {
                try {
                    product.setImages(fileService.save(images));
                } catch (IOException e) {
                    model.addAttribute("fileError", "Ошибка в одном из файлов: \" " + e.getMessage() + " \"");
                    return "createProduct/index";
                }
            }

            try {
                TreeMap<Integer, Product.OptPrice> optPrices = new TreeMap<>();
                optPrices.put(Integer.parseInt(count_1), new Product.OptPrice(Integer.parseInt(count_1), Integer.parseInt(price_1), "грн"));
                optPrices.put(Integer.parseInt(count_2), new Product.OptPrice(Integer.parseInt(count_2), Integer.parseInt(price_2), "грн"));
                optPrices.put(Integer.parseInt(count_3), new Product.OptPrice(Integer.parseInt(count_3), Integer.parseInt(price_3), "грн"));
                product.setOptPrices(optPrices);
            } catch (Exception e) {
                model.addAttribute("priceWrong", "Неправильно заполнена форма оптовых цен");
                return "createProduct/index";
            }
        }

        //Сохранение данных о товаре
        boolean b = false;
        if (isPublic.equals("on")) b = true;

        product.setPublic(b);
        Product savedProduct = productService.saveProduct(product);
        return "redirect:/product/" + savedProduct.getId();
    }


}
