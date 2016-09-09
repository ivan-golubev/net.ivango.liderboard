package net.ivango.liderboard.rest.types.requests;

import lombok.Data;
import org.joda.time.DateTime;

@Data
public class LiderboardTimedRequest {
    private DateTime fromTime, toTime;
}
