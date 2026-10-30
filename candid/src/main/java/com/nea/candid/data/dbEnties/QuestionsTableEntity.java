package com.nea.candid.data.dbEnties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class QuestionsTableEntity {

    @Id
    private long questionid;
    private long photographerid;
    private String question;
    private boolean active;

    public QuestionsTableEntity(boolean active, String question, long photographerid, long questionid) {
        this.active = active;
        this.question = question;
        this.photographerid = photographerid;
        this.questionid = questionid;
    }

    public long getQuestionid() {
        return questionid;
    }

    public long getPhotographerid() {
        return photographerid;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isActive() {
        return active;
    }
}
