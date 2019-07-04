package com.olyno.util.game;

public class GameSound {

    private String sound;
    private Float volume;
    private Float pitch;

    public GameSound(String sound) {
        this.sound = sound;
        this.volume = 100f;
        this.pitch = 100f;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public Float getVolume() {
        return volume;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public Float getPitch() {
        return pitch;
    }

    public void setPitch(Float pitch) {
        this.pitch = pitch;
    }
}
