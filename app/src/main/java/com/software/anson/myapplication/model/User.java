package com.software.anson.myapplication.model;

/**
 * Created by Anson on 2016/8/7.
 */
public class User {

    private String startFrom, destination, shift, timetable, seat, name, identityCard, room,deviceId;
    private int fee;

    public void User(String identityCard, String name, String startFrom, String destination, String shift, String seat, String room ,String timetable,int fee, String deviceId){
        this.destination = destination;
        this.name = name;
        this.identityCard = identityCard;
        this.startFrom = startFrom;
        this.shift = shift;
        this.seat = seat;
        this.room = room;
        this.timetable = timetable;
        this.fee = fee;
        this.deviceId = deviceId;
    }
    public String getDeviceId(String deviceId) {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getStartFrom(String startFrom) {
        return this.startFrom;
    }

    public void setStartFrom(String startFrom) {
        this.startFrom = startFrom;
    }

    public String getDestination(String destination) {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getShift(String shift) {
        return this.shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getTimetable(String timetable) {
        return this.timetable;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }

    public String getSeat(String seat) {
        return this.seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getName(String name) {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentityCard(String identityCard) {
        return this.identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getRoom(String room) {
        return this.room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getFee(int fee) {
        return this.fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

}
