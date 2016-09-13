package net.ivango.leaderboard.rest.types.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardRangeRequest {
    private int from, to;
}
