package net.ivango.liderboard.services;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import net.ivango.liderboard.storage.LiderboardDAOImpl;
import net.ivango.liderboard.storage.types.Player;
import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;

@Log
@Singleton
public class LiderBoardServiceImpl implements LiderboardService {

    private LiderboardDAOImpl liderboardDAO;
    @Getter @Setter private String url;

    private static final int DEFAULT_LIDERBOARD_LENGTH = 50;

    @Inject
    public LiderBoardServiceImpl(LiderboardDAOImpl liderboardDAO) {
        this.liderboardDAO = liderboardDAO;
    }

    @Override
    public List<Player> getLiderboard() {
        /* just get 50 liders for the last day */
        DateTime end = new DateTime(), start = end.minusDays(1);
        return liderboardDAO.getPlayers(1, DEFAULT_LIDERBOARD_LENGTH, start, end);
    }

    /**
     * @return bounded liderboard or empty list if wrong parameters are submitted.
     * */
    @Override
    public List<Player> getLiderboard(int from, int to) {
        if (from < 0 || to <=0 || to < from) { return Collections.emptyList(); }
                /* just get 50 liders for the last day */
        DateTime end = new DateTime(), start = end.minusDays(1);
        return liderboardDAO.getPlayers(from, to, start, end);
    }

    @Override
    public List<Player> getLiderboard(DateTime from, DateTime to) {
        return liderboardDAO.getPlayers(1, DEFAULT_LIDERBOARD_LENGTH, from, to);
    }

    /**
     * Removes the specified players from the liderboards.
     * */
    @Override
    public void banPlayers(List<Integer> playerIds) { liderboardDAO.changeBanStatus(playerIds, true); }

    /**
     * Adds the previously banned players back to the liderboards.
     * */
    @Override
    public void unbanPlayers(List<Integer> playerIds) { liderboardDAO.changeBanStatus(playerIds, false); }
}
