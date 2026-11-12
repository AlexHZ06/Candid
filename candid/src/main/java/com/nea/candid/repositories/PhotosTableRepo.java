package com.nea.candid.repositories;

import com.nea.candid.data.dbEnties.PhotosTableEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PhotosTableRepo {

    private final JdbcTemplate jdbcTemplate;

    public PhotosTableRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long addPhoto(long userid, String photoName, String description, String category, LocalDateTime datePosted, String photoUrl, String thumbNailUrl, float[] globalEmbeddedVector) {

        String sql = """
                
                insert into photostable(userid, photoname, description, category, dateposted, photourl, thumbnailurl, filesize, width, height, globalEmbeddedVector)
                values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                returning photoid
                """;

        return jdbcTemplate.queryForObject(sql, Long.class, userid, photoName, description, category, datePosted, photoUrl, thumbNailUrl, globalEmbeddedVector);

    }

    public int[] addPhotoTags(long photoid, List<Long> tagids) {

        String sql = """
                insert into phototagstable
                values(?,?);
                """;

        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, photoid);
                ps.setLong(2, tagids.get(i));
            }

            @Override
            public int getBatchSize() {
                return tagids.size();
            }

        });

    }

    public PhotosTableEntity getPhotoById(long photoid){

        String sql = """
                select * 
                from photostable
                where photoid = ?;
        """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

            Array sqlArray = rs.getArray("globalembeddedvector");
            Float[] boxed = (Float[]) sqlArray.getArray();
            float[] vector = new float[23];
            for(int i = 0; i < vector.length; i++){

                vector[i] = boxed[i].floatValue();

            }

            return new PhotosTableEntity(
                    rs.getLong("photoid"),
                    rs.getLong("userid"),
                    rs.getString("photoname"),
                    rs.getString("description"),
                    rs.getString("category"),
                    rs.getDate("dateposted"),
                    rs.getString("photourl"),
                    rs.getString("thumbnailurl"),
                    vector
            );

        },photoid);

    }

    public List<PhotosTableEntity> getPhotosForFeed(List<String> tags, List<Long> userid, String category, int minTags, List<Long> excludeId){

        String tagsPlaceHolder = "";
        for(int i = 0; i < tags.size(); i++){

            if(i == tags.size() -1){

                tagsPlaceHolder += "?";

            }
            else {

                tagsPlaceHolder += "?,";

            }

        }
        String excludesPlaceHolder = "";
        for(int i = 0; i < excludeId.size(); i++){

            if(i == excludeId.size()-1){

                excludesPlaceHolder += "?";

            }
            else {

                excludesPlaceHolder += "?,";

            }

        }
        String usersPlaceHolder = "";
        for(int i = 0; i < userid.size(); i++){

            if(i == userid.size()-1){

                usersPlaceHolder += "?";

            }
            else {

                usersPlaceHolder += "?,";

            }

        }

        String conditionalSql = "";
        if(!excludesPlaceHolder.isEmpty()){

            conditionalSql = """
                    and photostable.photoid not in (
                    """
                    +excludesPlaceHolder +
                    """
                    )
                    """;

        }

        String sql = """
                select *
                from photostable
                join phototagstable
                    on photostable.photoid = phototagstable.photoid
                join tagstable
                    on phototagstable.tagid = tagstable.tagid
                where photostable.category = ?
                and tagstable.tagname in ("""
                + tagsPlaceHolder +
                """
                )
                """
                + conditionalSql +
                """
                and photostable.userid in (
                """
                + usersPlaceHolder +
                """
                )
                group by photostable.photoid
                having count(distinct tagstable.tagname) >= ?;
                """;

        Object[] params = new Object[tags.size() + userid.size() + excludeId.size() + 2];
        params[0] = category;
        params[params.length - 1] = minTags;
        int finalIndex;
        for(finalIndex = 1; finalIndex <= tags.size(); finalIndex++){

            params[finalIndex] = tags.get(finalIndex - 1);

        }
        for(int i = 0; i < excludeId.size(); i++){

            params[finalIndex] = excludeId.get(i);
            finalIndex++;

        }
        for(int i = 0; i < userid.size(); i++){

                params[finalIndex] = userid.get(i);
                finalIndex++;
        }

        return jdbcTemplate.query(sql ,params, (rs, rowNum) -> {

            Array sqlArray = rs.getArray("globalembeddedvector");
            Float[] boxed = (Float[]) sqlArray.getArray();
            float[] vector = new float[23];
            for(int i = 0; i < vector.length; i++){

                vector[i] = boxed[i].floatValue();

            }

            return new PhotosTableEntity(
                    rs.getLong("photoid"),
                    rs.getLong("userid"),
                    rs.getString("photoname"),
                    rs.getString("description"),
                    rs.getString("category"),
                    rs.getDate("dateposted"),
                    rs.getString("photourl"),
                    rs.getString("thumbnailurl"),
                    vector
            );

        });

    }

    public List<PhotosTableEntity> getPreferencePhotos(int amount){

        String sql = """
                select *
                from photostable
                where userid = 4
                order by random()
                limit ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Array sqlArray = rs.getArray("globalembeddedvector");
            Float[] boxed = (Float[]) sqlArray.getArray();
            float[] vector = new float[23];
            for(int i = 0; i < vector.length; i++){

                vector[i] = boxed[i].floatValue();

            }

            return new PhotosTableEntity(
                    rs.getLong("photoid"),
                    rs.getLong("userid"),
                    rs.getString("photoname"),
                    rs.getString("description"),
                    rs.getString("category"),
                    rs.getDate("dateposted"),
                    rs.getString("photourl"),
                    rs.getString("thumbnailurl"),
                    vector
            );

        },amount);

    }

    public List<PhotosTableEntity> getPhotosByWords(List<String> words, List<Long> excludeIds, String category, int imagesNeeded, float minPrice, float maxPrice, List<Long> validPhotographers){



        String wordsList = words.getFirst();
        for(int i = 1; i < wordsList.length(); i++){

            wordsList = wordsList + ", " + words.get(i);

        }
        String idList = "" + excludeIds.getFirst();
        for(int i = 1; i < idList.length(); i++){

            idList = idList + ", " + excludeIds.get(i);

        }

        String photographerIds =  "";

        String sql = """
                select photostable.*
                from photostable, keywordstable, catpricetable, userstable
                where photostable.photoid = keyswordstable.photoid
                and userstable.userid = photostable.userid
                and userstable.userid = catpricetable.userid
                and catpricetable.avprice Between(? and ?)
                and photostable.tagname in (
                and keyswordstable.word in (""" + wordsList + """
                )
                and photostable.photoid not in ("""+ idList +"""
                )
                and category = ?
                group by photostable.photoid
                having count(distinct keywordstable.keyword) = ?
                """;

        if(validPhotographers != null){

            photographerIds =  "" + validPhotographers.getFirst();

            for(int i = 1; i < validPhotographers.size(); i++){

                photographerIds = photographerIds + ", " + validPhotographers.get(i);

            }

            sql = """
                select photostable.*
                from photostable, keywordstable, catpricetable, userstable
                where photostable.photoid = keyswordstable.photoid
                and userstable.userid = photostable.userid
                and userstable.userid = catpricetable.userid
                and catpricetable.avprice Between(? and ?)
                and photostable.tagname in (
                and keyswordstable.word in (""" + wordsList + """
                )
                and photostable.photoid not in ("""+ idList +"""
                )
                and photostable.userid in ("""+ photographerIds +"""
                )
                and category = ?
                group by photostable.photoid
                having count(distinct keywordstable.keyword) = ?
                """;

        }

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Array sqlArray = rs.getArray("globalembeddedvector");
            Float[] boxed = (Float[]) sqlArray.getArray();

            float[] vector = new float[23];

            for (int i = 0; i < vector.length; i++) {
                vector[i] = boxed[i].floatValue();
            }

            return new PhotosTableEntity(
                    rs.getLong("photoid"),
                    rs.getLong("userid"),
                    rs.getString("photoname"),
                    rs.getString("description"),
                    rs.getString("category"),
                    rs.getDate("dateposted"),
                    rs.getString("photourl"),
                    rs.getString("thumbnailurl"),
                    vector
            );
        },minPrice, maxPrice, category, imagesNeeded);

    }

}
