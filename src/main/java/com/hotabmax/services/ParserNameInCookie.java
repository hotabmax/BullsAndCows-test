package com.hotabmax.services;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.Key;

@Service
public class ParserNameInCookie {
    public String parsePlayerName(Cookie[] cookies, Key key) throws IOException {
        StringBuilder jWTLogin = new StringBuilder();
        for(int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("JWT")) {
                String jws = cookies[i].getValue();
                jWTLogin.append(Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jws)
                        .getBody()
                        .getSubject().split("\\s+")[0]);
            }
        }
        return jWTLogin.toString();
    }
}
