package com.nea.candid.repositories;

import com.nea.candid.data.dbEnties.EmbeddedVectorTableEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class EmbeddedVectorsTableRepo {

    private final JdbcTemplate jdbcTemplate;

    public EmbeddedVectorsTableRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int[] insertVector(ArrayList<EmbeddedVectorTableEntity> vectors) {

        String sql = """
                    insert into embeddedvectorstable(photoid, sectionnumber, brightnessmean, brightnessdeviation, dynamicrange, saturationmean, saturationdeviation, red, orange, yellow, green, cyan, blue, purple, magenta, aspectratio, edgedensity)
                    values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                """;

        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {


            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {

                ps.setLong(1, vectors.get(i).getPhotoid());
                ps.setInt(2, vectors.get(i).getSectionnumber());
                ps.setFloat(3, vectors.get(i).getBrightnessmean());
                ps.setFloat(4, vectors.get(i).getBrightnessdeviation());
                ps.setFloat(5, vectors.get(i).getDynamicrange());
                ps.setFloat(6, vectors.get(i).getSaturationmean());
                ps.setFloat(7, vectors.get(i).getSaturationdeviation());
                ps.setFloat(8, vectors.get(i).getRed());
                ps.setFloat(9, vectors.get(i).getOrange());
                ps.setDouble(10, vectors.get(i).getYellow());
                ps.setDouble(11, vectors.get(i).getGreen());
                ps.setDouble(12, vectors.get(i).getCyan());
                ps.setDouble(13, vectors.get(i).getBlue());
                ps.setDouble(14, vectors.get(i).getPurple());
                ps.setDouble(15, vectors.get(i).getMagenta());
                ps.setFloat(16, vectors.get(i).getAspectratio());
                ps.setFloat(17, vectors.get(i).getEdgedensity());

            }

            @Override
            public int getBatchSize() {
                return vectors.size();
            }
        });

    }

}
