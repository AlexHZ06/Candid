package com.nea.candid.repositories;

import com.nea.candid.data.dbEnties.PhotoInteractionsTableEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class PhotoInteractionsTableRepo {

    private JdbcTemplate jdbcTemplate;

    public int addInteraction(long interactionid, long profileid, long photoid, String interactiontype, Date interactedat, int duration, float weight) {

        String sql = """
                    insert into photointeractions(interactionid,profileid,photoid,interactiontype,interactedat,duration, weight)
                    values(?,?,?,?,?,?,?)
                """;

        return jdbcTemplate.update(sql,interactionid,profileid,photoid,interactiontype,interactedat,duration,weight);

    }

    public PhotoInteractionsTableEntity getInteractionByProfile(long profileId, Date interactedat){

        String sql = """
                    select *
                    from photointeractions
                    where userid = ?
                    and interactedat > ?
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->{

            return new PhotoInteractionsTableEntity(
                    rs.getLong("interactionid"),
                    rs.getLong("profileid"),
                    rs.getLong("photoid"),
                    rs.getString("interactiontype"),
                    rs.getDate("interactedat"),
                    rs.getInt("duration"),
                    rs.getFloat("weight")
            );

        },  profileId, interactedat);

    }

}
