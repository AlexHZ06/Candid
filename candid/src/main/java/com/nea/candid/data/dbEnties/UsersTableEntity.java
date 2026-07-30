package com.nea.candid.data.dbEnties;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "userstable")
public class UsersTableEntity {

    @Id
    private Long userid;
    @Column(unique = true)
    private String username;
    private String firstname;
    private String lastname;
    private String usertype;
    private String email;
    private String hashedpassword;
    private Date datejoined;

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
