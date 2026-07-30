package com.nea.candid.services.database;

import com.nea.candid.data.dbEnties.TagsTableEntity;
import com.nea.candid.repositories.PhotosTableRepo;
import com.nea.candid.repositories.TagsTableRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class PhotosTableService {

    private final PhotosTableRepo photosTableRepo;
    private final TagsTableRepo tagsTableRepo;

    public PhotosTableService(PhotosTableRepo photosTableRepo, TagsTableRepo tagsTableRepo) {
        this.photosTableRepo = photosTableRepo;
        this.tagsTableRepo = tagsTableRepo;
    }

    public Long addToPhotosTable(long userid, String photoName, String description, String category, LocalDateTime datePosted, String photoUrl, String thumbNailUrl, float fileSize, float width, float height){

        return photosTableRepo.addPhoto(userid, photoName, description, category, datePosted, photoUrl, thumbNailUrl, fileSize, width, height);

    }

    public int[] addTags(String[] tags){

        return tagsTableRepo.addTags(tags);

    }

    public List<Long> getTagIds(String[] tags){

        return tagsTableRepo.getTagIds(tags);

    }

    public int[] addPhotoTags(Long photoId, List<Long> tagids){

        return photosTableRepo.addPhotoTags(photoId, tagids);

    }


}
