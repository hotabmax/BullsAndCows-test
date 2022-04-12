package com.hotabmax.application.controller.genegatorKey;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class GeneratorKey {
    private static Key key = null;

    public static Key getKey() {
        if (key == null){
            key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        }
        return key;
    }
}
