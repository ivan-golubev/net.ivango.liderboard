package net.ivango.liderboard.rest.types.responses;

import lombok.Data;

@Data
public class ErrorResponse extends Response {
    private int code;
    private String message;

    public ErrorResponse(int code, String message){
        this.status = "ERROR";
        this.code = code;
        this.message = message;
    }
}
