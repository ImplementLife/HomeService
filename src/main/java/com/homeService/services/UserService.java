package com.homeService.services;

import com.homeService.dao.UserDao;
import com.homeService.entity.Role;
import com.homeService.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserService implements UserDetailsService {


    public User getUserById(Long id) {
        return userDao.getOne(id);
    }

    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserDao userDao;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

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


}
