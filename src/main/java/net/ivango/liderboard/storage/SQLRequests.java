package net.ivango.liderboard.storage;

public class SQLRequests {

    public static final String SELECT_LIDERBOARD =
            "SELECT player_id, name, avatar, max(score) AS max_score FROM player " +
                    "INNER JOIN scores ON player.player_id=scores.player_id_fk " +
                    "GROUP BY player_id " +
                    "ORDER BY max_score DESC " +
                    "LIMIT 5;";

}
