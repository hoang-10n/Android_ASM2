package com.android.asm2.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Report {
    private String zoneId;
    private String created;
    private int tested;
    private int volunteer;
    private int sample;
    private int positive1st;
    private int positive;
    private String note;

    public Report(String zoneId) {
        this.zoneId = zoneId;
        this.tested = 0;
        this.volunteer = 0;
        this.sample = 0;
        this.positive1st = 0;
        this.positive = 0;
        this.note = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.US);
        this.created = format.format(new Date());
    }

    public Report(String zoneId, String created, int tested, int volunteer, int sample, int positive1st, int positive, String note) {
        this.zoneId = zoneId;
        this.tested = tested;
        this.volunteer = volunteer;
        this.sample = sample;
        this.positive1st = positive1st;
        this.positive = positive;
        this.note = note;
        this.created = created;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
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
