package net.ivango.leaderboard.rest.types.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeBanStatusRequest {
    private List<Integer> userIds;
}
