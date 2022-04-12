package com.hotabmax.application.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hotabmax.application.controller.genegatorKey.GeneratorKey;
import com.hotabmax.application.controller.jsonRequests.JSONRequestInputValue;
import com.hotabmax.application.interfacesServices.GameServiceInterface;
import com.hotabmax.application.filters.FilterAuthentication;
import com.hotabmax.application.filters.FilterAuthorization;
import com.hotabmax.application.filters.FilterRegistration;
import com.hotabmax.application.filters.resultFilterAutorization.ResultFilterAutorization;
import com.hotabmax.application.models.HistoryOfAttempts;
import com.hotabmax.application.models.Progress;
import com.hotabmax.application.models.User;
import com.hotabmax.services.ChangerMiddleAttempts;
import com.hotabmax.services.ParserNameInCookie;
import com.hotabmax.application.servicesJPA.HistoryOfAttemptsService;
import com.hotabmax.application.servicesJPA.ProgressService;
import com.hotabmax.application.servicesJPA.UserService;
import com.hotabmax.services.gameService.GameService;
import com.hotabmax.services.gameService.gameServiceLogic.CreatorHistoryRecordAndTryChangeRandomValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Collections;
import java.util.List;

@Import({GameService.class,
        CreatorHistoryRecordAndTryChangeRandomValue.class,
        ParserNameInCookie.class,
        ChangerMiddleAttempts.class})
@Controller
public class PlayerController {
    private Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();
    private Key key;
    private Cookie[] cookies;
    private StringBuilder login = new StringBuilder();
    private StringBuilder password = new StringBuilder();
    @Autowired
    private UserService userService;
    @Autowired
    private ProgressService progressService;
    @Autowired
    private HistoryOfAttemptsService historyOfAttemptsService;
    @Autowired
    private FilterAuthentication filterAutentification;
    @Autowired
    private FilterAuthorization filterAutorization;
    @Autowired
    private FilterRegistration filterRegistration;
    @Autowired
    private GameServiceInterface gameServiceInterface;
    @Autowired
    private ParserNameInCookie parserNameInCookie;
    @Autowired
    private ChangerMiddleAttempts changerMiddleAttempts;

    @Bean
    public void setKey(){
        key = GeneratorKey.getKey();
    }

    @GetMapping("/")
    public String getHost(HttpServletRequest httpServletRequest) throws IOException {
        Cookie[] cookies = httpServletRequest.getCookies();
        return filterAutentification.autentification(cookies, key);
    }

    @GetMapping("/exit")
    public String exit(HttpServletRequest httpServletRequest,
                          HttpServletResponse httpServletResponse) {
        Cookie[] cookies = httpServletRequest.getCookies();
        for(int i = 0; i < cookies.length; i++){
            if(cookies[i].getName().equals("JWT")){
                cookies[i].setValue("111111");
                httpServletResponse.addCookie(cookies[i]);
            }
        }
        return "autorizationAndRegistration";
    }

    @PostMapping("/tryAutorization")
    @ResponseBody
    public String tryAutorization(@RequestBody String data) throws IOException{
        login.append(gson.fromJson(data, User.class).getLogin());
        password.append(gson.fromJson(data, User.class).getPassword());
        if (userService.findByLogin(login.toString()).size() == 1){
            login.delete(0, login.length());
            password.delete(0, password.length());
            return "Успех";
        } else {
            login.delete(0, login.length());
            password.delete(0, password.length());
            return "Неудача";
        }
    }

    @PostMapping("/autorization")
    public String autorization(HttpServletRequest httpServletRequest,
                                   HttpServletResponse httpServletResponse,
                                   @RequestBody String data) throws IOException{
        cookies = httpServletRequest.getCookies();
        login.append(gson.fromJson(data, User.class).getLogin());
        password.append(gson.fromJson(data, User.class).getPassword());
        ResultFilterAutorization resultFilterAutorization =
                filterAutorization.autorizationIfCookieIsNull(cookies, key, login, password);
        if (resultFilterAutorization.getCookie() != null) {
            httpServletResponse.addCookie(resultFilterAutorization.getCookie());
        }
        login.delete(0, login.length());
        password.delete(0, password.length());
        return resultFilterAutorization.getResultPage();
    }

    @PostMapping("/tryRegistration")
    @ResponseBody
    public String tryRegistration(@RequestBody String data) throws IOException{
        login.append(gson.fromJson(data, User.class).getLogin());
        password.append(gson.fromJson(data, User.class).getPassword());
        if (userService.findByLogin(login.toString()).size() == 0){
            login.delete(0, login.length());
            password.delete(0, password.length());
            return "Успех";
        } else {
            login.delete(0, login.length());
            password.delete(0, password.length());
            return "Неудача";
        }
    }

    @PostMapping("/registration")
    public String registration(HttpServletRequest httpServletRequest,
                                   HttpServletResponse httpServletResponse,
                                   @RequestBody String data) throws IOException{
        cookies = httpServletRequest.getCookies();
        login.append(gson.fromJson(data, User.class).getLogin());
        password.append(gson.fromJson(data, User.class).getPassword());
        ResultFilterAutorization resultFilterAutorization =
                filterRegistration.registrationIfCookieIfNull(cookies, key, login, password);
        if (resultFilterAutorization.getCookie() != null) {
            httpServletResponse.addCookie(resultFilterAutorization.getCookie());
        }
        login.delete(0, login.length());
        password.delete(0, password.length());
        return resultFilterAutorization.getResultPage();
    }

    @PostMapping("/findHistoryOfAttempts")
    @ResponseBody
    public String findHistoryOfAttemptsByLogin(HttpServletRequest httpServletRequest) throws IOException{
        cookies = httpServletRequest.getCookies();
        List<HistoryOfAttempts> historyOfAttempts = historyOfAttemptsService.findByLogin("Тест");
        String gsonresult = gson.toJson(historyOfAttempts);
        if(filterAutentification.autentification(cookies, key).equals("player")){
            if(historyOfAttemptsService
                    .findByLogin(parserNameInCookie.parsePlayerName(cookies, key)).size() != 0){
                return gson.toJson(historyOfAttemptsService
                        .findByLogin(
                                parserNameInCookie.parsePlayerName(cookies, key)));
            }
        }
        return gson.toJson(new HistoryOfAttempts());
    }

    @PostMapping("/findProgress")
    @ResponseBody
    public String findProgress(HttpServletRequest httpServletRequest) throws IOException{
        cookies = httpServletRequest.getCookies();
        if(filterAutentification.autentification(cookies, key).equals("player")){
            if(progressService.findAll().size() != 0){
                List<Progress> progresses = progressService.findAll();
                Collections.sort(progresses);
                List<Progress> progressess = progressService.findByLogin(parserNameInCookie.parsePlayerName(cookies, key));
                return gson.toJson(progresses);
            }
        }
        return gson.toJson(new Progress());
    }

    @PostMapping("/play")
    @ResponseBody
    public String play(HttpServletRequest httpServletRequest,
                               @RequestBody String data) throws IOException{
        JSONRequestInputValue jsonRequestInputValue = gson.fromJson(data, JSONRequestInputValue.class);
        Cookie[] cookies = httpServletRequest.getCookies();
        if(filterAutentification.autentification(cookies, key).equals("player")){
            List<Progress> progresses = progressService.findByLogin(parserNameInCookie.parsePlayerName(cookies, key));
            return gameServiceInterface.play(
                    parserNameInCookie.parsePlayerName(cookies, key),
                    jsonRequestInputValue.getValue());
        }
        return "";
    }

    @PostMapping("/changeMiddleAttempts")
    @ResponseBody
    public String changeMiddleAttempts(HttpServletRequest httpServletRequest) throws IOException{
        Cookie[] cookies = httpServletRequest.getCookies();
        if(filterAutentification.autentification(cookies, key).equals("player")){
            List<Progress> progresses = progressService.findByLogin(parserNameInCookie.parsePlayerName(cookies, key));
            return changerMiddleAttempts.setMiddleAttempts(parserNameInCookie.parsePlayerName(cookies, key));
        }
        return "Неудача";
    }
}
