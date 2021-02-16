package com.huawei.fixture.model;

public class Fixture {

    String homeTeam;
    String awayTeam;
    String homeAmblem;
    String awayAmblem;
    public Fixture(String homeTeam, String awayTeam, String homeAmblem, String awayAmblem) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeAmblem = homeAmblem;
        this.awayAmblem = awayAmblem;
    }

    public String getHomeAmblem() {
        return homeAmblem;
    }

    public void setHomeAmblem(String homeAmblem) {
        this.homeAmblem = homeAmblem;
    }

    public String getAwayAmblem() {
        return awayAmblem;
    }

    public void setAwayAmblem(String awayAmblem) {
        this.awayAmblem = awayAmblem;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }
   


}