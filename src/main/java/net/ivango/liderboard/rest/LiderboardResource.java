package net.ivango.liderboard.rest;

import lombok.extern.java.Log;
import net.ivango.liderboard.rest.types.requests.ChangeBanStatusRequest;
import net.ivango.liderboard.rest.types.requests.LiderboardRangeRequest;
import net.ivango.liderboard.rest.types.requests.LiderboardTimedRequest;
import net.ivango.liderboard.rest.types.responses.LiderboardResponse;
import net.ivango.liderboard.rest.types.responses.Response;
import net.ivango.liderboard.services.LiderboardService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Log
@Path("leaderboard")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LiderboardResource {

    private LiderboardService liderboardService;

    @Inject
    public LiderboardResource(LiderboardService liderboardService) {
        this.liderboardService = liderboardService;
    }

    @GET
    public LiderboardResponse getLiderboard(){
        return new LiderboardResponse(liderboardService.getLiderboard());
    }

    @POST
    public LiderboardResponse getLiderboard(LiderboardRangeRequest request) {
        return new LiderboardResponse(
                liderboardService.getLiderboard(request.getFrom(), request.getTo())
        );
    }

    @Path("/timed")
    @POST
    public LiderboardResponse getLiderBoard(LiderboardTimedRequest request) {
        return new LiderboardResponse(
                liderboardService.getLiderboard(request.getFromTime(), request.getToTime())
        );
    }

    @Path("/ban")
    @POST
    public Response banPlayers(ChangeBanStatusRequest request) {
        liderboardService.banPlayers(request.getUserIds());
        return new Response();
    }

    @Path("/unban")
    @POST
    public Response unbanPlayers(ChangeBanStatusRequest request) {
        liderboardService.unbanPlayers(request.getUserIds());
        return new Response();
    }

}
