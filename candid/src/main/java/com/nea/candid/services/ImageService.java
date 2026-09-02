package com.nea.candid.services;

import com.nea.candid.data.dataObjects.ImageProfileObject;
import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.services.database.PhotosDbService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class ImageService {

    private final PhotosDbService photosTableService;
    private final ImageDecoderService imageDecoderService;

    public ImageService(PhotosDbService photosTableService, ImageDecoderService imageDecoderService) {
        this.photosTableService = photosTableService;
        this.imageDecoderService = imageDecoderService;
    }


    @Transactional
    public ResponseBody saveImage(MultipartFile file, String photoName, String category,String description, long userId, String[] tags, long albumId) {

        final String[] characters = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        String newName = photoName + ".jpg";
        String newThumbName = photoName + "Thumbnail.jpg";

        Path path = null;
        Path thumbnailPath = null;



        try{

            path = Paths.get("C:\\Users\\Alexander Hernandez\\Desktop\\Programing\\Projects\\Candid\\Storage\\Test\\" + newName);
            thumbnailPath = Paths.get("C:\\Users\\Alexander Hernandez\\Desktop\\Programing\\Projects\\Candid\\Storage\\TestThumbnails\\" + newThumbName);
            boolean pathExists = Files.exists(thumbnailPath) || Files.exists(path);


            String addon = "";
            Random random = new Random();

            while (pathExists) {

                for(int i =0; i < 6; i++){

                    addon = addon + characters[random.nextInt(characters.length)];

                }

                newName = photoName + addon + ".jpg";
                newThumbName = photoName + addon + "Thumbnail.jpg";
                path = Paths.get("C:\\Users\\Alexander Hernandez\\Desktop\\Programing\\Projects\\Candid\\Storage\\Test\\" + newName);
                thumbnailPath = Paths.get("C:\\Users\\Alexander Hernandez\\Desktop\\Programing\\Projects\\Candid\\Storage\\TestThumbnails\\" + newThumbName);
                pathExists = Files.exists(thumbnailPath) || Files.exists(path);

            }

            Files.createDirectories(path.getParent());
            Files.createDirectories(thumbnailPath.getParent());

            BufferedImage image = ImageIO.read(file.getInputStream());


            ImageIO.write(image, "jpg", path.toFile());

            ImageProfileObject imageProfileObject = new ImageProfileObject(image);

            BufferedImage thumbnail = imageDecoderService.createThumbnail(imageProfileObject, 600);
            ImageIO.write(thumbnail, "jpg", thumbnailPath.toFile());

            imageDecoderService.calculateValues(imageProfileObject);
            imageDecoderService.printImageValues(imageProfileObject);
            long photoId = photosTableService.addToPhotosTable(userId, photoName, description, category, LocalDateTime.now(), "/storage/public/Test/" + newName, "/storage/public/TestThumbnails/" + newThumbName, file.getSize(), image.getWidth(), image.getHeight(), imageProfileObject.getGlobalVector());
            photosTableService.insertSectionVectors(photoId, imageProfileObject.getSectionVectors());
            photosTableService.addPhotoToAlbum(albumId, photoId);

            List<Long> tagids = saveTags(tags);
            addPhotoTags(photoId, tagids);

        }catch (Exception e){

            if(path != null){

                try{
                    Files.delete(path);
                }catch (IOException ioe){

                    e.addSuppressed(ioe);

                }

            }

            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            throw new RuntimeException("Failed to save image " + Arrays.toString(e.getStackTrace()));

        }

        return ResponseBody.success("image saved", 351);

    }

    public List<Long> saveTags(String[] tags){

            photosTableService.addTags(tags);
            List<Long> result = photosTableService.getTagIds(tags);
            if(result.size() == tags.length){

                return result;

            }
            throw new RuntimeException("tags id's do not align with number of tags");

    }

    public void addPhotoTags(long photoid, List<Long> tagid){

            int[] result = photosTableService.addPhotoTags(photoid, tagid);
            if(result.length != tagid.size()){

                throw new RuntimeException("Tags junctions do not align with number of tags");

            }

    }


    //TODO implement delete photo
    public void deletePhoto(){

        //TODO delete all photo tags
        //TODO Delete from storage
        //TODO Delete from database
    }
}
