package com.nea.candid.data.dbEnties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "albumstable")
public class AlbumsTableEntity {

    @Id
    private final long albumid;
    private final long photographerid;
    private final String albumname;
    private final String thumnail;
    private final boolean visible;
    private final String description;

    public AlbumsTableEntity(long albumid, long photographerid, String albumname, String thumnail, boolean visible, String description) {
        this.albumid = albumid;
        this.photographerid = photographerid;
        this.albumname = albumname;
        this.thumnail = thumnail;
        this.visible = visible;
        this.description = description;
    }

    public long getAlbumid() {
        return albumid;
    }

    public String getDescription() {
        return description;
    }

    public long getPhotographerid() {
        return photographerid;
    }

    public String getAlbumname() {
        return albumname;
    }



    public String getThumnail() {
        return thumnail;
    }

    public boolean isVisible() {
        return visible;
    }
}
