package com.hotabmax.application.filters;

import com.hotabmax.application.filters.resultFilterAutorization.ResultFilterAutorization;
import com.hotabmax.application.servicesJPA.UserService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.security.Key;

/**
 * FilterAuthorization accept variables array cookies, java.security.Key,
 * baosLogin and baosPassword. baosLogin and baosPassword try find in database. If
 * user with this login not registered or password not true authorization rejected.
 * If login and password true created new cookie with JWT token.
 * And if user already exist find cookie which name "JWT" and take value. Value parse with io.jsonwebtoken.Jwts,
 * at once spit in login and password and change to byte array.
 * If cookie, login and password unavailable return autorizationAndRegistration page else player page.
 */
@Service
public class FilterAuthorization {
    @Autowired
    private UserService userService;
    public ResultFilterAutorization autorizationIfCookieIsNull(Cookie[] cookies,
                                                 Key key,
                                                 StringBuilder login,
                                                               StringBuilder password) throws IOException {
        StringBuilder dataBaseLogin = new StringBuilder();
        StringBuilder dataBasePassword = new StringBuilder();
        String resultPage = null;
        Cookie cookieResult = null;
        if(cookies == null) {
            if (userService.findByLogin(login.toString()).size() != 0) {
                dataBaseLogin.append(userService.findByLogin(login
                                .toString()).get(0).getLogin());
                dataBasePassword.append(userService.findByLogin(login
                                .toString()).get(0).getPassword());
                if (dataBasePassword.toString().equals(password.toString())) {
                    String jws = Jwts.builder().setSubject(
                            login.toString()
                                    +" "+
                                    password.toString()).signWith(key).compact();
                    cookieResult = new Cookie("JWT", jws);
                    cookieResult.setMaxAge(999999);
                    resultPage = "player";
                } else resultPage = "autorizationAndRegistration";
            } else resultPage = "autorizationAndRegistration";
        }
        if (resultPage == null)
            return tryFindJWTToken(cookies, key, login, password);
        else {
            login.delete(0, login.length());
            password.delete(0, password.length());
            dataBaseLogin.delete(0, dataBaseLogin.length());
            dataBasePassword.delete(0, dataBasePassword.length());
            return new ResultFilterAutorization(resultPage, cookieResult);
        }
    }

    private ResultFilterAutorization tryFindJWTToken(Cookie[] cookies,
                                                            Key key,
                                                            StringBuilder baosLogin,
                                                            StringBuilder baosPassword) throws IOException{
        String resultPage = null;
        Cookie cookieResult = null;
        StringBuilder jWTLogin = new StringBuilder();
        StringBuilder jWTPassword = new StringBuilder();
        StringBuilder dataBaseLogin = new StringBuilder();
        StringBuilder dataBasePassword = new StringBuilder();
        try {
            for(int i = 0; i < cookies.length; i++) {
                if(cookies[i].getName().equals("JWT")) {
                    String jws = cookies[i].getValue();
                    jWTLogin.append(Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(jws)
                            .getBody()
                            .getSubject().split("\\s+")[0]);
                    jWTPassword.append(Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(jws)
                            .getBody()
                            .getSubject().split("\\s+")[1]);
                }
            }
        } catch (JwtException exc) {
            System.out.println("Куки недействителен");

        }
        if (userService.findByLogin(jWTLogin.toString()).size() != 0) {
            dataBaseLogin.append(userService.findByLogin(jWTLogin
                            .toString()).get(0).getLogin());
            dataBasePassword.append(userService.findByLogin(jWTLogin
                            .toString()).get(0).getPassword());
            if (dataBasePassword.toString()
                    .equals(jWTPassword.toString())) {
                resultPage = "player";
            }
        }

        jWTLogin.delete(0, jWTLogin.length());
        jWTPassword.delete(0, jWTPassword.length());
        dataBaseLogin.delete(0, dataBaseLogin.length());
        dataBasePassword.delete(0, dataBasePassword.length());
        if (resultPage == null){
            return autorizationIfJWTTokenNotTrue(key, baosLogin, baosPassword);
        }
        else{
            return new ResultFilterAutorization(resultPage, cookieResult);
        }
    }

    private ResultFilterAutorization autorizationIfJWTTokenNotTrue(Key key,
                                                                      StringBuilder baosLogin,
                                                                      StringBuilder baosPassword) throws IOException {
        String resultPage;
        Cookie cookieResult = null;
        StringBuilder dataBaseLogin = new StringBuilder();
        StringBuilder dataBasePassword = new StringBuilder();
        if (userService.findByLogin(baosLogin.toString()).size() != 0) {
            dataBaseLogin.append(userService.findByLogin(baosLogin
                            .toString()).get(0).getLogin());
            dataBasePassword.append(userService.findByLogin(baosLogin
                            .toString()).get(0).getPassword());
            if (dataBasePassword.toString()
                    .equals(baosPassword.toString())) {
                String jws = Jwts.builder().setSubject(baosLogin.toString()
                        +" "+
                        baosPassword.toString()).signWith(key).compact();
                cookieResult = new Cookie("JWT", jws);
                cookieResult.setMaxAge(999999);
                resultPage = "player";
            } else resultPage = "autorizationAndRegistration";
        } else resultPage = "autorizationAndRegistration";

        baosLogin.delete(0, baosLogin.length());
        baosPassword.delete(0, baosPassword.length());
        dataBaseLogin.delete(0, dataBaseLogin.length());
        dataBasePassword.delete(0, dataBasePassword.length());
        return new ResultFilterAutorization(resultPage, cookieResult);
    }
}
