package com.hotabmax.application.models;

import javax.persistence.*;

@Entity
@Table(name = "historyofattempts")
public class HistoryOfAttempts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String login;
    @Column
    private Integer value;
    @Column
    private Integer bulls;
    @Column
    private Integer cows;
    public HistoryOfAttempts() {
    }

    public HistoryOfAttempts(String login, Integer value, Integer bulls, Integer cows) {
        this.login = login;
        this.value = value;
        this.bulls = bulls;
        this.cows = cows;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getBulls() {
        return bulls;
    }

    public void setBulls(int bulls) {
        this.bulls = bulls;
    }

    public int getCows() {
        return cows;
    }

    public void setCows(int cows) {
        this.cows = cows;
    }
}
