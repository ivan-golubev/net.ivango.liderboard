package net.ivango.liderboard.rest;


import net.ivango.liderboard.rest.types.responses.ErrorResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UncaughtExceptionMapper  extends Throwable implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        return Response.status(500).entity(
                new ErrorResponse(500, "Generic server error")
        ).type("application/json").build();
    }
}
