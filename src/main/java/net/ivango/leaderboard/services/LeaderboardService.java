package net.ivango.leaderboard.services;


import net.ivango.leaderboard.storage.types.Player;
import org.joda.time.DateTime;

import java.util.List;

public interface LeaderboardService {

    List<Player> getLeaderboard();
    List<Player> getLeaderboard(int from, int to);
    List<Player> getLeaderboard(DateTime from, DateTime to);
    void banPlayers(List<Integer> playerIds);
    void unbanPlayers(List<Integer> playerIds);

}
