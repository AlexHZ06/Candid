package com.nea.candid.data.dto;

public class LogInResponse {

    private final String jwt;
    private final String refreshUUID;

    public LogInResponse(String jwt, String refreshUUID) {
        this.jwt = jwt;
        this.refreshUUID = refreshUUID;
    }

    public String getJwt() {
        return jwt;
    }

    public String getRefreshUUID() {
        return refreshUUID;
    }
}
