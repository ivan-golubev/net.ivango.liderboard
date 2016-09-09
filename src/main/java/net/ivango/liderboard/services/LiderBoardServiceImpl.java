package net.ivango.liderboard.services;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import net.ivango.liderboard.storage.LiderboardDAO;
import net.ivango.liderboard.storage.types.Player;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Log
public class LiderBoardServiceImpl implements LiderboardService {

    @Autowired private LiderboardDAO liderboardDAO;
    @Getter @Setter private String url;

    private static final int DEFAULT_LIDERBOARD_LENGTH = 50;

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
    public List<Player> getLiderboard(DateTime from, DateTime to) {
        return null;
    }

    /**
     * Removes the specified players from the liderboards.
     * */
    @Override
    public void banPlayers(List<Integer> playerIds) { liderboardDAO.removePlayers(playerIds); }
}
