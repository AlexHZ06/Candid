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
    private  Date dateofshoot;
    private final long clientid;
    private final long photographerid;
    private final String status;
    private String message;
    private String address;
    private double latitude;
    private double longitude;
    private String category;


    public BookingSlotsTableEntity(long slotid, int startslot, int endslot, Date dateofshoot, long clientid, long photographerid, String status, String message, String address, double latitude, double longitude, String category) {
        this.slotid = slotid;
        this.startslot = startslot;
        this.endslot = endslot;
        this.dateofshoot = dateofshoot;
        this.clientid = clientid;
        this.photographerid = photographerid;
        this.status = status;
        this.message = message;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public Date getDateofshoot() {
        return dateofshoot;
    }

    public String getMessage() {
        return message;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
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
        return dateofshoot;
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

    public void setDateofshoot(Date dateofshoot) {
        this.dateofshoot = dateofshoot;
    }
}
