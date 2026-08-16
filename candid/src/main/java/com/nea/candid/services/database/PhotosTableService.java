package com.nea.candid.services.database;

import com.nea.candid.data.dbEnties.EmbeddedVectorTableEntity;
import com.nea.candid.data.dbEnties.TagsTableEntity;
import com.nea.candid.repositories.EmbeddedVectorsTableRepo;
import com.nea.candid.repositories.PhotosTableRepo;
import com.nea.candid.repositories.TagsTableRepo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PhotosTableService {

    private final PhotosTableRepo photosTableRepo;
    private final TagsTableRepo tagsTableRepo;
    private final EmbeddedVectorsTableRepo embeddedVectorsTableRepo;

    public PhotosTableService(PhotosTableRepo photosTableRepo, TagsTableRepo tagsTableRepo, EmbeddedVectorsTableRepo embeddedVectorsTableRepo) {
        this.photosTableRepo = photosTableRepo;
        this.tagsTableRepo = tagsTableRepo;
        this.embeddedVectorsTableRepo = embeddedVectorsTableRepo;
    }

    public Long addToPhotosTable(long userid, String photoName, String description, String category, LocalDateTime datePosted, String photoUrl, String thumbNailUrl, float fileSize, float width, float height) {

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

    public void addEmbeddedVectors(ArrayList<EmbeddedVectorTableEntity> vectors){

        int[] results = embeddedVectorsTableRepo.insertVector(vectors);
        if(results.length != vectors.size()){

            throw new DataIntegrityViolationException("Vectors where not saved");

        }
        else{

            for(int i = 0; i < vectors.size(); i++){

                if(results[i] == 0){

                    throw new DataIntegrityViolationException("Vectors where not saved");

                }

            }

        }

    }

}
