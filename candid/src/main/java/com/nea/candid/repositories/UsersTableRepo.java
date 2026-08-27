package com.nea.candid.repositories;

import com.nea.candid.data.dbEnties.UsersTableEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsersTableRepo {

    private final JdbcTemplate jdbcTemplate;

    public UsersTableRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UsersTableEntity getUserByUserName(String userName) {

        String sql = """
                select * 
                from userstable  
                where username = ?
                """;

        try {

            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

                return new UsersTableEntity(rs.getLong("userid"), rs.getString("username"), rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("usertype"),
                        rs.getString("email"),
                        rs.getString("hashedpassword"),
                        rs.getDate("datejoined")
                );

            }, userName);
        }
        catch (EmptyResultDataAccessException e) {

            return null;

        }

    }

    public UsersTableEntity getUserById(long userId) {

        String sql = """
                select * 
                from userstable  
                where userid = ?
                """;

        try {

            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

                return new UsersTableEntity(rs.getLong("userid"), rs.getString("username"), rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("usertype"),
                        rs.getString("email"),
                        rs.getString("hashedpassword"),
                        rs.getDate("datejoined")
                );

            }, userId);
        }
        catch (EmptyResultDataAccessException e) {

            return null;

        }

    }

    public List<Long> getPhotographerByDistanceBounds(double minLat, double minLon, double maxLat, double maxLon){

        String sql  = """
                select userid
                from userstable
                where usertype = 'photographer'
                and latitude between ? and ?
                and longitude between ? and ?
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            return rs.getLong("userid");

        }, minLat, maxLat, minLon, maxLon);

    }

}

