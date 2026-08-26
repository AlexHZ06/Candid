package com.nea.candid.data.dbEnties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sectionVectorsTable")
public class SectionEmbeddedVectorsTableEntity {

    @Id
    private final long vectorid;
    private final long photoid;
    private final int sectionnum;
    private final long[] embeddedvector;

    public SectionEmbeddedVectorsTableEntity(long vectorid, long photoid, int sectionnum, long[] embeddedvector) {
        this.vectorid = vectorid;
        this.photoid = photoid;
        this.sectionnum = sectionnum;
        this.embeddedvector = embeddedvector;
    }

    public long getVectorid() {
        return vectorid;
    }

    public long getPhotoid() {
        return photoid;
    }

    public int getSectionnum() {
        return sectionnum;
    }

    public long[] getEmbeddedvector() {
        return embeddedvector;
    }
}
