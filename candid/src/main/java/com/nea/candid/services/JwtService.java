package com.nea.candid.services;

import com.nea.candid.data.dbEnties.RefreshTokenTableEntity;
import com.nea.candid.data.dataObjects.JwtObject;
import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.services.database.RefreshTokenTableService;
import com.nea.candid.services.database.UsersTableService;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;


import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Component
public class JwtService {

    private String key = "9f4c2d7b81e6a3f05d2c9b7e4a1f8d63c7e91a5b2d4f6c8e0b3a7d9f1c5e2a6";

    private final RefreshTokenTableService refreshTokenTableService;

    public JwtService(RefreshTokenTableService refreshTokenTableService, UsersTableService usersTableService) {
        this.refreshTokenTableService = refreshTokenTableService;
    }

    public ResponseBody requestJwt(long userid){

        RefreshTokenTableEntity record = refreshTokenTableService.getTokenByUserId(userid);

        if(record == null){

            return ResponseBody.error("invalid refresh token", 202);

        }

        String refreshJwt = record.getJwttoken();
        JwtObject jwtObject = decodeJwt(refreshJwt);

        if(jwtObject.getExpired() || jwtObject.getInvalid()){

            return ResponseBody.error("invalid refresh token", 202);

        }
        else{

            String jwt = buildJwt(userid, (String) jwtObject.getClaims().get("role"), false);
            return ResponseBody.success(jwt, 252);

        }

    }

    public String buildJwt(long userId, String role, boolean refresh) {

        Instant now = Instant.now();
        int expiryTime;

        if(refresh) {expiryTime = 10;}
        else { expiryTime = 5; }

        String jwt = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("role", role)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(expiryTime, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        return jwt;

    }

    public void addJwt(JwtObject jws, String jwt) {

        LocalDateTime issuedAt = LocalDateTime.ofInstant(jws.getClaims().getIssuedAt().toInstant(), ZoneId.systemDefault());
        LocalDateTime expiresAt = LocalDateTime.ofInstant(jws.getClaims().getExpiration().toInstant(), ZoneId.systemDefault());

        RefreshTokenTableEntity record = new RefreshTokenTableEntity(jwt, expiresAt, issuedAt, Long.parseLong(jws.getClaims().getSubject()));

        refreshTokenTableService.saveRefreshToken(record);

    }

    public void removeJwt(long userId){

        refreshTokenTableService.deleteTokenByUserId(userId);

    }

    public JwtObject decodeJwt(String jwt){

        try {

            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            return new JwtObject(jwt, claims, false, false);

        }

        catch(ExpiredJwtException e) {

            Claims claims = e.getClaims();
            return new JwtObject(jwt, claims, true, false);

        }
        catch(Exception e) {

            return new JwtObject("", null, null, true);

        }
    }

}
