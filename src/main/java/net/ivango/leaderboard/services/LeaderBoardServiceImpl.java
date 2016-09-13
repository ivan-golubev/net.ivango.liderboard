package net.ivango.leaderboard.services;

import lombok.extern.java.Log;
import net.ivango.leaderboard.storage.LeaderboardDAOImpl;
import net.ivango.leaderboard.storage.types.Player;
import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;

@Log
@Singleton
public class LeaderBoardServiceImpl implements LeaderboardService {

    private LeaderboardDAOImpl leaderboardDAO;

    private static final int DEFAULT_LEADERBOARD_LENGTH = 50;

    @Inject
    public LeaderBoardServiceImpl(LeaderboardDAOImpl leaderboardDAO) {
        this.leaderboardDAO = leaderboardDAO;
    }

    @Override
    public List<Player> getLeaderboard() {
        /* just get 50 leaders for the last day */
        DateTime end = new DateTime(), start = end.minusDays(1);
        return leaderboardDAO.getPlayers(1, DEFAULT_LEADERBOARD_LENGTH, start, end);
    }

    /**
     * @return bounded leaderboard or empty list if wrong parameters are submitted.
     * */
    @Override
    public List<Player> getLeaderboard(int from, int to) {
        if (from < 0 || to <=0 || to < from) { return Collections.emptyList(); }
                /* just get 50 leaders for the last day */
        DateTime end = new DateTime(), start = end.minusDays(1);
        return leaderboardDAO.getPlayers(from, to, start, end);
    }

    @Override
    public List<Player> getLeaderboard(DateTime from, DateTime to) {
        return leaderboardDAO.getPlayers(1, DEFAULT_LEADERBOARD_LENGTH, from, to);
    }

    /**
     * Removes the specified players from the leaderboards.
     * */
    @Override
    public void banPlayers(List<Integer> playerIds) { leaderboardDAO.changeBanStatus(playerIds, true); }

    /**
     * Adds the previously banned players back to the leaderboards.
     * */
    @Override
    public void unbanPlayers(List<Integer> playerIds) { leaderboardDAO.changeBanStatus(playerIds, false); }
}
