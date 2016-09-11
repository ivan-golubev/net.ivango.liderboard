package net.ivango.liderboard.storage.types;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerMapper implements RowMapper<Player> {
    @Override
    public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Player(
                rs.getInt("player_id"),
                rowNum+1,
                rs.getInt("max_score"),
                rs.getString("name"),
                rs.getString("avatar")
        );
    }
}
