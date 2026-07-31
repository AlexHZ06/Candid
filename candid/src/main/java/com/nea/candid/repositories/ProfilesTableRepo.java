package com.nea.candid.repositories;

import com.nea.candid.data.dbEnties.ProfilesTableEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ProfilesTableRepo {

    private final JdbcTemplate jdbcTemplate;

    public ProfilesTableRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insertProfile(long userId, String profileName, String profileDescription, LocalDateTime createdAt, float mincost, float maxcost, String projectCatagory){

        String sql = """
                   insert into profilestable(userid, profilename, profiledescription, creationdate, active, mincost, maxcost, projectcatagory)
                   values(?, ?, ?, ?, ?, ?, ?, ?);
                """;

        return jdbcTemplate.update(sql, userId, profileName, profileDescription, createdAt, false, mincost, maxcost, projectCatagory);

    }

    public ProfilesTableEntity getProfile(long userId, String profileName){

        String sql = """
                    select *
                    from profilestable
                    where userid = ? and profilename = ?;
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

            return new ProfilesTableEntity(
                    rs.getLong("profileid"),
                    rs.getLong("userid"),
                    rs.getString("profilename"),
                    rs.getString("profiledescription"),
                    rs.getDate("createdAt"),
                    rs.getBoolean("active"),
                    rs.getFloat("mincost"),
                    rs.getFloat("maxcost"),
                    rs.getString("projectCatagory")
            );

        }, userId, profileName);

    }

    public List<ProfilesTableEntity> getAllProfiles(long userId){

        String sql = """
                    select *
                    from profilestable
                    where userid = ?;
                """;

        return jdbcTemplate.query(sql, (rs, rowMap) ->{

            return new ProfilesTableEntity(
                    rs.getLong("profileid"),
                    rs.getLong("userid"),
                    rs.getString("profilename"),
                    rs.getString("profiledescription"),
                    rs.getDate("createdAt"),
                    rs.getBoolean("active"),
                    rs.getFloat("mincost"),
                    rs.getFloat("maxcost"),
                    rs.getString("projectCatagory")
            );

        }, userId);
    }

}
