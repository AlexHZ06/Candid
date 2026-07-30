package com.nea.candid.data.dto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class JwtJwsBody {

    private String jwt;
    private Claims claims;
    private Boolean expired;
    private Boolean invalid;

    public JwtJwsBody(String jwt, Claims claims, Boolean expired, Boolean invalid) {
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
