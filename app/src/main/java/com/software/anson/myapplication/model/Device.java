package com.software.anson.myapplication.model;

/**
 * Created by Anson on 2016/8/7.
 */
public class Device {
    private String deviceName;
    private String music;
    private String sound;
    private int battery;
    private int alarm;
    private String deviceCode;
    private int lock;
    private int online;
    private int heart;

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {

        this.heart = heart;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getLock() {
        return lock;
    }

    public void setLock(int lock) {
        this.lock = lock;
    }

    public String getSound(String sound) {
        return this.sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getMusic(String start_from) {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getAlarm() {
        return alarm;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceName(String deviceName) {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
