package net.ivango.liderboard.storage;


import net.ivango.liderboard.storage.types.Player;
import org.joda.time.DateTime;

import java.util.List;

public interface LiderboardDAO {
    void changeBanStatus(List<Integer> bannedList, boolean ban);
    List<Player> getPlayers(int from, int to, DateTime fromDateTime, DateTime toDateTime);
}
