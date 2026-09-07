package com.nea.candid.data.dataObjects;

import com.nea.candid.data.dbEnties.PhotosTableEntity;

public class FeedImage {

    private final PhotosTableEntity photo;
    private final float likeScore;

    public FeedImage(PhotosTableEntity photo, float likeScore) {
        this.photo = photo;
        this.likeScore = likeScore;

    }

    public PhotosTableEntity getPhoto() {
        return photo;
    }

    public float getLikeScore() {
        return likeScore;
    }


}
