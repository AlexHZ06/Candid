package com.nea.candid.repositories;

import com.nea.candid.data.dbEnties.TagsTableEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
public class TagsTableRepo {

    private final JdbcTemplate jdbcTemplate;

    public TagsTableRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int[] addTags(String[] tags) {

        String sql = """
                insert into tagstable(tagname)
                values(?)
                on conflict (tagname) do nothing;
                """;

        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement sql, int i) throws SQLException {

                sql.setString(1, tags[i]);

            }

            @Override
            public int getBatchSize() {

                return tags.length;

            }

        });

    }

    public List<Long> getTagIds(String[] tags) {

        ArrayList<String> placholders = new ArrayList<>();

        for(int i = 0; i <  tags.length; i++) {

            placholders.add("?");

        }

        String placeholder = String.join(",", placholders);
        String sql = "select tagid from tagstable where tagname in (" + placeholder +")";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            return rs.getLong("tagid");

        }, tags);

    }

}
