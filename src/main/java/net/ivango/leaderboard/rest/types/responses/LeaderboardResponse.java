package net.ivango.leaderboard.rest.types.responses;

import lombok.*;
import net.ivango.leaderboard.storage.types.Player;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardResponse extends Response {
    private List<Player> leaderboard;
}