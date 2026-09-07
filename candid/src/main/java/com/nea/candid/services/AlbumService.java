package com.nea.candid.services;

import com.nea.candid.data.dataObjects.ImageProfileObject;
import com.nea.candid.data.dbEnties.AlbumsTableEntity;
import com.nea.candid.data.dbEnties.PhotosTableEntity;
import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.services.database.PhotosDbService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

@Service
public class AlbumService {

    private final PhotosDbService photosDbService;
    private final ImageDecoderService imageDecoderService;

    public AlbumService(PhotosDbService photosDbService, ImageDecoderService imageDecoderService) {
        this.photosDbService = photosDbService;
        this.imageDecoderService = imageDecoderService;
    }

    public String makeThumbNail(MultipartFile file, String albumName){

        String thumbnail = null;
        Path path = null;
        String newName = "";

        try{

            newName = albumName + "Thumbnail.jpg";

            BufferedImage image = ImageIO.read(file.getInputStream());
            path = Paths.get("C:\\Users\\Alexander Hernandez\\Desktop\\Programing\\Projects\\Candid\\Storage\\TestThumbnails\\" + newName);
            boolean pathExists = Files.exists(path);

            final String[] characters = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
            String addon = "";

            Random random = new Random();

            while (pathExists) {

                for(int i =0; i < 6; i++){

                    addon = addon + characters[random.nextInt(characters.length)];

                }

                newName = albumName + addon + "Thumbnail.jpg";
                path = Paths.get("C:\\Users\\Alexander Hernandez\\Desktop\\Programing\\Projects\\Candid\\Storage\\TestThumbnails\\" + newName);
                pathExists = Files.exists(path);

            }

            Files.createDirectories(path.getParent());
            thumbnail = path.toString();
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            ImageProfileObject imageProfileObject = new ImageProfileObject(bufferedImage);
            BufferedImage thumbNail = imageDecoderService.createThumbnailSquare(imageProfileObject, 600);
            ImageIO.write(thumbNail, "jpg", path.toFile());
            return "/storage/public/TestThumbnails/" + newName;

        } catch (IOException e) {

            return null;

        }

    }

    public ResponseBody createAlbum(long userId, String albumName, MultipartFile file, String description){



        try{

            String url = makeThumbNail(file, albumName);
            photosDbService.addAlbum(userId, albumName, url, description);
            return ResponseBody.success("album saved", 951);

        }catch(Exception e){


            return ResponseBody.error("album was not saved", 901);

        }

    }


    public ResponseBody addToAlbum(long albumId, long photoId){

        try{

            photosDbService.addPhotoToAlbum(albumId, photoId);
            return ResponseBody.success("photo added", 951);

        }catch(Exception e){

            return ResponseBody.error("photo was not added", 901);

        }

    }

    public ResponseBody getAllPhotosFromAlbum(long albumId){

        List<PhotosTableEntity> photos = photosDbService.getAllPhotosFromAlbum(albumId);
        if(photos.isEmpty()){

            return ResponseBody.error("no photos found", 904);

        }
        else {

            return ResponseBody.success(photos, 953);

        }

    }


    public ResponseBody deleteAlbum(long albumId){

        try{

            photosDbService.deleteAlbum(albumId);
            return ResponseBody.success("album deleted", 952);

        }catch(Exception e){

            return ResponseBody.error("album was not deleted", 903);

        }


    }


    public ResponseBody removeFromAlbum(long albumId, long photoId){

        try {
            photosDbService.removePhotoFromAlbum(albumId, photoId);
            return ResponseBody.success("album removed", 952);
        }
        catch(Exception e){

            return ResponseBody.error("album was not removed", 903);

        }

    }

    public ResponseBody getAllAlbums(long userId){

        List<AlbumsTableEntity> albums = photosDbService.getAllAlbumsOfPhotographer(userId);
        if(albums.isEmpty()){

            return ResponseBody.error("no albums found", 904);

        }
        else{

            return ResponseBody.success(albums, 903);

        }

    }

    public ResponseBody getAlbum(long albumId){

        AlbumsTableEntity album =  photosDbService.getAlbum(albumId);
        if(album == null){

            return ResponseBody.error("no album found", 904);

        }
        else {

            return ResponseBody.success(album, 903);
        }

    }

    public ResponseBody editAlbum(long albumId, String name, String thumbnail, String description, boolean updateThumbnail, MultipartFile file){

        try{

            if(updateThumbnail){

                thumbnail = makeThumbNail(file, name);

            }
            photosDbService.changeAlbumDetails(albumId, name, thumbnail, description);
            return ResponseBody.success("album edited", 952);

        }
        catch(Exception e){

            return ResponseBody.error("album was not edited", 903);

        }

    }

}
