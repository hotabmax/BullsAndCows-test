package com.hotabmax.application.filters;

import com.hotabmax.application.models.User;
import com.hotabmax.application.servicesJPA.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class FilterAuthenticationTest {
    @Autowired
    private UserService userService;
    @Autowired
    private FilterAuthentication filterAuthentication;

    private String jws;
    private Cookie cookie;
    private MockMvc mockMvc;
    private Cookie[] cookies = new Cookie[1];

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void autentificationTestIfCookieTrue() throws IOException {
        try {
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            userService.create(new User("Тест", "123"));
            jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
            cookie = new Cookie("JWT", jws);
            cookies[0] = cookie;
            assertEquals("player", filterAuthentication.autentification(cookies, key));

            userService.deleteByLogin("Тест");
            assertEquals("autorizationAndRegistration",
                    filterAuthentication.autentification(cookies, key));

        } catch (Exception exc){
            userService.deleteByLogin("Тест");
        }
    }
    @Test
    void autentificationTestIfCookieFalse() throws IOException{
        try {
            userService.create(new User("Тест", "123"));
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
            key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            cookie = new Cookie("JWT", jws);
            cookies[0] = cookie;
            assertEquals("autorizationAndRegistration",
                    filterAuthentication.autentification(cookies, key));
            userService.deleteByLogin("Тест");
        } catch (Exception exc){
            userService.deleteByLogin("Тест");
        }
    }

    @Test
    void autentificationTestIfCookieConsistEmptyString() throws IOException{
        try {
            userService.create(new User("Тест", "123"));
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            jws = Jwts.builder().setSubject(" ").signWith(key).compact();
            key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            cookie = new Cookie("JWT", jws);
            cookies[0] = cookie;
            assertEquals("autorizationAndRegistration",
                    filterAuthentication.autentification(cookies, key));
            userService.deleteByLogin("Тест");
        } catch (Exception exc){
            userService.deleteByLogin("Тест");
        }
    }

    @Test
    void autentificationTestIfNoUser() throws IOException{
        try {
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
            key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            cookie = new Cookie("JWT", jws);
            cookies[0] = cookie;
            assertEquals("autorizationAndRegistration",
                    filterAuthentication.autentification(cookies, key));
            userService.deleteByLogin("Тест");
        } catch (Exception exc){
            userService.deleteByLogin("Тест");
        }
    }
}