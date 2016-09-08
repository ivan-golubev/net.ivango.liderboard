package net.ivango.liderboard;

import net.ivango.liderboard.types.Player;

import java.util.Collections;
import java.util.List;

public class LiderBoardServiceImpl implements LiderboardService {

    private static final int DEFAULT_LIDERBOARD_LENGTH = 10;
    private LiderboardDAO liderboardDAO;

    private String liderboardServiceURL;
    @Override public void setURL(String newUrl) { this.liderboardServiceURL = newUrl; }
    @Override public String getURL() { return this.liderboardServiceURL; }

    @Override
    public List<Player> getLiderboard() { return liderboardDAO.getPlayers(0, DEFAULT_LIDERBOARD_LENGTH); }

    /**
     * @return bounded liderboard or empty list if wrong parameters are submitted.
     * */
    @Override
    public List<Player> getLiderboard(int from, int to) {
        if (from < 0 || to <=0 || to < from) { return Collections.emptyList(); }
        else return liderboardDAO.getPlayers(from, to);
    }

    @Override
    public List<Player> getLiderboard(long datetimeFrom, long dateTimeTo) {
        return null;
    }

    /**
     * Removes the specified players from the liderboards.
     * */
    @Override
    public void banPlayers(List<Integer> playerIds) { liderboardDAO.removePlayers(playerIds); }
}
