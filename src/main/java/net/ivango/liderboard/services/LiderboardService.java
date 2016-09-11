package net.ivango.liderboard.services;


import net.ivango.liderboard.storage.types.Player;
import org.joda.time.DateTime;

import java.util.List;

public interface LiderboardService {

    void setUrl(String newUrl);
    String getUrl();

    List<Player> getLiderboard();
    List<Player> getLiderboard(int from, int to);
    List<Player> getLiderboard(DateTime from, DateTime to);
    void banPlayers(List<Integer> playerIds);
    void unbanPlayers(List<Integer> playerIds);

}
