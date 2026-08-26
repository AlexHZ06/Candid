package com.nea.candid.data.dbEnties;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "interactionstable")
public class InteractionsTableEntity {

    private final long interactionid;
    private final long photoid;
    private final long profileid;
    private final String interaction;
    private final Date interactiondate;

    public InteractionsTableEntity(long interactionid, long photoid, long profileid, String interaction, Date interactiondate) {
        this.interactionid = interactionid;
        this.photoid = photoid;
        this.profileid = profileid;
        this.interaction = interaction;
        this.interactiondate = interactiondate;
    }

    public long getInteractionid() {
        return interactionid;
    }

    public long getPhotoid() {
        return photoid;
    }

    public long getProfileid() {
        return profileid;
    }

    public String getInteraction() {
        return interaction;
    }

    public Date getInteractiondate() {
        return interactiondate;
    }
}
