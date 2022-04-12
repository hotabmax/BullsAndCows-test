package com.hotabmax.application.filters;

import com.hotabmax.application.models.User;
import com.hotabmax.application.servicesJPA.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.Cookie;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilterRegistrationTest {

    @Autowired
    private UserService userService;
    @Autowired
    private FilterRegistration filterRegistration;

    private String jws;
    private Cookie cookie;
    private Cookie[] cookies = new Cookie[1];
    private StringBuilder baosLogin = new StringBuilder();
    private StringBuilder baosPassword = new StringBuilder();
    @Test
    void registrationIfCookieIfNullTestIfCookieTrue() throws IOException {
        try {
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            userService.create(new User("Тест", "123"));
            jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
            cookie = new Cookie("JWT", jws);
            cookies[0] = cookie;
            assertEquals("player",
                    filterRegistration.registrationIfCookieIfNull(cookies, key,
                            baosLogin, baosPassword).getResultPage());
            userService.deleteByLogin("Тест");
        } catch (Exception exc){
            userService.deleteByLogin("Тест");
        }
    }
    @Test
    void registrationIfCookieIfNullTestIfCookieFalse() throws IOException{
        try {
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            userService.create(new User("Тест", "123"));
            jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
            key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            cookie = new Cookie("JWT", jws);
            cookies[0] = cookie;
            assertEquals("autorizationAndRegistration",
                    filterRegistration.registrationIfCookieIfNull(cookies, key,
                            baosLogin, baosPassword).getResultPage());
            userService.deleteByLogin("Тест");
        } catch (Exception exc){
            userService.deleteByLogin("Тест");
        }
    }

    @Test
    void autentificationTestIfCookieConsistEmptyString() throws IOException{
        try {
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            userService.create(new User("Тест", "123"));
            jws = Jwts.builder().setSubject(" ").signWith(key).compact();
            cookie = new Cookie("JWT", jws);
            cookies[0] = cookie;
            assertEquals("autorizationAndRegistration",
                    filterRegistration.registrationIfCookieIfNull(cookies, key,
                            baosLogin, baosPassword).getResultPage());
            userService.deleteByLogin("Тест");
        } catch (Exception exc){
            userService.deleteByLogin("Тест");
        }
    }

    @Test
    void autentificationTestIfNoUserButCookieTrue() throws IOException{
        try {
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
            cookie = new Cookie("JWT", jws);
            cookies[0] = cookie;
            assertEquals("autorizationAndRegistration",
                    filterRegistration.registrationIfCookieIfNull(cookies, key,
                            baosLogin, baosPassword).getResultPage());
            userService.deleteByLogin("Тест");
        } catch (Exception exc){
            userService.deleteByLogin("Тест");
        }
    }

    @Test
    void autentificationTestIfUserNotExist() throws IOException{
        try {
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            userService.create(new User("Тест", "123"));
            cookie = new Cookie("JWT", jws);
            cookies[0] = cookie;
            baosLogin.append("НовыйТест");
            baosPassword.append("123");
            assertEquals("player",
                    filterRegistration.registrationIfCookieIfNull(cookies, key,
                            baosLogin, baosPassword).getResultPage());
            userService.deleteByLogin("Тест");
            userService.deleteByLogin("НовыйТест");
        } catch (Exception exc){
            userService.deleteByLogin("Тест");
            userService.deleteByLogin("НовыйТест");
        }
    }

    @Test
    void autentificationTestIfUserExist() throws IOException{
        try {
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            userService.create(new User("Тест", "123"));
            cookie = new Cookie("JWT", jws);
            cookies[0] = cookie;
            baosLogin.append("Тест");
            baosPassword.append("123");
            assertEquals("autorizationAndRegistration",
                    filterRegistration.registrationIfCookieIfNull(cookies, key,
                            baosLogin, baosPassword).getResultPage());
            userService.deleteByLogin("Тест");
            userService.deleteByLogin("НовыйТест");
        } catch (Exception exc){
            userService.deleteByLogin("Тест");
            userService.deleteByLogin("НовыйТест");
        }
    }
}