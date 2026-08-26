package com.nea.candid.data.dbEnties;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "userstable")
public class UsersTableEntity {

    @Id
    private final Long userid;
    @Column(unique = true)
    private final String username;
    private final String firstname;
    private final String lastname;
    private final String usertype;
    private final String email;
    private final String hashedpassword;
    private final Date datejoined;

    public UsersTableEntity(Long userid, String username, String firstname, String lastname, String usertype, String email, String hashedpassword, Date datejoined) {
        this.userid = userid;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.usertype = usertype;
        this.email = email;
        this.hashedpassword = hashedpassword;
        this.datejoined = datejoined;
    }

    public Long getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsertype() {
        return usertype;
    }

    public String getEmail() {
        return email;
    }

    public String getHashedpassword() {
        return hashedpassword;
    }

    public Date getDatejoined() {
        return datejoined;
    }
}
