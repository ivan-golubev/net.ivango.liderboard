package net.ivango.liderboard.rest.types.requests;

import lombok.Data;

import java.util.List;

@Data
public class ChangeBanStatusRequest {
    private List<Integer> userIds;
}
