package net.ivango.liderboard.storage;

public interface SQLRequests {

    String SELECT_LIDERBOARD =
            "SELECT player_id, name, avatar, max(score) AS max_score FROM player " +
                    "INNER JOIN scores ON player.player_id=scores.player_id_fk " +
                    "WHERE banned=0 AND score_timestamp BETWEEN ? AND ? " +
                    "GROUP BY player_id " +
                    "ORDER BY max_score DESC " +
                    "LIMIT ? OFFSET ?";

    String UPDATE_BAN_STATUS = "UPDATE player SET banned=? WHERE id=?";

}
