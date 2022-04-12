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
class FilterAuthorizationTest {
    @Autowired
    private UserService userService;
    @Autowired
    private FilterAuthorization filterAuthorization;

    private String jws;
    private Cookie cookie;
    private Cookie[] cookies = new Cookie[1];
    private StringBuilder baosLogin = new StringBuilder();
    private StringBuilder baosPassword = new StringBuilder();
    @Test
    void autorizationIfCookieIsNullTestIfCookieTrue() throws IOException {
        try {
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            userService.create(new User("Тест", "123"));
            jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
            cookie = new Cookie("JWT", jws);
            cookies[0] = cookie;
            assertEquals("player",
                    filterAuthorization.autorizationIfCookieIsNull(cookies, key,
                            baosLogin, baosPassword).getResultPage());

            userService.deleteByLogin("Тест");
        } catch (Exception exc){
            userService.deleteByLogin("Тест");
        }
    }
    @Test
    void autorizationIfCookieIsNullTestIfCookieFalse() throws IOException{
        try {
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            userService.create(new User("Тест", "123"));
            jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
            key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            cookie = new Cookie("JWT", jws);
            cookies[0] = cookie;
            assertEquals("autorizationAndRegistration",
                    filterAuthorization.autorizationIfCookieIsNull(cookies, key,
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
            key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            cookie = new Cookie("JWT", jws);
            cookies[0] = cookie;
            assertEquals("autorizationAndRegistration",
                    filterAuthorization.autorizationIfCookieIsNull(cookies, key,
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
            key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            cookie = new Cookie("JWT", jws);
            cookies[0] = cookie;
            assertEquals("autorizationAndRegistration",
                    filterAuthorization.autorizationIfCookieIsNull(cookies, key,
                            baosLogin, baosPassword).getResultPage());
            userService.deleteByLogin("Тест");
        } catch (Exception exc){
            userService.deleteByLogin("Тест");
        }
    }

    @Test
    void autentificationTestIfCookieNullButDataTrue() throws IOException{
        try {
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            userService.create(new User("Тест", "123"));
            cookie = new Cookie("JWT", jws);
            cookies[0] = null;
            baosLogin.append("Тест");
            baosPassword.append("123");
            assertEquals("player",
                    filterAuthorization.autorizationIfCookieIsNull(cookies, key,
                            baosLogin, baosPassword).getResultPage());
            userService.deleteByLogin("Тест");
        } catch (Exception exc){
            userService.deleteByLogin("Тест");
        }
    }

    @Test
    void autentificationTestIfInputDataFalse() throws IOException{
        try {
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            userService.create(new User("Тест", "123"));
            cookie = new Cookie("JWT", jws);
            cookies[0] = null;
            baosLogin.append("ergwe124grege123rerqgghrt");
            baosPassword.append("1regr213gwererqgg23");
            assertEquals("autorizationAndRegistration",
                    filterAuthorization.autorizationIfCookieIsNull(cookies, key,
                            baosLogin, baosPassword).getResultPage());
            userService.deleteByLogin("Тест");
        } catch (Exception exc){
            userService.deleteByLogin("Тест");
        }
    }
}