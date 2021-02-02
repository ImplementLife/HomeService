package com.homeService.controllers.user;

import com.homeService.controllers.common.HeaderController;
import com.homeService.entity.Category;
import com.homeService.entity.Product;
import com.homeService.lib.Path;
import com.homeService.lib.myJSON.MyJsonArray;
import com.homeService.services.CategoryService;
import com.homeService.services.ProductService;
import com.homeService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CardProductController {
    @Autowired UserService userService;
    @Autowired ProductService productService;
    @Autowired CategoryService categoryService;
    @Autowired HeaderController headerController;

    @GetMapping("/productPrices/{id}")
    public @ResponseBody String getProductDiscounts(@PathVariable String id) {
        Product product = productService.findById(Long.parseLong(id));
        return product.getPriceListJSON();
    }

    @GetMapping("/product/{id}")
    public String get(@PathVariable String id, Principal principal, Model model) throws Exception {
        headerController.init(model, principal);
        Product product = productService.findById(Long.parseLong(id));
        /*
        boolean isAdmin = false;
        User currentUser;
        if (principal != null) {
            currentUser = (User) ((Authentication) principal).getPrincipal();
            headerController.initForCurrentUser(model, currentUser);
            for (GrantedAuthority GA : currentUser.getAuthorities()) if (GA.getAuthority().equals("ROLE_ADMIN")) isAdmin = true;
 !!!!         -->  if (userService.getCartProducts(currentUser).contains(product)) product.setInCart(true);
 !!!!         -->  if (userService.getFavoriteProducts(currentUser).contains(product)) product.setFavorite(true);
        }*/

        //Path
        {
            ArrayList<Path> path = new ArrayList<>();
            for (Category cat : categoryService.findByIdAndParent(product.getCategoryId())) {
                path.add(new Path("/search/categories/" + cat.getId(), cat.getName()));
            }
            path.add(new Path(null, product.getName()));
            model.addAttribute("path", path);
        }
        {
            ArrayList<List<String>> table = new ArrayList<>();
            for (String str : new MyJsonArray(product.getCharacteristicsJSON())) {
                table.add(new MyJsonArray(str).get());
            }
            model.addAttribute("characteristics", table);
        }

        model.addAttribute("product", product);
        return "viewData/cardProduct/index";
    }

}
