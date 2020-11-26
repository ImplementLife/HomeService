package com.homeService.controllers.user;

import com.homeService.controllers.common.HeaderController;
import com.homeService.entity.Category;
import com.homeService.entity.Product;
import com.homeService.entity.User;
import com.homeService.lib.Path;
import com.homeService.services.CategoryService;
import com.homeService.services.ProductService;
import com.homeService.services.UserService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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

    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    private HeaderController headerController;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/productPrices/{id}")
    public @ResponseBody String getProductOptPrices(@PathVariable("id") String id) throws Exception {
        Product product = productService.findProductById(Long.parseLong(id));
        JSONArray array = new JSONArray();
        for (Product.OptPrice op : product.getOptPrices().values()) {
            JSONObject temp = new JSONObject();
            temp.put('c', op.getMinCount());
            temp.put('m', op.getMoney());
            array.add(temp);
        }
        return array.toJSONString();
    }

    @GetMapping("/product/{id}")
    public String get(
    Model model,
    Principal principal,
    @PathVariable("id") String id
    ) throws Exception {
        boolean isAdmin = false;
        User currentUser;
        Product product = productService.findProductById(Long.parseLong(id));
        if (principal != null) {
            currentUser = (User) ((Authentication) principal).getPrincipal();
            headerController.init(model, currentUser);
            for (GrantedAuthority GA : currentUser.getAuthorities()) if (GA.getAuthority().equals("ROLE_ADMIN")) isAdmin = true;
            if (userService.getCartProducts(currentUser).contains(product)) product.setInCart(true);
            if (userService.getFavoriteProducts(currentUser).contains(product)) product.setFavorite(true);
        }
        List<Category> categories = categoryService.findByIdAndParent(product.getCategoryId());

        //Path
        {
            ArrayList<Path> path = new ArrayList<>();
            for (Category cat : categoryService.findByIdAndParent(product.getCategoryId())) {
                path.add(new Path("/search/categories/" + cat.getName(), cat.getName()));
            }
            path.add(new Path(null, product.getName()));
            model.addAttribute("path", path);
        }

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("categories", categories);
        model.addAttribute("product", product);
        return "cardProduct/index";
    }


}
