package com.nea.candid.repositories;

import com.nea.candid.data.dbEnties.RefreshTokenTableEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class RefreshTokenTableRepo {

    private final JdbcTemplate jdbcTemplate;

    public RefreshTokenTableRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public String saveRefreshToken(String jwtToken, LocalDateTime expiresAt, LocalDateTime issuedAt, long userId, String uuid) {

        String sql = """
                INSERT INTO refreshtokentable
                (jwttoken, expiresat, issuedat, userid, tokenuuid)
                VALUES (?, ?, ?, ?, ?)
                returning tokenuuid
                """;


        return jdbcTemplate.queryForObject(
                sql,
                String.class,
                jwtToken,
                expiresAt,
                issuedAt,
                userId,
                uuid
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
                        rs.getLong("userid"),
                        rs.getString("tokenuuid")
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
                        rs.getLong("userid"),
                        rs.getString("tokenuuid")
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