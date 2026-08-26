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

    public long addPhoto(long userid, String photoName, String description, String category, LocalDateTime datePosted, String photoUrl, String thumbNailUrl, float fileSize, float width, float height, float[] globalEmbeddedVector) {

        String sql = """
                
                insert into photostable(userid, photoname, description, category, dateposted, photourl, thumbnailurl, filesize, width, height, globalEmbeddedVector)
                values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                returning photoid
                """;

        return jdbcTemplate.queryForObject(sql, Long.class, userid, photoName, description, category, datePosted, photoUrl, thumbNailUrl, fileSize, width, height, globalEmbeddedVector);

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
            float[] vector = new float[19];
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
                    rs.getFloat("filesize"),
                    rs.getFloat("width"),
                    rs.getFloat("height"),
                    vector
            );

        },photoid);

    }

}
