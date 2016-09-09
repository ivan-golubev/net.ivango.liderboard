package net.ivango.liderboard.rest;

import lombok.extern.java.Log;
import net.ivango.liderboard.rest.types.requests.LiderboardRangeRequest;
import net.ivango.liderboard.rest.types.requests.LiderboardTimedRequest;
import net.ivango.liderboard.rest.types.responses.LiderboardResponse;
import net.ivango.liderboard.rest.types.responses.Response;
import net.ivango.liderboard.services.LiderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Component
@Log
@Path("leaderboard")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LiderboardResource {

    @Autowired private LiderboardService liderboardService;

    @GET
    public Response getLiderboard(){
        return new LiderboardResponse(liderboardService.getLiderboard());
    }

    @POST
    public Response getLiderboard(LiderboardRangeRequest request) {
        return new LiderboardResponse(
                liderboardService.getLiderboard(request.getFrom(), request.getTo())
        );
    }

    @Path("/timed")
    @POST
    public Response getLiderBoard(LiderboardTimedRequest request) {
        return new LiderboardResponse(
                liderboardService.getLiderboard(request.getFromTime(), request.getToTime())
        );
    }

}
