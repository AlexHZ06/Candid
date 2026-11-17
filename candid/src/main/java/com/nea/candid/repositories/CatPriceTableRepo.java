package com.nea.candid.repositories;

import com.nea.candid.data.dbEnties.CatPriceTableEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CatPriceTableRepo {

    private final JdbcTemplate jdbcTemplate;

    public CatPriceTableRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public float getCatPrice(String category, long userid) {

        String sql = """
                select avprice
                from catpricetable
                where userid = ?
                and category = ?
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

            return rs.getFloat("avprice");

        }, userid, category);

    }

    public List<CatPriceTableEntity> getCatPrices(long userid) {

        String sql = """
                select *
                from catpricetable
                where userid = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            return new  CatPriceTableEntity(
                    rs.getLong("catpriceid"),
                    rs.getLong("userid"),
                    rs.getString("category"),
                    rs.getFloat("avprice")
            );

        }, userid);

    }

}
