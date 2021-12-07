package com.android.asm2.model;

public class Report {
    private String id;
    private int registeredNum;
    private int arrivedNum;
    private int testedNum;
    private String note;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRegisteredNum() {
        return registeredNum;
    }

    public void setRegisteredNum(int registeredNum) {
        this.registeredNum = registeredNum;
    }

    public int getArrivedNum() {
        return arrivedNum;
    }

    public void setArrivedNum(int arrivedNum) {
        this.arrivedNum = arrivedNum;
    }

    public int getTestedNum() {
        return testedNum;
    }

    public void setTestedNum(int testedNum) {
        this.testedNum = testedNum;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
