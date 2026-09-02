package com.nea.candid.repositories;

import com.nea.candid.data.dbEnties.AlbumsTableEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlbumsTableRepo {

    private final JdbcTemplate jdbcTemplate;

    public AlbumsTableRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addAlbum(long userId, String albumName, String thumnail, String description){

        String sql = """
                insert into albumstable(photographerid, albumname, thumnail, description, visible)
                values(?,?,?,?, true)
                """;

        return jdbcTemplate.update(sql, userId, albumName,  thumnail, description);

    }

    public int deleteAlbum(long albumId){

        String sql = """
                update albumstable
                set visible = false
                where albumid = ?;
                """;

        return jdbcTemplate.update(sql, albumId);

    }

    public List<AlbumsTableEntity> getAllAlbumsOfPhotographer(long userId) {

        String sql = """
                select *
                from albumstable
                where photographerid = ?
                and visible = true
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            return new  AlbumsTableEntity(
                    rs.getLong("albumid"),
                    rs.getLong("photographerid"),
                    rs.getString("albumname"),
                    rs.getString("thumnail"),
                    rs.getBoolean("visible"),
                    rs.getString("description")
            );

        }, userId);

    }

    public int addPhotoToAlbum(long albumId, long photoId){

        String sql = """
                insert into albumphotostable(albumid, photoid)
                values(?, ?)
                """;

        return jdbcTemplate.update(sql, albumId, photoId);

    }

    public int changeAlbumDetails(long albumId, String newName, String thumbnail, String description){
        String sql = """
                update albumstable
                set albumname = ?, thumnail = ?, description = ?
                where albumid = ?;
        """;

        return jdbcTemplate.update(sql, newName, thumbnail, description, albumId);
    }

    public int deletePhotoFromAlbum(long albumId, long photoId){

        String  sql = """
                delete from albumphotostable
                where albumid = ? 
                and photoid = ?;
                
        """;

        return jdbcTemplate.update(sql, albumId, photoId);

    }

    public AlbumsTableEntity getAlbum(long albumId){

        String sql = """
                select *
                from albumstable
                where albumid = ?;
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

            return new  AlbumsTableEntity(
                    rs.getLong("albumid"),
                    rs.getLong("photographerid"),
                    rs.getString("albumname"),
                    rs.getString("thumnail"),
                    rs.getBoolean("visible"),
                    rs.getString("description")
            );

        }, albumId);

    }

}
