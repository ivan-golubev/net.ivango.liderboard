package net.ivango.liderboard.rest.types.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiderboardRangeRequest {
    private int from, to;
}
