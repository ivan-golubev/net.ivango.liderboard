package net.ivango.liderboard.storage;

import lombok.extern.java.Log;
import net.ivango.liderboard.storage.types.Player;
import net.ivango.liderboard.storage.types.PlayerMapper;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.inject.Singleton;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static net.ivango.liderboard.storage.SQLRequests.SELECT_LIDERBOARD;

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

    public void removePlayers(List<Integer> bannedList){
//        players.removeAll(bannedList);
    }

    public List<Player> getPlayers(int from, int to){
        return jdbcTemplate.query(SELECT_LIDERBOARD, playerMapper);
    }

}
