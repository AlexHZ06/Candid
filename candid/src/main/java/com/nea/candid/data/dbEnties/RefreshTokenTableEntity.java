package com.nea.candid.data.dbEnties;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "refreshtokentable")
public class RefreshTokenTableEntity {

    @Id
    private final long tokenid;
    private final String jwttoken;
    private final LocalDateTime expiresat;
    private final LocalDateTime issuedat;
    private final long userid;
    @Column(unique = true)
    private final String tokenuuid;

    public RefreshTokenTableEntity(long tokenId, String jwtToken, LocalDateTime expiresat, LocalDateTime issuedat, long userID, String tokenuuid) {
        this.tokenid = tokenId;
        this.jwttoken = jwtToken;
        this.expiresat = expiresat;
        this.issuedat = issuedat;
        this.userid = userID;
        this.tokenuuid = tokenuuid;
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

    public String getTokenuuid() {
        return tokenuuid;
    }
}