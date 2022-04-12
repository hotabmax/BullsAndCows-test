package com.hotabmax.application.filters;

import com.hotabmax.application.servicesJPA.UserService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.security.Key;

/**
 * FilterAuthentication accept variables array cookies and java.security.Key.
 * Inside find cookie which name "JWT" and take value. Value parse with io.jsonwebtoken.Jwts,
 * at once spit in login and password and change to byte array.
 * If cookie unavailable return autorizationAndRegistration page else player page.
 */
@Service
public class FilterAuthentication {
    @Autowired
    private UserService userService;

    public String autentification(Cookie[] cookies, Key key) throws IOException {
        String resultPage = "autorizationAndRegistration";
        StringBuilder jWTLogin = new StringBuilder();
        StringBuilder jWTPassword = new StringBuilder();
        StringBuilder dataBaseLogin = new StringBuilder();
        StringBuilder dataBasePassword = new StringBuilder();
        if (cookies != null){
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
                        if (!jWTLogin.toString().equals("") && !jWTPassword.toString().equals("")){
                            if (userService.findByLogin(jWTLogin.toString()).size() != 0) {
                                dataBaseLogin.append(userService.findByLogin(jWTLogin
                                                .toString()).get(0).getLogin());
                                dataBasePassword.append(userService.findByLogin(jWTLogin
                                                .toString()).get(0).getPassword());
                                if (dataBasePassword.toString().equals(jWTPassword.toString())) {
                                        resultPage = "player";
                                }
                            }
                        }

                    }
                }
            } catch (JwtException exc) {
                resultPage = "autorizationAndRegistration";
            }
        }
        jWTLogin.delete(0, jWTLogin.length());
        jWTPassword.delete(0, jWTPassword.length());
        dataBaseLogin.delete(0, dataBaseLogin.length());
        dataBasePassword.delete(0, dataBasePassword.length());
        return resultPage;
    }
}
