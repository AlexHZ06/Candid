package com.nea.candid.data.dbEnties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "schedulestable")
public class SchedulesTableEntity {

    @Id
    private final long scheduleid;
    private final int startslot;
    private final int endslot;
    private final int dayofweek;
    private final long photographerid;

    public SchedulesTableEntity(long scheduleid, int startSlot, int endSlot, int dayofweek, long photographerid) {
        this.scheduleid = scheduleid;
        this.startslot = startSlot;
        this.endslot = endSlot;
        this.dayofweek = dayofweek;
        this.photographerid = photographerid;
    }

    public long getPhotographerid() {
        return photographerid;
    }

    public long getScheduleid() {
        return scheduleid;
    }

    public int getStartSlot() {
        return startslot;
    }

    public int getEndSlot() {
        return endslot;
    }

    public int getDayofweek() {
        return dayofweek;
    }
}
