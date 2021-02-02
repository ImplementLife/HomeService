package com.homeService.controllers.admin;

import com.homeService.controllers.Lib;
import com.homeService.controllers.common.HeaderController;
import com.homeService.entity.Category;
import com.homeService.entity.Product;
import com.homeService.services.CategoryService;
import com.homeService.services.FileService;
import com.homeService.services.ProductService;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ProductController {
    private static final String createProduct = "admin/createProduct/index";
    private static final String updateProduct = "admin/createProduct/index";
    private static final String allProductTable = "admin/tableAllProducts/index";

    @Autowired CategoryService categoryService;
    @Autowired ProductService productService;
    @Autowired FileService fileService;
    @Autowired HeaderController headerController;

    @GetMapping("/updateProduct/{id}")
    public String updateProduct(@PathVariable("id") String id, Model model, Principal principal, HttpServletRequest request) {
        headerController.init(model, principal);
        Product product = productService.findById(Long.parseLong(id));
        model.addAttribute("product", product);
        model.addAttribute("action", "update");
        model.addAttribute("referer", new Lib().ref(request));
        productService.initForUpdate(product, model);
        getCategories(model);
        return createProduct;
    }

    @PostMapping("/updateProduct")
    public String updateProduct(
        @RequestParam(defaultValue="") String id,
        @RequestParam(defaultValue="") String name,
        @RequestParam(defaultValue="") String article,
        @RequestParam(defaultValue="") String description,
        @RequestParam(defaultValue="") String categoryId,
        @RequestParam(defaultValue="") String isPublic,

        @RequestParam(defaultValue="") String characteristics,
        @RequestParam(defaultValue="") String priceList,

        @RequestParam(defaultValue="") String referer,
        Model model, Principal principal
    ) {
        headerController.init(model, principal);
        Product product = productService.findById(Long.parseLong(id));
        {
            product.setName(name);
            product.setArticle(article);
            product.setDescription(description);
            product.setCategoryId(Long.parseLong(categoryId));
            product.setPublic(isPublic.equals("on"));
            product.setCharacteristicsJSON(characteristics);
            product.setPriceListJSON(priceList);
            getCategories(model);
        }
        //Валидация
        {
            if (name.isEmpty())        return createProduct;
            if (categoryId.isEmpty())  return createProduct;
            if (description.isEmpty()) return createProduct;

            if (characteristics.isEmpty()) return createProduct;
            else product.setCharacteristicsJSON(characteristics);
        }
        //Сохранение данных о товаре
        Product savedProduct = productService.saveProduct(product);
        return "redirect:" + referer;
    }

    @PostMapping("/updateVis/{id}")
    public String updateVis(@PathVariable String id, @RequestParam String vis, HttpServletRequest request) {
        try {
            Product product = productService.findById(Long.parseLong(id));
            product.setPublic(Boolean.parseBoolean(vis));
            productService.saveProduct(product);
        } catch (Exception e) { e.printStackTrace(); }
        return "redirect:" + new Lib().ref(request);
    }

    @GetMapping("/createProduct")
    public String getForm(Model model, Principal principal) {
        headerController.init(model, principal);
        getCategories(model);
        Product product = new Product();
        model.addAttribute("action", "create");
        model.addAttribute("product", product);
        return createProduct;
    }

    private void getCategories(Model model) {
        List<Category> categories = categoryService.findAllByIsForProduct(true);
        model.addAttribute("categories", categoryService.initPathAll(categories));
    }

    @PostMapping("/createProduct")
    public String post(
        @RequestParam(defaultValue="") String name,
        @RequestParam(defaultValue="") String article,
        @RequestParam(defaultValue="") String description,
        @RequestParam(defaultValue="") String categoryId,
        @RequestParam(defaultValue="") String isPublic,

        @RequestParam("images[]") MultipartFile[] images,

        @RequestParam(defaultValue="") String characteristics,
        @RequestParam(defaultValue="") String priceList,
        Model model, Principal principal
    ) {
        headerController.init(model, principal);
        Product product = new Product();
        {
            product.setName(name);
            product.setArticle(article);
            product.setDescription(description);
            product.setCategoryId(Long.parseLong(categoryId));
            product.setPublic(isPublic.equals("on"));
            product.setCharacteristicsJSON(characteristics);
            product.setPriceListJSON(priceList);
            getCategories(model);
        }
        //Валидация
        {
            if (name.isEmpty())        return createProduct;
            if (categoryId.isEmpty())  return createProduct;
            if (description.isEmpty()) return createProduct;

            if (characteristics.isEmpty()) return createProduct;
            else product.setCharacteristicsJSON(characteristics);

            if (images.length == 0) {
                model.addAttribute("fileError", "Добавте изображений");
                return createProduct;
            } else {
                try {
                    JSONArray array = new JSONArray();
                    array.addAll(fileService.save(images));
                    product.setImagesJSON(array.toJSONString());
                } catch (IOException e) {
                    model.addAttribute("fileError", "Ошибка в одном из файлов: \" " + e.getMessage() + " \"");
                    return createProduct;
                }
            }
        }

        //Сохранение данных о товаре
        Product savedProduct = productService.saveProduct(product);
        return "redirect:/product/" + savedProduct.getId();
    }

    /*======      REST     =======*/

    @GetMapping("/allProducts")
    public String getAllProducts(Model model, Principal principal) {
        headerController.init(model, principal);
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("article", "");
        return allProductTable;
    }

    @GetMapping("/searchProductByArticle")
    public String searchProductByArticle(Model model, Principal principal, @RequestParam String article) {
        headerController.init(model, principal);
        model.addAttribute("products", productService.searchByArticle(article));
        model.addAttribute("article", article);
        return allProductTable;
    }

    @GetMapping("/deleteProduct/{id}")
    public String getDeleteProduct(@PathVariable String id) {
        productService.delete(Long.parseLong(id));
        return "redirect: /admin/allProducts";
    }

}
