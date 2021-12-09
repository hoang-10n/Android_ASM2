package com.android.asm2.model;

public class User {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String name;
    private String role;
    private String[] joinedZones;

    public User(String username, String password, String email, String phone, String name, String role, String[] joinedZones) {
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

    public String[] getJoinedZones() {
        return joinedZones;
    }

    public void setJoinedZones(String[] joinedZones) {
        this.joinedZones = joinedZones;
    }
}
