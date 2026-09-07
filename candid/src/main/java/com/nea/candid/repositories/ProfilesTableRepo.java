package com.nea.candid.repositories;

import com.nea.candid.data.dbEnties.ProfilesTableEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ProfilesTableRepo {

    private final JdbcTemplate jdbcTemplate;

    public ProfilesTableRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private float[][] getFloat2DArray(ResultSet rs, String column)
            throws SQLException {

        Float[][] dbArray = (Float[][]) rs.getArray(column).getArray();

        float[][] result = new float[dbArray.length][];

        for (int i = 0; i < dbArray.length; i++) {
            result[i] = new float[dbArray[i].length];

            for (int j = 0; j < dbArray[i].length; j++) {
                result[i][j] = dbArray[i][j];
            }
        }

        return result;
    }

    public long insertProfile(long userId, String profileName, String profileDescription, LocalDateTime createdAt, float mincost, float maxcost, String projectCatagory, double latitude, double longitude){

        float[][] defaultVector = new float[23][17];
        for(int i=0; i<defaultVector.length; i++){
            for(int j=0; j<defaultVector[i].length; j++){

                defaultVector[i][j] = 0.5f;

            }
        }

        String sql = """
                   insert into profilestable(userid, profilename, profiledescription, creationdate, active, mincost, maxcost, projectcatagory, latitude, longitude, preferencevector, dislikesvector)
                   values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                   returning profileid
                """;

        return jdbcTemplate.queryForObject(sql, Long.class, userId, profileName, profileDescription, createdAt, false, mincost, maxcost, projectCatagory, latitude, longitude, defaultVector, defaultVector);

    }

    public ProfilesTableEntity getProfile(long userId, String profileName){

        String sql = """
                    select *
                    from profilestable
                    where userid = ? 
                    and profilename = ?;
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

            float[][] preferenceVector = getFloat2DArray(rs, "preferencevector");

            float[][] dislikesVector = getFloat2DArray(rs, "dislikesvector");

            return new ProfilesTableEntity(
                    rs.getLong("profileid"),
                    rs.getLong("userid"),
                    rs.getString("profilename"),
                    rs.getString("profiledescription"),
                    rs.getBoolean("active"),
                    rs.getFloat("mincost"),
                    rs.getFloat("maxcost"),
                    rs.getString("projectCatagory"),
                    preferenceVector,
                    dislikesVector,
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude")
            );

        }, userId, profileName);

    }

    public ProfilesTableEntity getProfileById(long profileId){

        String sql = """
                select *
                from profilestable
                where profileid = ?
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

            float[][] preferenceVector = getFloat2DArray(rs, "preferencevector");

            float[][] dislikesVector = getFloat2DArray(rs, "dislikesvector");


            return new ProfilesTableEntity(
                    rs.getLong("profileid"),
                    rs.getLong("userid"),
                    rs.getString("profilename"),
                    rs.getString("profiledescription"),
                    rs.getBoolean("active"),
                    rs.getFloat("mincost"),
                    rs.getFloat("maxcost"),
                    rs.getString("projectCatagory"),
                    preferenceVector,
                    dislikesVector,
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

            Float[][] dbPreference = (Float[][]) rs.getArray("preferencevector").getArray();

            Float[][] dbDislikes = (Float[][]) rs.getArray("dislikesvector").getArray();

            float[][] preferenceVector = new float[dbPreference.length][];

            for (int i = 0; i < dbPreference.length; i++) {
                preferenceVector[i] = new float[dbPreference[i].length];

                for (int j = 0; j < dbPreference[i].length; j++) {
                    preferenceVector[i][j] = dbPreference[i][j].floatValue();
                }
            }

            float[][] dislikesVector = new float[dbDislikes.length][];

            for (int i = 0; i < dbDislikes.length; i++) {
                dislikesVector[i] = new float[dbDislikes[i].length];

                for (int j = 0; j < dbDislikes[i].length; j++) {
                    dislikesVector[i][j] = dbDislikes[i][j].floatValue();
                }
            }

            return new ProfilesTableEntity(
                    rs.getLong("profileid"),
                    rs.getLong("userid"),
                    rs.getString("profilename"),
                    rs.getString("profiledescription"),
                    rs.getBoolean("active"),
                    rs.getFloat("mincost"),
                    rs.getFloat("maxcost"),
                    rs.getString("projectCatagory"),
                    preferenceVector,
                    dislikesVector,
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude")

            );

        }, userId);
    }

    public int setPreferenceVector(long profileid, float[][] preferenceVector){

        String sql = """
                update profilestable
                set preferencevector = ?
                where profileid = ?;
                """;

        return jdbcTemplate.update(sql, preferenceVector, profileid);

    }

    public int setDislikeVector(long profileid, float[][] dislikesVector){

        String sql = """
                update profilestable
                set dislikesvector = ?
                where profileid = ?;
                """;

        return jdbcTemplate.update(sql, dislikesVector, profileid);

    }

}
