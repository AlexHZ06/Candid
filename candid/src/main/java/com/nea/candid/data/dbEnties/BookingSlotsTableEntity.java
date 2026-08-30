package com.nea.candid.data.dbEnties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "bookingslotstable")
public class BookingSlotsTableEntity {

    @Id
    private final long slotid;
    private final int startslot;
    private final int endslot;
    private final Date dayofshoot;
    private final long clientid;
    private final long photographerid;
    private final String status;

    public BookingSlotsTableEntity(long slotid, int startslot, int endslot, Date dayofshoot, long clientid, long photographerid, String status) {
        this.slotid = slotid;
        this.startslot = startslot;
        this.endslot = endslot;
        this.dayofshoot = dayofshoot;
        this.clientid = clientid;
        this.photographerid = photographerid;
        this.status = status;
    }

    public long getSlotid() {
        return slotid;
    }

    public int getStartslot() {
        return startslot;
    }

    public int getEndslot() {
        return endslot;
    }

    public Date getDayofshoot() {
        return dayofshoot;
    }

    public long getClientid() {
        return clientid;
    }

    public long getPhotographerid() {
        return photographerid;
    }

    public String getStatus() {
        return status;
    }
}
