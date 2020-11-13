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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Controller
public class CreateProduct {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    FileService fileService;

    @GetMapping("/admin/createProduct")
    public String getCreateProduct(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "createProduct/index";
    }

    @PostMapping("/admin/createProduct")
    public String postCreateProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("categoryId") String categoryId,
            @RequestParam("count_1") String count_1,
            @RequestParam("price_1") String price_1,
            @RequestParam("count_2") String count_2,
            @RequestParam("price_2") String price_2,
            @RequestParam("count_3") String count_3,
            @RequestParam("price_3") String price_3,
            @RequestParam("images[]") MultipartFile[] images,
            Model model
    ) {
        model.addAttribute("name", name);
        model.addAttribute("description", description);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("count_1", count_1);
        model.addAttribute("price_1", price_1);
        model.addAttribute("count_2", count_2);
        model.addAttribute("price_2", price_2);
        model.addAttribute("count_3", count_3);
        model.addAttribute("price_3", price_3);

        if (categoryId == null || categoryId.isEmpty()) {
            return "createProduct/index";
        }

        fileService.save(images);

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setCategoryId(Long.parseLong(categoryId));
        TreeMap<Integer, Product.OptPrice> optPrices = new TreeMap<>();
        try {
            optPrices.put(Integer.parseInt(count_1), new Product.OptPrice(Integer.parseInt(count_1), Integer.parseInt(price_1), "грн"));
            optPrices.put(Integer.parseInt(count_2), new Product.OptPrice(Integer.parseInt(count_2), Integer.parseInt(price_2), "грн"));
            optPrices.put(Integer.parseInt(count_3), new Product.OptPrice(Integer.parseInt(count_3), Integer.parseInt(price_3), "грн"));
        } catch (Exception e) {
            model.addAttribute("priceWrong", e.getMessage());
            return "createProduct/index";
        }
        product.setOptPrices(optPrices);

        Product savedProduct = productService.saveProduct(product);
        long id = savedProduct.getId();
        return "redirect:/productCard/" + id;
    }


}
