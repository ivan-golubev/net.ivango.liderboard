package net.ivango.leaderboard.rest;


import lombok.extern.java.Log;
import net.ivango.leaderboard.rest.types.responses.ErrorResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;

@Provider
@Log
public class UncaughtExceptionMapper  extends Throwable implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        log.log(Level.WARNING, "Uncaught exception:", exception);
        return Response.status(500).entity(
                new ErrorResponse(500, "Generic server error")
        ).type(MediaType.APPLICATION_JSON).build();
    }
}
