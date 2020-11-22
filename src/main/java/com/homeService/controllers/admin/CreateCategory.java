package com.homeService.controllers.admin;

import com.homeService.entity.Category;
import com.homeService.services.CategoryService;
import com.homeService.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class CreateCategory {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FileService fileService;

    @GetMapping("/admin/createCategory")
    public String get(Model model) {
        model.addAttribute("categories", categoryService.filterIsForProduct(categoryService.findAll(), false));
        return "createCategory/index";
    }

    @PostMapping("/admin/createCategory")
    public String postCategory(
            @RequestParam("name") String name,
            @RequestParam("isForProduct") String isForProduct,
            @RequestParam("parentId") String parentId,
            @RequestParam("image") MultipartFile image,
            Model model
    ) throws IOException {
        //Перезаполнение формы
        model.addAttribute("name", name);

        Category category = new Category();
        //Валидация
        {
            if (name == null || name.isEmpty()) return get(model);
            else category.setName(name);

            if (parentId == null || parentId.isEmpty()) {
                return get(model);
            } else {
                Long id = null;
                try { id = Long.parseLong(parentId);
                } catch (Exception ignore) {}
                category.setParentId(id);
            }
        }
        category.setForProduct(Boolean.parseBoolean(isForProduct));
        category.setNameImage(fileService.save(image));

        categoryService.save(category);

        return "createCategory/index";
    }
}
