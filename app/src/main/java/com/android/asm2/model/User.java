package com.android.asm2.model;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String name;
    private String role;
    private ArrayList<String> joinedZones;

    public User(String username) {
        this.username = username;
        this.password = null;
        this.email = null;
        this.phone = null;
        this.name = null;
        this.role = "admin";
        this.joinedZones = null;
    }

    public User(String username, String password, String email, String phone, String name, String role, ArrayList<String> joinedZones) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.role = role;
        this.joinedZones = joinedZones;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getJoinedZones() {
        return joinedZones;
    }

    public void setJoinedZones(ArrayList<String> joinedZones) {
        this.joinedZones = joinedZones;
    }

    public boolean isJoinedZone(String zoneId) {
        for (String id : joinedZones) {
            if (id.equals(zoneId)) return true;
        }
        return false;
    }

    public void joinZone(String zoneId) {
        joinedZones.add(zoneId);
    }

    public void leaveZone(String zoneId) {
        joinedZones.remove(zoneId);
    }
}
