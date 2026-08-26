package com.nea.candid.data.dbEnties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "photostable")
public class PhotosTableEntity {

    @Id
    private final  long photoid;
    private final  long userid;
    private final  String photoname;
    private final  String description;
    private final  String category;
    private final Date dateposted;
    private final  String photourl;
    private final  String thumbnailurl;
    private final  float filesize;
    private final  float width;
    private final  float height;
    private final  float[] globalembeddedvector;

    public PhotosTableEntity(long photoid, long userid, String photoname, String description, String category, Date dateposted, String photourl, String thumbnailurl, float filesize, float width, float height, float[] globalEmbeddedVector) {
        this.photoid = photoid;
        this.userid = userid;
        this.photoname = photoname;
        this.description = description;
        this.category = category;
        this.dateposted = dateposted;
        this.photourl = photourl;
        this.thumbnailurl = thumbnailurl;
        this.filesize = filesize;
        this.width = width;
        this.height = height;
        this.globalembeddedvector = globalEmbeddedVector;
    }

    public long getPhotoid() {
        return photoid;
    }

    public long getUserid() {
        return userid;
    }

    public String getPhotoname() {
        return photoname;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public Date getDateposted() {
        return dateposted;
    }

    public String getPhotourl() {
        return photourl;
    }

    public String getThumbnailurl() {
        return thumbnailurl;
    }

    public float getFilesize() {
        return filesize;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float[] getGlobalEmbeddedVector() {
        return globalembeddedvector;
    }
}
