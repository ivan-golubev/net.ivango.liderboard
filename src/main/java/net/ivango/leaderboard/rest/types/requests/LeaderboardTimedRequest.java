package net.ivango.leaderboard.rest.types.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaderboardTimedRequest {
    private long fromTime, toTime;
}
