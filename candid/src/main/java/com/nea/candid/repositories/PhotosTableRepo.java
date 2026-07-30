package com.nea.candid.repositories;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

    public long addPhoto(long userid, String photoName, String description, String category, LocalDateTime datePosted, String photoUrl, String thumbNailUrl, float fileSize, float width, float height) {

        String sql = """
                
                insert into photostable(userid, photoname, description, category, dateposted, photourl, thumbnailurl, filesize, width, height)
                values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                returning photoid
                """;

        return jdbcTemplate.queryForObject(sql, Long.class, userid, photoName, description, category, datePosted, photoUrl, thumbNailUrl, fileSize, width, height);

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

}
