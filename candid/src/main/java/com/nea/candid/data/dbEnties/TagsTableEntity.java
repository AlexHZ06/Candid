package com.nea.candid.data.dbEnties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tagstable")
public class TagsTableEntity {

    @Id
    private final long tagid;
    private final String tagname;

    public TagsTableEntity(long tagid, String tagname) {
        this.tagid = tagid;
        this.tagname = tagname;
    }

    public long getTagid() {
        return tagid;
    }

    public String getTagname() {
        return tagname;
    }
}
