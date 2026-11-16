package com.nea.candid.repositories;

import com.nea.candid.data.dbEnties.PhotosTableEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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

    public List<PhotosTableEntity> getFeedPhotos(
            List<String> words,
            List<Long> excludeIds,
            String category,
            float minPrice,
            float maxPrice,
            List<Long> validPhotographers,
            int neededWords) {

        String sql = """
            SELECT DISTINCT photostable.*
            FROM photostable, userstable
            WHERE userstable.userid = photostable.userid
            AND EXISTS (
                SELECT 1
                FROM unnest(string_to_array(photostable.category, ',')) AS c(value)
                WHERE c.value = ?
            )
            """;

        List<Object> params = new ArrayList<>();
        params.add(category);

        if (!words.isEmpty()) {

            sql = sql + """
                AND (
                    SELECT COUNT(DISTINCT keywordstable.word)
                    FROM keywordstable
                    WHERE keywordstable.photoid = photostable.photoid
                    AND keywordstable.word IN (
                """;

            sql = sql + String.join(", ", Collections.nCopies(words.size(), "?"));
            sql = sql + ")) >= ?";

            params.addAll(words);
            params.add(neededWords);
        }

        if (!excludeIds.isEmpty()) {

            sql = sql + " AND photostable.photoid NOT IN ("
                    + String.join(", ", Collections.nCopies(excludeIds.size(), "?"))
                    + ")";

            params.addAll(excludeIds);
        }

        if (!validPhotographers.isEmpty()) {

            sql = sql + " AND photostable.userid IN ("
                    + String.join(", ", Collections.nCopies(validPhotographers.size(), "?"))
                    + ")";

            params.addAll(validPhotographers);
        }

        sql = sql + """
            AND EXISTS (
                SELECT 1
                FROM catpricetable
                WHERE catpricetable.userid = photostable.userid
                AND catpricetable.category = ?
            """;

        params.add(category);

        if (minPrice != -1) {
            sql = sql + " AND catpricetable.avprice >= ?";
            params.add(minPrice);
        }

        if (maxPrice != -1) {
            sql = sql + " AND catpricetable.avprice <= ?";
            params.add(maxPrice);
        }

        sql = sql + ")";

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

        }, params.toArray());
    }

    public float getCatPrice(String category, long userid) {

        String sql = """
                select avprice
                from catpricetable
                where userid = ?
                and category = ?
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

            return rs.getFloat("avprice");

        }, userid, category);

    }

}
