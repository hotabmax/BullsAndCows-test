package com.hotabmax.application.services;

import com.hotabmax.services.ParserNameInCookie;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.Cookie;

import java.io.IOException;
import java.security.Key;

@SpringBootTest
class ParserNameInCookieTest {
    @Autowired
    private ParserNameInCookie parserNameInCookie;

    private String jws;
    private Cookie cookie;
    private Cookie[] cookies = new Cookie[1];

    @Test
    void parsePlayerName() throws IOException {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
        cookie = new Cookie("JWT", jws);
        cookies[0] = cookie;
        assert parserNameInCookie.parsePlayerName(cookies, key).equals("Тест");
    }
}