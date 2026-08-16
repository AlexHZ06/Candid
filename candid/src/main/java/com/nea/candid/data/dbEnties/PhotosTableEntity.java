package com.nea.candid.data.dbEnties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "photostable")
public class PhotosTableEntity {

    @Id
    private long photoid;
    private long userid;
    private String photoname;
    private String description;
    private String category;
    private LocalDateTime dateposted;
    private String photourl;
    private String thumbnailurl;
    private float filesize;
    private float width;
    private float height;

    public PhotosTableEntity(long photoid, long userid, String photoname, String description, String category, LocalDateTime dateposted, String photourl, String thumbnailurl, float filesize, float width, float height) {
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

    public LocalDateTime getDateposted() {
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

}
