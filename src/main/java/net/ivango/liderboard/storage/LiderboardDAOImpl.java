package net.ivango.liderboard.storage;

import lombok.extern.java.Log;
import net.ivango.liderboard.storage.types.Player;
import net.ivango.liderboard.storage.types.PlayerMapper;
import org.h2.jdbcx.JdbcDataSource;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.inject.Singleton;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static net.ivango.liderboard.storage.SQLRequests.SELECT_LIDERBOARD;
import static net.ivango.liderboard.storage.SQLRequests.UPDATE_BAN_STATUS;

@Log
@Singleton
public class LiderboardDAOImpl implements LiderboardDAO {

    private JdbcTemplate jdbcTemplate;
    private PlayerMapper playerMapper = new PlayerMapper();

    public LiderboardDAOImpl() {
        this.jdbcTemplate = new JdbcTemplate(getDataSource());
    }

    private DataSource getDataSource(){
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("sql/create-db.sql")
                .addScript("sql/insert-data.sql")
                .build();
    }

    public void changeBanStatus(List<Integer> bannedList, boolean ban){
        jdbcTemplate.batchUpdate(UPDATE_BAN_STATUS, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, ban ? 1 : 0);
                ps.setInt(2, bannedList.get(i));
            }

            @Override
            public int getBatchSize() {
                return bannedList.size();
            }
        });
    }

    public List<Player> getPlayers(int from, int to, DateTime fromDateTime, DateTime toDateTime){
        int limit = to-from, offset = from-1;

        PreparedStatementSetter pre = ps -> {
            ps.setTimestamp(1, new Timestamp(fromDateTime.getMillis()));
            ps.setTimestamp(2, new Timestamp(toDateTime.getMillis()));
            ps.setInt(3, limit);
            ps.setInt(4, offset);
        };
        return jdbcTemplate.query(
                SELECT_LIDERBOARD,
                pre,
                playerMapper
        );
    }

}
