package net.ivango.liderboard.storage;

import lombok.extern.java.Log;
import net.ivango.liderboard.storage.types.Player;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Log
@Singleton
public class LiderboardDAOImpl implements LiderboardDAO {

    private List<Player> players = new ArrayList<>();

    public void removePlayers(List<Integer> bannedList){
//        players.removeAll(bannedList);
    }

    public List<Player> getPlayers(int from, int to){
        return null;
    }

}
