package com.homeService.controllers.user;

import com.homeService.controllers.common.PathController;
import com.homeService.entity.Category;
import com.homeService.entity.Product;
import com.homeService.lib.Path;
import com.homeService.services.CategoryService;
import com.homeService.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Collection;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @GetMapping("/search/categories")
    public String getCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "categories/index";
    }
    @GetMapping("/search/categories/{name}")
    public String get(@PathVariable("name") String name, Model model) throws Exception {
        Category category = categoryService.findByName(name);
        Long categoryId = category.getId();

        //Path
        {
            ArrayList<Path> path = new ArrayList<>();
            for (Category cat : categoryService.findByIdAndParent(categoryId)) {
                path.add(new Path("/search/categories/" + cat.getName(), cat.getName()));
            }
            model.addAttribute("path", path);
        }

        Collection<Product> products = new ArrayList<>();
        Collection<Category> categories = new ArrayList<>();
        if (category.isForProduct()) {
            products.addAll(productService.isPublicFilter(productService.findAllByCategoryId(categoryId), true));
        } else {
            categories.addAll(categoryService.findAllByParentId(categoryId));
        }

        model.addAttribute("title", name);
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "catSearch/index";
    }

}
