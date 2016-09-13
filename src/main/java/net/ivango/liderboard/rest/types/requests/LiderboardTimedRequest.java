package net.ivango.liderboard.rest.types.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiderboardTimedRequest {
    private long fromTime, toTime;
}
