package com.nea.candid.repositories;

import com.nea.candid.data.dbEnties.RefreshTokenTableEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokenTableRepo {

    private final JdbcTemplate jdbcTemplate;

    public RefreshTokenTableRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void saveRefreshToken(RefreshTokenTableEntity token) {

        String sql = """
                INSERT INTO refreshtokentable
                (jwttoken, expiresat, issuedat, userid)
                VALUES (?, ?, ?, ?)
                """;


        jdbcTemplate.update(
                sql,
                token.getJwttoken(),
                token.getExpiresat(),
                token.getIssuedat(),
                token.getUserid()
        );
    }


    public RefreshTokenTableEntity getByToken(String jwtToken) {

        String sql = """
                SELECT *
                FROM refreshtokentable
                WHERE jwttoken = ?
                """;

        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

                return new RefreshTokenTableEntity(
                        rs.getLong("tokenid"),
                        rs.getString("jwttoken"),
                        rs.getTimestamp("expiresat").toLocalDateTime(),
                        rs.getTimestamp("issuedat").toLocalDateTime(),
                        rs.getLong("userid")
                );

            }, jwtToken);
        }
        catch (Exception e) {

            return null;

        }

    }

    public RefreshTokenTableEntity getByUserId(long userId) {

        String sql = """
                SELECT *
                FROM refreshtokentable
                WHERE userid = ?
                """;

        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

                return new RefreshTokenTableEntity(
                        rs.getLong("tokenid"),
                        rs.getString("jwttoken"),
                        rs.getTimestamp("expiresat").toLocalDateTime(),
                        rs.getTimestamp("issuedat").toLocalDateTime(),
                        rs.getLong("userid")
                );

            }, userId);
        }
        catch (Exception e) {

            return null;

        }

    }

    public void deleteTokenByUserId(long userId) {

        String sql = """
                DELETE FROM refreshtokentable
                WHERE userid = ?
                """;


        jdbcTemplate.update(sql, userId);
    }

}