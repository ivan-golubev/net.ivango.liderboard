package net.ivango.liderboard;


import net.ivango.liderboard.types.Player;

import java.util.List;

public interface LiderboardService {

    void setURL(String newUrl);
    String getURL();

    List<Player> getLiderboard();
    List<Player> getLiderboard(int from, int to);
    List<Player> getLiderboard(long datetimeFrom, long dateTimeTo);
    void banPlayers(List<Integer> playerIds);

}
