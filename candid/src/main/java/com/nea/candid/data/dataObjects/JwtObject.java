package com.nea.candid.data.dataObjects;

import io.jsonwebtoken.Claims;

public class JwtObject {

    private String jwt;
    private Claims claims;
    private Boolean expired = false;
    private Boolean invalid = false;

    public JwtObject(String jwt, Claims claims, Boolean expired, Boolean invalid) {
        this.jwt = jwt;
        this.claims = claims;
        this.expired = expired;
        this.invalid = invalid;
    }

    public String getJwt() {
        return jwt;
    }

    public Claims getClaims() {
        return claims;
    }

    public Boolean getExpired() {
        return expired;
    }

    public Boolean getInvalid() {
        return invalid;
    }

}
