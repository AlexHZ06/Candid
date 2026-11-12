package com.nea.candid.repositories;

import com.nea.candid.data.dbEnties.PhotosTableEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class KeyWordsRepo {

    private final JdbcTemplate jdbcTemplate;

    public KeyWordsRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int[] addKeyWords(List<String> keyWords, long photoid){

        String sql = """
                insert into keywordstable(photoid, word)
                values (?, ?)
                """;

        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {

                ps.setLong(1, photoid);
                ps.setString(2, keyWords.get(i));

            }

            @Override
            public int getBatchSize() {
                return keyWords.size();
            }
        });

    }

}
