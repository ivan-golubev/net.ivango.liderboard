package net.ivango.leaderboard.storage.types;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Player implements Comparable<Player> {
    private int id;
    private int place, score;
    private @NonNull String name, avatarURL;


    @Override
    public int compareTo(Player o) {
        return score > o.score ? -1
                : score < o.score ? 1
                :0;
    }
}