package main.java;

import java.io.Serializable;

public class User implements Serializable {

    private String userName;
    private double userScore;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getUserScore() {
        return userScore;
    }

    public void setUserScore(double userScore) {
        this.userScore = userScore;
    }

    @Override
    public String toString() {
        return "Name: " + userName + "\tScore: " + userScore;
    }
}