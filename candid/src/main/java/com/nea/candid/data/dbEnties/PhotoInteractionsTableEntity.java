package com.nea.candid.data.dbEnties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "photointeractions")
public class PhotoInteractionsTableEntity {

    @Id
    private long interactionid;
    private long profileid;
    private long photoid;
    private String interactiontype;
    private Date interactedat;
    private int duration;
    private float weight;

    public PhotoInteractionsTableEntity(long interactionid, long profileid, long photoid, String interactiontype, Date interactedat, int duration, float weight) {
        this.interactionid = interactionid;
        this.profileid = profileid;
        this.photoid = photoid;
        this.interactiontype = interactiontype;
        this.interactedat = interactedat;
        this.duration = duration;
        this.weight = weight;
    }

    public long getInteractionid() {
        return interactionid;
    }

    public long getProfileid() {
        return profileid;
    }

    public long getPhotoid() {
        return photoid;
    }

    public String getInteractiontype() {
        return interactiontype;
    }

    public Date getInteractedat() {
        return interactedat;
    }

    public int getDuration() {
        return duration;
    }

    public float getWeight() {
        return weight;
    }
}
