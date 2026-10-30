package com.nea.candid.data.dbEnties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ResponsesTableEntity {

    @Id
    private long responseid;
    private String response;
    private long questionid;
    private long bookingid;

    public ResponsesTableEntity(long responseid, String response, long questionid, long bookingid) {
        this.responseid = responseid;
        this.response = response;
        this.questionid = questionid;
        this.bookingid = bookingid;
    }

    public long getResponseid() {
        return responseid;
    }

    public String getResponse() {
        return response;
    }

    public long getQuestionid() {
        return questionid;
    }

    public long getBookingid() {
        return bookingid;
    }
}
