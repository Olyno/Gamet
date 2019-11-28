package com.olyno.gamet.util.game;

public enum GameStatus {

    WAITING("waiting"),
    STARTED("started"),
    PROCESSING("processing"),
    ENDED("ended");

    private String status;

    GameStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return this.status;
    }

}
