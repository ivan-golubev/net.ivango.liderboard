package net.ivango.liderboard.rest;

import lombok.extern.java.Log;
import net.ivango.liderboard.rest.types.requests.ChangeBanStatusRequest;
import net.ivango.liderboard.rest.types.requests.LiderboardRangeRequest;
import net.ivango.liderboard.rest.types.requests.LiderboardTimedRequest;
import net.ivango.liderboard.rest.types.responses.LiderboardResponse;
import net.ivango.liderboard.rest.types.responses.Response;
import net.ivango.liderboard.services.LeaderboardService;
import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Log
@Path("leaderboard")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LeaderboardResource {

    private LeaderboardService liderboardService;

    @Inject
    public LeaderboardResource(LeaderboardService liderboardService) {
        this.liderboardService = liderboardService;
    }

    /**
     * returns a default liderboard
     * */
    @GET
    public LiderboardResponse getLiderboard(){
        return new LiderboardResponse(liderboardService.getLeaderboard());
    }

    /**
     * returns a ranged liderboard from place x to y
     * */
    @POST
    public LiderboardResponse getLiderboard(LiderboardRangeRequest request) {
        return new LiderboardResponse(
                liderboardService.getLeaderboard(request.getFrom(), request.getTo())
        );
    }

    /**
     * returns a timed liderboard from timestamp x to y
     * */
    @Path("/timed")
    @POST
    public LiderboardResponse getLiderBoard(LiderboardTimedRequest request) {
        return new LiderboardResponse(
                liderboardService.getLeaderboard(new DateTime(request.getFromTime()), new DateTime(request.getToTime()))
        );
    }

    /**
     * marks the specified players as banned
     * */
    @Path("/ban")
    @POST
    public Response banPlayers(ChangeBanStatusRequest request) {
        liderboardService.banPlayers(request.getUserIds());
        return new Response();
    }

    /**
     * marks the specified players as not banned
     * */
    @Path("/unban")
    @POST
    public Response unbanPlayers(ChangeBanStatusRequest request) {
        liderboardService.unbanPlayers(request.getUserIds());
        return new Response();
    }

}
