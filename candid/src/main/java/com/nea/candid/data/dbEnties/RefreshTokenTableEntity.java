package com.nea.candid.data.dbEnties;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "refreshtokentable")
public class RefreshTokenTableEntity {

    @Id

    private long tokenid;
    private String jwttoken;
    private LocalDateTime expiresat;
    private LocalDateTime issuedat;
    private long userid;


    public RefreshTokenTableEntity() {
    }


    public RefreshTokenTableEntity(String jwtToken, LocalDateTime expiresat, LocalDateTime issuedat, long userID) {
        this.jwttoken = jwtToken;
        this.expiresat = expiresat;
        this.issuedat = issuedat;
        this.userid = userID;
    }


    public RefreshTokenTableEntity(long tokenId, String jwtToken, LocalDateTime expiresat, LocalDateTime issuedat, long userID) {
        this.tokenid = tokenId;
        this.jwttoken = jwtToken;
        this.expiresat = expiresat;
        this.issuedat = issuedat;
        this.userid = userID;
    }

    public long getTokenid() {
        return tokenid;
    }

    public String getJwttoken() {
        return jwttoken;
    }

    public LocalDateTime getExpiresat() {
        return expiresat;
    }

    public LocalDateTime getIssuedat() {
        return issuedat;
    }

    public long getUserid() {
        return userid;
    }
}