package com.hotabmax.application.servicesJPA;

import com.hotabmax.application.models.Progress;
import com.hotabmax.services.ChangerMiddleAttempts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProgressServiceTest {
    @Autowired
    private ProgressService progressService;

    @Test
    void tranzactionAddAttemptsTestCreate() {
        try{
            progressService.create(new Progress("Тест", 1, 0, (float)0));
            assertEquals( 1, progressService.findByLogin("Тест").get(0).getAttempts());
            progressService.deleteByLogin("Тест");
        } catch (Exception exc){
            progressService.deleteByLogin("Тест");
        }
    }

    @Test
    void tranzactionAddSuccessesTestCreate() {
        try{
            progressService.create(new Progress("Тест", 1, 1, (float)1));
            assertEquals( 1, progressService.findByLogin("Тест").get(0).getSuccess());
            progressService.deleteByLogin("Тест");
        } catch (Exception exc){
            progressService.deleteByLogin("Тест");
        }
    }

    @Test
    void tranzactionAddAttempts() {
        try{
            progressService.create(new Progress("Тест", 1, 0, (float)0));
            progressService.tranzactionAddAttempts("Тест", 1);
            assertEquals( 2, progressService.findByLogin("Тест").get(0).getAttempts());
            progressService.deleteByLogin("Тест");
        } catch (Exception exc){
            progressService.deleteByLogin("Тест");
        }
    }

    @Test
    void tranzactionAddSuccesses() {
        try{
            progressService.create(new Progress("Тест", 1, 0, (float)0));
            progressService.tranzactionAddSuccesses("Тест", 1);
            assertEquals( 1, progressService.findByLogin("Тест").get(0).getSuccess());
            progressService.deleteByLogin("Тест");
        } catch (Exception exc){
            progressService.deleteByLogin("Тест");
        }
    }
    @Test
    void tranzactionTestMiddleAttemptsTwoRecords() {
        try{
            progressService.create(new Progress("Тест", 1, 0, (float)0));
            progressService.tranzactionAddSuccesses("Тест", 1);
            progressService.tranzactionAddAttempts("Тест", 1);
            progressService.tranzactionChangeMiddleAttempts("Тест");
            assertEquals(  2, progressService.findByLogin("Тест").get(0).getMiddleattempts());
            progressService.deleteByLogin("Тест");
        } catch (Exception exc){
            progressService.deleteByLogin("Тест");
        }
    }
    @Test
    void tranzactionTestMiddleAttemptsThreeRecords() {
        try{
            progressService.create(new Progress("Тест", 1, 0, (float)0));
            progressService.tranzactionAddSuccesses("Тест", 1);
            progressService.tranzactionAddAttempts("Тест", 1);
            progressService.tranzactionAddAttempts("Тест", 1);
            progressService.tranzactionChangeMiddleAttempts("Тест");
            assertEquals(3, progressService.findByLogin("Тест").get(0).getMiddleattempts());
            progressService.deleteByLogin("Тест");
        } catch (Exception exc){
            progressService.deleteByLogin("Тест");
        }
    }

}