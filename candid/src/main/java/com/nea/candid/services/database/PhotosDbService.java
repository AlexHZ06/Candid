package com.nea.candid.services.database;

import com.nea.candid.data.dbEnties.AlbumsTableEntity;
import com.nea.candid.data.dbEnties.PhotosTableEntity;
import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.repositories.AlbumsTableRepo;
import com.nea.candid.repositories.PhotosTableRepo;
import com.nea.candid.repositories.SectionVectorsTableRepo;
import com.nea.candid.repositories.TagsTableRepo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhotosDbService {

    private final PhotosTableRepo photosTableRepo;
    private final TagsTableRepo tagsTableRepo;
    private final SectionVectorsTableRepo sectionVectorsTableRepo;
    private final AlbumsTableRepo albumsTableRepo;


    public PhotosDbService(PhotosTableRepo photosTableRepo, TagsTableRepo tagsTableRepo, SectionVectorsTableRepo sectionVectorsTableRepo, AlbumsTableRepo albumsTableRepo) {
        this.photosTableRepo = photosTableRepo;
        this.tagsTableRepo = tagsTableRepo;
        this.sectionVectorsTableRepo = sectionVectorsTableRepo;
        this.albumsTableRepo = albumsTableRepo;
    }

    public Long addToPhotosTable(long userid, String photoName, String description, String category, LocalDateTime datePosted, String photoUrl, String thumbNailUrl, float fileSize, float width, float height, float[] globalVector) {

        return photosTableRepo.addPhoto(userid, photoName, description, category, datePosted, photoUrl, thumbNailUrl, fileSize, width, height, globalVector);

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

    public List<float[]> getSectionVectors(long ImageId){

        return sectionVectorsTableRepo.getSectionVectors(ImageId);

    }

    public void insertSectionVectors(long photoId, ArrayList<float[]> sectionVectors){

        int[] result = sectionVectorsTableRepo.insertSectionVectors(photoId, sectionVectors);

        for(int i = 0; i < result.length; i++){

            if(result[i] == 0){

                throw new DataIntegrityViolationException("Not all section Vectors where added");

            }

        }

    }

    public PhotosTableEntity getPhotosTableById(long photoid){

        return  photosTableRepo.getPhotoById(photoid);

    }

    public List<PhotosTableEntity> getFeed(List<String> tags, List<Long> userid, String category, int minTags, List<Long> excludeId){

        return photosTableRepo.getPhotosForFeed(tags, userid, category, minTags, excludeId);

    }

    public List<AlbumsTableEntity> getAllAlbumsOfPhotographer(long photographerid){

        return  albumsTableRepo.getAllAlbumsOfPhotographer(photographerid);

    }

    public void addAlbum(long userId, String albumName, String thumnail, String description){

        int result = albumsTableRepo.addAlbum( userId,  albumName,  thumnail, description);
        if(result == 0){

            throw new DataIntegrityViolationException("album has not been added");

        }

    }

    public void deleteAlbum(long albumId){

        int result = albumsTableRepo.deleteAlbum(albumId);

        if(result == 0){

            throw new DataIntegrityViolationException("album has not been deleted");

        }

    }

    public void changeAlbumDetails(long albumId, String newName, String thumbnail, String description){

        int result = albumsTableRepo.changeAlbumDetails(albumId, newName, thumbnail, description);
        if(result == 0){

            throw new DataIntegrityViolationException("album has not been updated");

        }

    }

    public void addPhotoToAlbum(long albumId, long photoId){

        int result =  albumsTableRepo.addPhotoToAlbum(albumId, photoId);
        if(result == 0){

            throw new DataIntegrityViolationException("photo has not been added");

        }

    }

    public void removePhotoFromAlbum(long albumId, long photoId){

        int result = albumsTableRepo.deletePhotoFromAlbum(albumId, photoId);
        if(result == 0){

            throw new DataIntegrityViolationException("photo has not been removed");

        }

    }

    public AlbumsTableEntity getAlbum(long albumId){

        return albumsTableRepo.getAlbum(albumId);

    }

    public List<PhotosTableEntity> getPreferencePhotos(int amount){

        return photosTableRepo.getPreferencePhotos(amount);

    }

    public List<PhotosTableEntity> getAllPhotosFromAlbum(long albumId){

        return albumsTableRepo.getAllImagesFromAlbum(albumId);

    }

}
