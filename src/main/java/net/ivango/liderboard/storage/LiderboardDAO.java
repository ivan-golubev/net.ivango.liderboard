package net.ivango.liderboard.storage;


import net.ivango.liderboard.storage.types.Player;

import java.util.List;

public interface LiderboardDAO {
    void removePlayers(List<Integer> bannedList);
    List<Player> getPlayers(int from, int to);
}
