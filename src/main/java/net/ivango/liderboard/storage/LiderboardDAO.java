package net.ivango.liderboard.storage;

import lombok.extern.java.Log;
import net.ivango.liderboard.storage.types.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Log
public class LiderboardDAO {

    private List<Player> players = new ArrayList<>();

    public void removePlayers(List<Integer> bannedList){
//        players.removeAll(bannedList);
    }

    public List<Player> getPlayers(int from, int to){
        return null;
    }

}
