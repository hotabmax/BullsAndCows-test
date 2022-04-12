package com.hotabmax.application.models;

import javax.persistence.*;

@Entity
@Table(name = "progress")
public class Progress implements Comparable<Progress>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String login;
    @Column
    private Integer attempts;
    @Column
    private Integer success;
    @Column
    private Float middleattempts;

    public Progress() {
    }

    public Progress(String login, Integer attempts, Integer success, Float middleattempts) {
        this.login = login;
        this.attempts = attempts;
        this.success = success;
        this.middleattempts = middleattempts;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public float getMiddleattempts() {
        return middleattempts;
    }

    public void setMiddleattempts(Float middleattempts) {
        this.middleattempts = middleattempts;
    }

    @Override
    public int compareTo(Progress o) {
        return (int) (this.getMiddleattempts() * 100) - (int) (o.getMiddleattempts() * 100);
    }
}
