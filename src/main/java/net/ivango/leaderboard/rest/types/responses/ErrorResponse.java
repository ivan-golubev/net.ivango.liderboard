package net.ivango.leaderboard.rest.types.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse extends Response {
    private int code;
    private String message;

    public ErrorResponse(int code, String message){
        this.status = "ERROR";
        this.code = code;
        this.message = message;
    }
}
