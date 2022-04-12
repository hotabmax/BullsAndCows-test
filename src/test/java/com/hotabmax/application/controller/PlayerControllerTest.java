package com.hotabmax.application.controller;

import com.hotabmax.application.controller.genegatorKey.GeneratorKey;
import com.hotabmax.application.models.HistoryOfAttempts;
import com.hotabmax.application.models.Progress;
import com.hotabmax.application.models.User;
import com.hotabmax.application.servicesJPA.HistoryOfAttemptsService;
import com.hotabmax.application.servicesJPA.ProgressService;
import com.hotabmax.application.servicesJPA.UserService;
import com.hotabmax.services.gameService.GameService;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import java.security.Key;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class PlayerControllerTest {

    @Autowired
    private UserService userService;
    @Autowired
    private HistoryOfAttemptsService historyOfAttemptsService;
    @Autowired
    private ProgressService progressService;

    private String jws;
    private Cookie cookie;
    private MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void getHost() {
        try {
            Key key = GeneratorKey.getKey();
            userService.create(new User("Тест", "123"));
            jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
            cookie = new Cookie("JWT", jws);
            mockMvc.perform(MockMvcRequestBuilders.post("/")
                            .cookie(cookie))
                            .andExpect(status().is(405));
            userService.deleteByLogin("Тест");
            mockMvc.perform(MockMvcRequestBuilders.post("/")
                            .cookie(cookie))
                            .andExpect(status().is(405));
        } catch (Exception exc){
            userService.deleteByLogin("Тест");
        }
    }

    @Test
    void exit() {
        try {
            Key key = GeneratorKey.getKey();
            jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
            cookie = new Cookie("JWT", jws);
            mockMvc.perform(MockMvcRequestBuilders.get("/exit")
                            .cookie(cookie))
                            .andExpect(cookie().value("JWT", "111111"));

        } catch (Exception exc){
            exc.printStackTrace();
        }
    }

    @Test
    void tryAutorization() {
        try {
            userService.create(new User("Тест", "123"));
            mockMvc.perform(MockMvcRequestBuilders.post("/tryAutorization")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{login: Тест, password: 123}"))
                            .andExpect(status().is(200))
                            .andExpect(content().string("Успех"));
            userService.deleteByLogin("Тест");
            mockMvc.perform(MockMvcRequestBuilders.post("/tryAutorization")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{login: Тест, password: 123}"))
                            .andExpect(status().is(200))
                            .andExpect(content().string(""));
        } catch (Exception exc){
            userService.deleteByLogin("Тест");
        }
    }

    @Test
    void autorization() {
        try {
            userService.create(new User("Тест", "123"));
            mockMvc.perform(MockMvcRequestBuilders.post("/autorization")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{login: Тест, password: 123}"))
                            .andExpect(status().is(200));
            userService.deleteByLogin("Тест");
            mockMvc.perform(MockMvcRequestBuilders.post("/autorization")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{login: Тест, password: 123}"))
                            .andExpect(status().is(200));
        } catch (Exception exc){
            userService.deleteByLogin("Тест");
        }
    }

    @Test
    void tryRegistration() {
        try {
            userService.create(new User("Тест", "123"));
            mockMvc.perform(MockMvcRequestBuilders.post("/tryRegistration")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{login: Тест, password: 123}"))
                            .andExpect(status().is(200))
                            .andExpect(content().string(""));
            userService.deleteByLogin("Тест");
            mockMvc.perform(MockMvcRequestBuilders.post("/tryRegistration")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{login: Тест, password: 123}"))
                            .andExpect(status().is(200))
                            .andExpect(content().string("Успех"));
        } catch (Exception exc){
            userService.deleteByLogin("Тест");
        }
    }

    @Test
    void registration() {
        try {
            userService.create(new User("Тест", "123"));
            mockMvc.perform(MockMvcRequestBuilders.post("/registration")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{login: Тест, password: 123}"))
                            .andExpect(status().is(200));
            userService.deleteByLogin("Тест");
            mockMvc.perform(MockMvcRequestBuilders.post("/registration")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{login: Тест, password: 123}"))
                            .andExpect(status().is(405));
            userService.deleteByLogin("Тест");
        } catch (Exception exc){
            userService.deleteByLogin("Тест");
        }
    }

    @Test
    void findHistoryOfAttempts() {
        try {
            Key key = GeneratorKey.getKey();
            userService.create(new User("Тест", "123"));
            historyOfAttemptsService.create(new HistoryOfAttempts("Тест", 1234, 0, 0));
            jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
            cookie = new Cookie("JWT", jws);
            mockMvc.perform(MockMvcRequestBuilders.post("/findHistoryOfAttempts")
                            .cookie(cookie))
                            .andExpect(status().is(200))
                            .andExpect(content().json("[{'login': 'Тест', 'value': 1234, 'bulls': 0, 'cows': 0}]"));
            userService.deleteByLogin("Тест");
            historyOfAttemptsService.deleteByLogin("Тест");
        } catch (Exception exc){
            userService.deleteByLogin("Тест");
            historyOfAttemptsService.deleteByLogin("Тест");
        }
    }

    @Test
    void findProgress() {
        try {
            Key key = GeneratorKey.getKey();
            progressService.create(new Progress("Тест", 10, 1,(float) 10));
            progressService.create(new Progress("Тест2", 10, 1,(float) 10));
            userService.create(new User("Тест", "123"));
            jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
            cookie = new Cookie("JWT", jws);
            mockMvc.perform(MockMvcRequestBuilders.post("/findProgress")
                            .cookie(cookie))
                            .andExpect(status().is(200))
                            .andExpect(content().json("[{'login': 'Тест', 'attempts': 10," +
                            " 'success': 1, 'middleattempts': 10}{'login': 'Тест2', 'attempts': 10," +
                                    " 'success': 1, 'middleattempts': 10}]"));
            userService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест2");
        } catch (Exception exc){
            userService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест2");
        }
    }

    @Test
    void testThreeRecords() {
        try {
            progressService.create(new Progress("Тест", 1, 0, (float)1));
            Key key = GeneratorKey.getKey();
            userService.create(new User("Тест", "123"));
            jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
            cookie = new Cookie("JWT", jws);
            mockMvc.perform(MockMvcRequestBuilders.post("/play")
                            .cookie(cookie)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{value: "+GameService.getRandom()+"}"))
                    .andExpect(status().is(200));
            mockMvc.perform(MockMvcRequestBuilders.post("/changeMiddleAttempts")
                            .cookie(cookie)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(200));
            mockMvc.perform(MockMvcRequestBuilders.post("/play")
                            .cookie(cookie)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{value: 1234}"));
            mockMvc.perform(MockMvcRequestBuilders.post("/changeMiddleAttempts")
                            .cookie(cookie)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(200));
            assert progressService.findByLogin("Тест").get(0).getMiddleattempts() == 3;
            System.out.println(progressService.findByLogin("Тест").get(0).getMiddleattempts());
            userService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест");
        } catch (Exception exc){
            exc.printStackTrace();
            userService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест");
        }
    }

    @Test
    void changeMiddleAttempts() {
        try {
            progressService.create(new Progress("Тест", 1, 1, (float)0));
            Key key = GeneratorKey.getKey();
            userService.create(new User("Тест", "123"));
            jws = Jwts.builder().setSubject("Тест"+" "+"123").signWith(key).compact();
            cookie = new Cookie("JWT", jws);
            mockMvc.perform(MockMvcRequestBuilders.post("/changeMiddleAttempts")
                            .cookie(cookie)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(200));
            assert progressService.findByLogin("Тест").get(0).getMiddleattempts() == 1;
            userService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест");
        } catch (Exception exc){
            userService.deleteByLogin("Тест");
            progressService.deleteByLogin("Тест");
        }

    }
}