package com.homeService.entity;

import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails, Comparable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Вместо username используется email
     */
    private String username;
    private String name;
    private String password;
    @Transient
    private String passwordConfirm;
    private boolean enabled;
    private boolean accountNonLocked;

    /**
     * example: ["1", "5", ...]
     */
    @Type(type = "text")
    private String idProductCartJSON;
    /**
     * example: ["1", "5", ...]
     */
    @Type(type = "text")
    private String idProductFavoriteJSON;

    /*===================================*/

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    /*===================================*/

    public User() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getIdProductCartJSON() {
        return idProductCartJSON;
    }
    public void setIdProductCartJSON(String idProductCartJSON) {
        this.idProductCartJSON = idProductCartJSON;
    }

    public String getIdProductFavoriteJSON() {
        return idProductFavoriteJSON;
    }
    public void setIdProductFavoriteJSON(String idProductFavoriteJSON) {
        this.idProductFavoriteJSON = idProductFavoriteJSON;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }
    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    /*===================================*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public int compareTo(Object o) {
        User user = (User) o;
        return (int) (this.id - user.id);
    }
}
