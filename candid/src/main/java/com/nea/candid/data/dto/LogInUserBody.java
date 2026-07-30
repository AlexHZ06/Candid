package com.nea.candid.data.dto;

public class LogInUserBody {

    private String userName;
    private String password;

    public LogInUserBody(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

}
