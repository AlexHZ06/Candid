package com.nea.candid.services;

import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.services.database.PhotosTableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class ImageService {

    private final PhotosTableService photosTableService;

    public ImageService(PhotosTableService photosTableService) {
        this.photosTableService = photosTableService;
    }

    @Transactional
    public ResponseBody saveImage(MultipartFile file, String photoName, long userId, String[] tags) {

        final String[] characters = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        String newName = photoName + ".jpg";

        Path path = null;



        try{

            path = Paths.get("C:\\Users\\Alexander Hernandez\\Desktop\\Programing\\Projects\\Candid\\Storage\\Test\\" + newName);
            boolean pathExists = Files.exists(path);

            String addon = "";
            Random random = new Random();

            while (pathExists) {

                for(int i =0; i < 6; i++){

                    addon = addon + characters[random.nextInt(characters.length)];

                }

                newName = photoName + addon + ".jpg";
                path = Paths.get("C:\\Users\\Alexander Hernandez\\Desktop\\Programing\\Projects\\Candid\\Storage\\Test\\" + newName);
                pathExists = Files.exists(path);

            }

            Files.createDirectories(path.getParent());
            BufferedImage image = ImageIO.read(file.getInputStream());

            ImageIO.write(image, "jpg", path.toFile());

            long photoId = photosTableService.addToPhotosTable(userId, photoName, "", "", LocalDateTime.now(), path.toString(), "", file.getSize(), image.getWidth(), image.getHeight());

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
            throw new RuntimeException("Failed to save image", e);

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
