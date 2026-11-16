package com.nea.candid.data.dbEnties;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "schedulestable")
public class SchedulesTableEntity {

    @Id
    private long scheduleid;
    private int startslot;
    private int endslot;
    private int dayofweek;
    private long photographerid;

    public SchedulesTableEntity(long scheduleid, int startslot, int endslot, int dayofweek, long photographerid) {
        this.scheduleid = scheduleid;
        this.startslot = startslot;
        this.endslot = endslot;
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
