package com.homeService.controllers.user;

import com.homeService.entity.User;
import com.homeService.services.UserService;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
public class UserController {
    @Autowired private UserService userService;

    @GetMapping("/addToCF")
    public @ResponseBody String addToCF(
    Principal principal,
    @RequestParam String productsCart,
    @RequestParam String type
    ) throws Exception {
        if (principal != null) {
            if (type != null && !type.isEmpty()) {
                if (productsCart != null && !productsCart.isEmpty()) {
                    User currentUser = (User) ((Authentication) principal).getPrincipal();
                    HashSet<Long> temp = new HashSet<>();
                    ArrayList<Long> cart = new ArrayList<>();
                    for (String str : productsCart.split("\\s")) cart.add(Long.parseLong(str.trim()));
                    if (type.equals("cart")) {
                        List<Long> f = userService.get(currentUser.getIdProductCartJSON());
                        f.addAll(cart);
                        for (Long l : f) temp.add(l);
                        JSONArray array = new JSONArray();
                        array.addAll(temp);
                        currentUser.setIdProductCartJSON(array.toJSONString());
                    }
                    if (type.equals("favorite")) {
                        List<Long> f = userService.get(currentUser.getIdProductFavoriteJSON());
                        f.addAll(cart);
                        for (Long l : f) temp.add(l);
                        JSONArray array = new JSONArray();
                        array.addAll(temp);
                        currentUser.setIdProductFavoriteJSON(array.toJSONString());
                    }
                    userService.save(currentUser);
                    return "OK";
                }
                return "productsCart is invalid";
            }
            return "param type is invalid";
        }
        return "reg";
    }

    @GetMapping("/removeFromCF")
    public @ResponseBody String removeFromCF(
    Principal principal,
    @RequestParam String productsCart,
    @RequestParam String type
    ) throws Exception {
        if (principal != null) {
            if (type != null && !type.isEmpty()) {
                if (productsCart != null && !productsCart.isEmpty()) {
                    User currentUser = (User) ((Authentication) principal).getPrincipal();
                    ArrayList<Long> cart = new ArrayList<>();
                    for (String str : productsCart.split("\\s")) cart.add(Long.parseLong(str.trim()));
                    JSONArray array = new JSONArray();
                    if (type.equals("cart")) {
                        HashSet<Long> temp = new HashSet<>(userService.get(currentUser.getIdProductCartJSON()));
                        for (Long l : cart) temp.remove(l);
                        array.addAll(temp);
                        currentUser.setIdProductCartJSON(array.toJSONString());
                    }
                    if (type.equals("favorite")) {
                        HashSet<Long> temp = new HashSet<>(userService.get(currentUser.getIdProductFavoriteJSON()));
                        for (Long l : cart) temp.remove(l);
                        array.addAll(temp);
                        currentUser.setIdProductFavoriteJSON(array.toJSONString());
                    }
                    userService.save(currentUser);
                    return "OK";
                }
                return "productsCart is invalid";
            }
            return "param type is invalid";
        }
        return "reg";
    }

    @GetMapping("/getCF")
    public @ResponseBody String getCF(
    Principal principal,
    @RequestParam String type) {
        if (principal != null) {
            User currentUser = (User) ((Authentication) principal).getPrincipal();
            if (type != null && !type.isEmpty()) {
                if (type.equals("cart")) return currentUser.getIdProductCartJSON();
                if (type.equals("favorite")) return currentUser.getIdProductFavoriteJSON();
            }
            return "param type is invalid";
        }
        return "reg";
    }

}
