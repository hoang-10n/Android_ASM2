package com.android.asm2.model;

public class Report {
    private String zoneId;
    private int tested;
    private int volunteer;
    private int sample;
    private int positive1st;
    private int positive;
    private String note;

    public Report(String zoneId, int tested, int volunteer, int sample, int positive1st, int positive, String note) {
        this.zoneId = zoneId;
        this.tested = tested;
        this.volunteer = volunteer;
        this.sample = sample;
        this.positive1st = positive1st;
        this.positive = positive;
        this.note = note;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public int getTested() {
        return tested;
    }

    public void setTested(int tested) {
        this.tested = tested;
    }

    public int getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(int volunteer) {
        this.volunteer = volunteer;
    }

    public int getSample() {
        return sample;
    }

    public void setSample(int sample) {
        this.sample = sample;
    }

    public int getPositive1st() {
        return positive1st;
    }

    public void setPositive1st(int positive1st) {
        this.positive1st = positive1st;
    }

    public int getPositive() {
        return positive;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
