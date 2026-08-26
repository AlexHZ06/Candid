package com.nea.candid.repositories;

import com.nea.candid.data.dbEnties.InteractionsTableEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class InteractionsTableRepo {

    private final JdbcTemplate jdbcTemplate;

    public InteractionsTableRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addInteraction(long photoId, long profileId, String interaction, Date interactionDate){

        String sql = """
                Insert Into interactionstable(photoid, profileid, interaction, interactiondate)
                values(?,?,?,?)
                """;

        return jdbcTemplate.update(sql, photoId, profileId, interaction, interactionDate);

    }

    public List<InteractionsTableEntity> getInteractionsByProfile(long profileId){

        String sql = """
                select *
                from  interactionstable
                where profileid=?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            return new InteractionsTableEntity(

                    rs.getLong("interactionid"),
                    rs.getLong("photoid"),
                    rs.getLong("profileid"),
                    rs.getString("interaction"),
                    rs.getDate("interactiondate")

                    );

        });

    }

    public List<InteractionsTableEntity> getInteractionsByPhoto(long photoId){

        String sql = """
                select * 
                from   interactionstable
                where photoid=?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            return new InteractionsTableEntity(

                    rs.getLong("interactionid"),
                    rs.getLong("photoid"),
                    rs.getLong("profileid"),
                    rs.getString("interaction"),
                    rs.getDate("interactiondate")

            );

        });

    }

}
