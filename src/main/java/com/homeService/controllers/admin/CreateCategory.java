package com.homeService.controllers.admin;

import com.homeService.controllers.common.HeaderController;
import com.homeService.entity.Category;
import com.homeService.services.CategoryService;
import com.homeService.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class CreateCategory {
    private static final String createCategory = "admin/createCategory/index";

    @Autowired private CategoryService categoryService;
    @Autowired private FileService fileService;
    @Autowired private HeaderController headerController;

    @GetMapping("/createCategory")
    public String getCreateCategory(Model model, Principal principal) {
        headerController.init(model, principal);
        List<Category> categories = categoryService.findAllByIsForProduct(false);
        model.addAttribute("categories", categoryService.initPathAll(categories));
        return createCategory;
    }

    @PostMapping("/createCategory")
    public String postCreateCategory(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String isForProduct,
            @RequestParam(defaultValue = "") String parentId,
            @RequestParam("image") MultipartFile image,
            Model model, Principal principal
    ) {
        headerController.init(model, principal);
        //Перезаполнение формы
        model.addAttribute("name", name);

        Category category = new Category();
        category.setName(name);
        model.addAttribute("category", category);
        //Валидация
        {
            if (name.isEmpty()) return getCreateCategory(model, principal);
            if (parentId.isEmpty()) {
                return getCreateCategory(model, principal);
            } else {
                Long id = null;
                try { id = Long.parseLong(parentId);
                } catch (Exception ignore) {}
                category.setParentId(id);
            }
            if (image != null) {
                try {category.setNameImage(fileService.save(image));
                } catch (Exception ignore) {}
            }
        }
        category.setForProduct(Boolean.parseBoolean(isForProduct));

        try {
            categoryService.save(category);
        } catch (Exception e) {
            model.addAttribute("saveException", e.getMessage());
            return createCategory;
        }
        model.addAttribute("categories", categoryService.filterIsForProduct(categoryService.findAll(), false));
        return "redirect:/admin/createCategory";
    }

    @GetMapping("/allCategories")
    public String getAllCategories(Model model, Principal principal) {
        headerController.init(model, principal);
        model.addAttribute("categories", categoryService.initPathAll(categoryService.findAll()));
        return "admin/tableAllCategory/index";
    }
}
