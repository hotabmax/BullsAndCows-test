package com.hotabmax.application.filters.resultFilterAutorization;

import javax.servlet.http.Cookie;

public class ResultFilterAutorization {
    private final String resultPage;
    private final Cookie cookie;
    public ResultFilterAutorization(String resultPage, Cookie cookie){
        this.resultPage = resultPage;
        this.cookie = cookie;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public String getResultPage() {
        return resultPage;
    }
}
