package com.homeService.services;

import com.homeService.dao.UserDao;
import com.homeService.entity.Product;
import com.homeService.entity.Role;
import com.homeService.entity.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class UserService implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserDao userDao;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired 
    ProductService productService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found");
        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userDao.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userDao.findAll();
    }

    public boolean saveUser(User user, Role role) {
        User userFromDB = userDao.findByUsername(user.getUsername());

        if (userFromDB != null) return false;

        user.setRoles(Collections.singleton(role));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.saveAndFlush(user);
        return true;
    }

    public boolean saveUser(User user) {
        return saveUser(user, new Role(1L, "ROLE_USER"));
    }

    public <S extends User> S save2(S s) {
        return userDao.save(s);
    }

    public <S extends User> S save(S s) {
        return userDao.saveAndFlush(s);
    }


    public boolean deleteUser(Long userId) {
        if (userDao.findById(userId).isPresent()) {
            userDao.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<User> userGetList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }

    public ArrayList<Long> get(String JSON) throws ParseException {
        ArrayList<Long> res = new ArrayList<>();
        if (JSON == null || JSON.isEmpty()) return res;
        JSONArray array = (JSONArray) new JSONParser().parse(JSON);
        for (Object o : array) res.add(Long.parseLong(o.toString()));
        return res;
    }
    public List<Product> getCartProducts(User user) throws Exception {
        return productService.findAllById(get(user.getIdProductCartJSON()));
    }
    public List<Product> getFavoriteProducts(User user) throws Exception {
        return productService.findAllById(get(user.getIdProductFavoriteJSON()));
    }
}
