package com.homeService.controllers.user;

import com.homeService.controllers.common.HeaderController;
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

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired CategoryService categoryService;
    @Autowired ProductService productService;
    @Autowired private HeaderController headerController;

    @GetMapping("/search/new")
    public String searchNew(Model model, Principal principal) {
        headerController.init(model, principal);
        Collection<Product> products = productService.findAllByIsPublic(true);
        model.addAttribute("products", products);
        model.addAttribute("categories", new ArrayList<>());
        model.addAttribute("title", "Новинки");
        //Path
        {
            ArrayList<Path> path = new ArrayList<>();
            path.add(new Path(null, "Новинки"));
            model.addAttribute("path", path);
        }
        return "viewData/catSearch/index";
    }

    @GetMapping("/search/categories")
    public String getCategories(Model model, Principal principal) {
        headerController.init(model, principal);
        model.addAttribute("categories", categoryService.findAll());
        return "viewData/categories/index";
    }
    @GetMapping("/search/categories/{id}")
    public String get(@PathVariable("id") String id, Model model, Principal principal) throws Exception {
        headerController.init(model, principal);
        Category category = categoryService.findById(Long.parseLong(id));
        Long categoryId = category.getId();

        //Path
        {
            ArrayList<Path> path = new ArrayList<>();
            for (Category cat : categoryService.findByIdAndParent(categoryId)) {
                path.add(new Path("/search/categories/" + cat.getId(), cat.getName()));
            }
            model.addAttribute("path", path);
        }

        List<Product> products = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        if (category.isForProduct()) products.addAll(productService.filterIsPublic(productService.findAllByCategoryId(categoryId), true));
        else categories.addAll(categoryService.findAllByParentId(categoryId));

        model.addAttribute("title", category.getName());
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "viewData/catSearch/index";
    }

}
