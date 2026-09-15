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
    private final long scheduleid;
    private final int startslot;
    private final int endslot;
    private final int dayofweek;
    private final long photographerid;

    @JsonCreator
    public SchedulesTableEntity(
            @JsonProperty("scheduleid") long scheduleid,
            @JsonProperty("startslot") int startSlot,
            @JsonProperty("endslot") int endSlot,
            @JsonProperty("dayofweek") int dayofweek,
            @JsonProperty("photographerid") long photographerid
    ) {
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
