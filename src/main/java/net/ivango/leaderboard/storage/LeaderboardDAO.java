package net.ivango.leaderboard.storage;


import net.ivango.leaderboard.storage.types.Player;
import org.joda.time.DateTime;

import java.util.List;

public interface LeaderboardDAO {
    void changeBanStatus(List<Integer> bannedList, boolean ban);
    List<Player> getPlayers(int from, int to, DateTime fromDateTime, DateTime toDateTime);
}
