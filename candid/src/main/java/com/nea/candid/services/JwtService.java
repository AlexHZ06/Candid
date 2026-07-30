package com.nea.candid.services;

import com.nea.candid.data.dbEnties.RefreshTokenTableEntity;
import com.nea.candid.data.dto.JwtJwsBody;
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
import java.util.UUID;

@Component
public class JwtService {

    private String key = "9f4c2d7b81e6a3f05d2c9b7e4a1f8d63c7e91a5b2d4f6c8e0b3a7d9f1c5e2a6";

    private final RefreshTokenTableService refreshTokenTableService;
    private final UsersTableService usersTableService;

    public JwtService(RefreshTokenTableService refreshTokenTableService, UsersTableService usersTableService) {
        this.refreshTokenTableService = refreshTokenTableService;
        this.usersTableService = usersTableService;
    }

    public ResponseBody requestJwt(long userid){

        RefreshTokenTableEntity record = refreshTokenTableService.getTokenByUserId(userid);

        if(record == null){

            return new ResponseBody("invalid refresh token", 202, false);

        }

        String refreshJwt = record.getJwttoken();
        JwtJwsBody jwtJwsBody = decodeJwt(refreshJwt);

        if(jwtJwsBody.getExpired() || jwtJwsBody.getInvalid()){

            return new ResponseBody("invalid refresh token", 202, false);

        }
        else{

            String jwt = buildJwt(userid, (String)jwtJwsBody.getClaims().get("role"), false);
            return new ResponseBody(jwt, 252, true);

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


    public void addJwt(JwtJwsBody jws, String jwt) {

        LocalDateTime issuedAt = LocalDateTime.ofInstant(jws.getClaims().getIssuedAt().toInstant(), ZoneId.systemDefault());
        LocalDateTime expiresAt = LocalDateTime.ofInstant(jws.getClaims().getExpiration().toInstant(), ZoneId.systemDefault());

        RefreshTokenTableEntity record = new RefreshTokenTableEntity(jwt, expiresAt, issuedAt, Long.parseLong(jws.getClaims().getSubject()));

        refreshTokenTableService.saveRefreshToken(record);

    }


    public void removeJwt(long userId){

        refreshTokenTableService.deleteTokenByUserId(userId);

    }


    public ResponseBody regenerateAccessToken(JwtJwsBody jwtJwsBody) {

        if(jwtJwsBody.getInvalid()){return new ResponseBody("Invalid access Key", 201, false);}

        else{

            if(jwtJwsBody.getExpired()){

                RefreshTokenTableEntity refreshRecord =  refreshTokenTableService.getTokenByUserId(Long.parseLong(jwtJwsBody.getClaims().getSubject()));

                if(refreshRecord == null){return new ResponseBody("Invalid refresh key", 202, false);}

                if(!(refreshRecord.getExpiresat().isAfter(LocalDateTime.now()))){

                    removeJwt(Long.parseLong(jwtJwsBody.getClaims().getSubject()));
                    return new ResponseBody("Expired refresh key", 203, false);

                }

                else{

                    String jwt = buildJwt(refreshRecord.getUserid(), jwtJwsBody.getClaims().get("role", String.class), false);
                    return new ResponseBody(jwt, 152, true);

                }


            }

        }

        return new  ResponseBody(jwtJwsBody.getJwt(), 251, true);

    }


    public ResponseBody getJwtStatus(JwtJwsBody jwtJwsBody) {

        if(jwtJwsBody.getInvalid()){

            return new ResponseBody("Invalid access Key", 201, false);

        }
        else if(jwtJwsBody.getExpired()){

            return new ResponseBody("expired access key", 202, false);

        }
        else{

            return new ResponseBody("valid access jwt", 251, true);

        }

    }


    public JwtJwsBody decodeJwt(String jwt){

        try {

            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            return new JwtJwsBody(jwt, claims, false, false);

        }

        catch(ExpiredJwtException e) {

            Claims claims = e.getClaims();
            return new JwtJwsBody(jwt, claims, true, false);

        }
        catch(Exception e) {

            return new JwtJwsBody("", null, null, true);

        }
    }

}
