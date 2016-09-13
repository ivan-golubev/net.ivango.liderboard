package net.ivango.leaderboard.storage;

import lombok.extern.java.Log;
import net.ivango.leaderboard.storage.types.Player;
import net.ivango.leaderboard.storage.types.PlayerMapper;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.inject.Singleton;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static net.ivango.leaderboard.storage.SQLRequests.SELECT_LEADERBOARD;
import static net.ivango.leaderboard.storage.SQLRequests.UPDATE_BAN_STATUS;

@Log
@Singleton
public class LeaderboardDAOImpl implements LeaderboardDAO {

    private JdbcTemplate jdbcTemplate;
    private PlayerMapper playerMapper = new PlayerMapper();

    public LeaderboardDAOImpl() {
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
                SELECT_LEADERBOARD,
                pre,
                playerMapper
        );
    }

}
