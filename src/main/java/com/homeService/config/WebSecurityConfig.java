package com.homeService.config;

import com.homeService.services.UserService;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity https) throws Exception {
        https.csrf().disable().authorizeRequests()
                //Доступ только для не зарегистрированных пользователей
                .antMatchers("/registration").not().fullyAuthenticated()

                //Доступ только для пользователям с ролью Администратор
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/manager/**").hasRole("MANAGER")
//                .antMatchers("/").hasRole("USER")

                //Доступ разрешен всем пользователям
                .antMatchers("/**", "/main/**", "/resource/**", "/test/").permitAll()
                .anyRequest().authenticated(); //Все остальные страницы требуют аутентификации

        https.formLogin()
                .permitAll() // даем доступ к форме логина всем
                .loginPage("/login") // указываем страницу с формой логина
                .loginProcessingUrl("/j_spring_security_check") // указываем action с формы логина
                .failureUrl("/login?error") // указываем URL при неудачном логине

                .usernameParameter("j_username") // Указываем параметры логина и пароля с формы логина
                .passwordParameter("j_password");

        https.logout()
                .permitAll()// разрешаем делать логаут всем
                .logoutUrl("/logout")// указываем URL логаута
                .logoutSuccessUrl("/login?logout")// указываем URL при удачном логауте
                .invalidateHttpSession(true); // делаем не валидной текущую сессию
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}
