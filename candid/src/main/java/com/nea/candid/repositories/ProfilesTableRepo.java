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

    public int insertProfile(long userId, String profileName, String profileDescription, LocalDateTime createdAt, float mincost, float maxcost, String projectCatagory, double latitude, double longitude){

        String sql = """
                   insert into profilestable(userid, profilename, profiledescription, creationdate, active, mincost, maxcost, projectcatagory, latitude, longitude
                   values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;

        return jdbcTemplate.update(sql, userId, profileName, profileDescription, createdAt, false, mincost, maxcost, projectCatagory, latitude, longitude);

    }

    public ProfilesTableEntity getProfile(long userId, String profileName){

        String sql = """
                    select *
                    from profilestable
                    where userid = ? and profilename = ?;
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

            float[][] vector = (float[][]) rs.getArray("preferencevector").getArray();

            return new ProfilesTableEntity(
                    rs.getLong("profileid"),
                    rs.getLong("userid"),
                    rs.getString("profilename"),
                    rs.getString("profiledescription"),
                    rs.getDate("createdAt"),
                    rs.getBoolean("active"),
                    rs.getFloat("mincost"),
                    rs.getFloat("maxcost"),
                    rs.getString("projectCatagory"),
                    vector,
                    rs.getInt("photointeractions"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude")
            );

        }, userId, profileName);

    }

    public ProfilesTableEntity getProfileById(long profileId){

        String sql = """
                select *
                from profilestable
                where profileid = ?;
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

            float[][] vector = (float[][]) rs.getArray("preferencevector").getArray();

            return new ProfilesTableEntity(
                    rs.getLong("profileid"),
                    rs.getLong("userid"),
                    rs.getString("profilename"),
                    rs.getString("profiledescription"),
                    rs.getDate("createdAt"),
                    rs.getBoolean("active"),
                    rs.getFloat("mincost"),
                    rs.getFloat("maxcost"),
                    rs.getString("projectCatagory"),
                    vector,
                    rs.getInt("photointeractions"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude")
            );

        }, profileId);

    }

    public List<ProfilesTableEntity> getAllProfiles(long userId){

        String sql = """
                    select *
                    from profilestable
                    where userid = ?;
                """;

        return jdbcTemplate.query(sql, (rs, rowMap) ->{

            float[][] vector = (float[][]) rs.getArray("preferencevector").getArray();

            return new ProfilesTableEntity(
                    rs.getLong("profileid"),
                    rs.getLong("userid"),
                    rs.getString("profilename"),
                    rs.getString("profiledescription"),
                    rs.getDate("createdAt"),
                    rs.getBoolean("active"),
                    rs.getFloat("mincost"),
                    rs.getFloat("maxcost"),
                    rs.getString("projectCatagory"),
                    vector,
                    rs.getInt("photointeractions"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude")

            );

        }, userId);
    }

    public int setPreferenceVector(long profileid, float[][] preferenceVector){

        String sql = """
                update profilestable
                set preferencevector = ?;
                where profileid = ?;
                """;

        return jdbcTemplate.update(sql, preferenceVector, profileid);

    }

}
