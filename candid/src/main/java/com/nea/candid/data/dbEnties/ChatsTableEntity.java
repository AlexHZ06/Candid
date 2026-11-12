package com.nea.candid.data.dbEnties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Timestamp;
import java.time.LocalTime;

@Entity
public class ChatsTableEntity {

    @Id
    private long chatid;
    private String chatmessage;
    private long bookingid;
    private Timestamp timesent;

    public ChatsTableEntity(long chatid, String chatmessage, long bookingid, Timestamp timesent) {
        this.chatid = chatid;
        this.chatmessage = chatmessage;
        this.bookingid = bookingid;
        this.timesent = timesent;
    }

    public long getChatid() {
        return chatid;
    }

    public String getChatmessage() {
        return chatmessage;
    }

    public long getBookingid() {
        return bookingid;
    }

    public Timestamp getTimesent() {
        return timesent;
    }
}
