package net.ivango.leaderboard.rest;

import lombok.extern.java.Log;
import net.ivango.leaderboard.rest.types.requests.ChangeBanStatusRequest;
import net.ivango.leaderboard.rest.types.requests.LeaderboardRangeRequest;
import net.ivango.leaderboard.rest.types.requests.LeaderboardTimedRequest;
import net.ivango.leaderboard.rest.types.responses.LeaderboardResponse;
import net.ivango.leaderboard.rest.types.responses.Response;
import net.ivango.leaderboard.services.LeaderboardService;
import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Log
@Path("leaderboard")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LeaderboardResource {

    private LeaderboardService leaderboardService;

    @Inject
    public LeaderboardResource(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    /**
     * returns a default leaderboard
     * */
    @GET
    public LeaderboardResponse getLeaderboard(){
        return new LeaderboardResponse(leaderboardService.getLeaderboard());
    }

    /**
     * returns a ranged leaderboard from place x to y
     * */
    @POST
    public LeaderboardResponse getLeaderboard(LeaderboardRangeRequest request) {
        return new LeaderboardResponse(
                leaderboardService.getLeaderboard(request.getFrom(), request.getTo())
        );
    }

    /**
     * returns a timed leaderboard from timestamp x to y
     * */
    @Path("/timed")
    @POST
    public LeaderboardResponse getLeaderBoard(LeaderboardTimedRequest request) {
        return new LeaderboardResponse(
                leaderboardService.getLeaderboard(new DateTime(request.getFromTime()), new DateTime(request.getToTime()))
        );
    }

    /**
     * marks the specified players as banned
     * */
    @Path("/ban")
    @POST
    public Response banPlayers(ChangeBanStatusRequest request) {
        leaderboardService.banPlayers(request.getUserIds());
        return new Response();
    }

    /**
     * marks the specified players as not banned
     * */
    @Path("/unban")
    @POST
    public Response unbanPlayers(ChangeBanStatusRequest request) {
        leaderboardService.unbanPlayers(request.getUserIds());
        return new Response();
    }

}
