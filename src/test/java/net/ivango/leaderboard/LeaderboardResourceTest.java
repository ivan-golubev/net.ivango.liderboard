package net.ivango.leaderboard;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import com.owlike.genson.Genson;
import net.ivango.leaderboard.rest.types.requests.ChangeBanStatusRequest;
import net.ivango.leaderboard.rest.types.requests.LeaderboardRangeRequest;
import net.ivango.leaderboard.rest.types.requests.LeaderboardTimedRequest;
import net.ivango.leaderboard.rest.types.responses.LeaderboardResponse;
import net.ivango.leaderboard.storage.types.Player;
import org.eclipse.jetty.server.Server;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.util.Comparator;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LeaderboardResourceTest {

    public static Server server;
    public static Genson genson;

    public static Logger logger = LoggerFactory.getLogger(LeaderboardResourceTest.class);

    public static Comparator<Player> placeComparator = (o1, o2) ->
            o1.getPlace() > o2.getPlace() ? 1 :
                    o1.getPlace() < o2.getPlace() ? -1 : 0;

    @BeforeClass
    public static void init() throws InterruptedException {
        try {
            genson = new Genson();
            server = new StartServer().startServerAsync();
            logger.info("server started.");
        } catch (Exception e) {
            logger.info("failed to start the server.");
        }
    }

    @AfterClass
    public static void destroy() {
        try {
            logger.info("stopping the server.");
            server.stop();
            server.join();
            server.destroy();
        } catch (Exception e) {
            logger.warn("Exception during the server stop");
        }
    }

    @Test
    public void getDefaultLeaderboard(){
        logger.info("Checking that the default leaderboard response is valid...");
        String responseJSON =
                given().
                        accept(MediaType.APPLICATION_JSON).
                        when().
                get("/leaderboard").
                        then().
                body("status", equalTo("OK")).
                        statusCode(200).extract().response().asString();

        /* result is valid json and is deserializable back to the LeaderboardResponse */
        List<Player> leaderboard = genson.deserialize(responseJSON, LeaderboardResponse.class).getLeaderboard();
        /* leader board is not empty */
        Assert.assertFalse(leaderboard.isEmpty());
        /* leader board is ordered by score descending */
        Ordering.natural().isOrdered(leaderboard);
        /* leader board is ordered by place ascending */
        Ordering.from(placeComparator).isOrdered(leaderboard);
    }

    @Test
    public void getLeaderboardRanged(){
        logger.info("Checking that the ranged leaderboard response is valid...");
        int from = 1, to = 2;

        String responseJSON =
                given().
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON).
                        body( genson.serialize(new LeaderboardRangeRequest(from, to)) ).
                when().
                        post("/leaderboard").
                then().
                        body("status", equalTo("OK")).
                        statusCode(200).extract().response().asString();

        /* result is valid json and is deserializable back to the LeaderboardResponse */
        List<Player> leaderboard = genson.deserialize(responseJSON, LeaderboardResponse.class).getLeaderboard();
        /* leader board is not empty */
        Assert.assertFalse(leaderboard.isEmpty());
        /* leader board is ordered by score descending */
        Ordering.natural().isOrdered(leaderboard);
        /* leader board is ordered by place ascending */
        Ordering.from(placeComparator).isOrdered(leaderboard);
        /* result starts with the "from" inclusive */
        Assert.assertEquals(leaderboard.get(0).getPlace(), from);
        /* result ends with the "end" exclusive */
        Assert.assertEquals(leaderboard.get(leaderboard.size()-1).getPlace(), to-1);
    }

    @Test
    public void getLeaderboardTimed() {
        logger.info("Checking that the timed leaderboard response is valid...");


        /* first of all get the leader board for the last hour */
        DateTime to = new DateTime(), from = new DateTime().minusHours(1);
        String responseJSON =
                given().
                        accept(MediaType.APPLICATION_JSON).
                        contentType(MediaType.APPLICATION_JSON).
                        body( genson.serialize(new LeaderboardTimedRequest(from.getMillis(), to.getMillis()))).
                when().
                        post("/leaderboard/timed").
                then().
                        body("status", equalTo("OK")).
                        statusCode(200).extract().response().asString();

        /* result is valid json and is deserializable back to the LeaderboardResponse */
        List<Player> leaderboard = genson.deserialize(responseJSON, LeaderboardResponse.class).getLeaderboard();
        Assert.assertTrue( !leaderboard.isEmpty() );
        /* leader board is ordered by score descending */
        Ordering.natural().isOrdered(leaderboard);
        /* leader board is ordered by place ascending */
        Ordering.from(placeComparator).isOrdered(leaderboard);

        /* try to get leaderboard from the past - should be empty */

        DateTime fromPast = new DateTime().minusDays(10);
        DateTime toPast = new DateTime().minusDays(8);

        responseJSON =
                given().
                        accept(MediaType.APPLICATION_JSON).
                        contentType(MediaType.APPLICATION_JSON).
                        body( genson.serialize(new LeaderboardTimedRequest(fromPast.getMillis(), toPast.getMillis()))).
                when().
                        post("/leaderboard/timed").
                then().
                        body("status", equalTo("OK")).
                        statusCode(200).extract().response().asString();

        List<Player> leaderboardPast = genson.deserialize(responseJSON, LeaderboardResponse.class).getLeaderboard();
        Assert.assertTrue( leaderboardPast.isEmpty() );
    }

    @Test
    public void testPlayerBanning() {
        logger.info("Testing the banning feature...");
        String responseJSON =
                given().
                        accept(MediaType.APPLICATION_JSON).
                        when().
                        get("/leaderboard").
                        then().
                        statusCode(200).extract().response().asString();

        /* result is valid json and is deserializable back to the LeaderboardResponse */
        List<Player> leaderboardOriginal = genson.deserialize(responseJSON, LeaderboardResponse.class).getLeaderboard();

        int bannedUserId = leaderboardOriginal.get(0).getId();
        /* banning a player */
        given().
                contentType(MediaType.APPLICATION_JSON).
                body( genson.serialize(
                        new ChangeBanStatusRequest(
                                ImmutableList.of(bannedUserId)
                        ))
                ).
        when().
                post("/leaderboard/ban").
        then().
                statusCode(200);

        /* checking that user is not present in the leaderboard */
        responseJSON =
                given().
                        accept(MediaType.APPLICATION_JSON).
                when().
                        get("/leaderboard").
                then().
                        statusCode(200).extract().response().asString();

        /* result is valid json and is deserializable back to the LeaderboardResponse */
        List<Player> leaderboardChanged = genson.deserialize(responseJSON, LeaderboardResponse.class).getLeaderboard();
        /* leader board is ordered by score descending */
        Ordering.natural().isOrdered(leaderboardChanged);
        /* leader board is ordered by place ascending */
        Ordering.from(placeComparator).isOrdered(leaderboardChanged);
        /* leader board does not contain the banned user */
        Assert.assertFalse(leaderboardOriginal.size() == leaderboardChanged.size());
        Assert.assertFalse( leaderboardChanged.stream().anyMatch(p -> p.getId() == bannedUserId) );

        /* unbanning the player */
        given().
                contentType(MediaType.APPLICATION_JSON).
                body( genson.serialize(
                        new ChangeBanStatusRequest(
                                ImmutableList.of(bannedUserId)
                        ))
                ).
        when().
                post("/leaderboard/unban").
        then().
                statusCode(200);

        /* checking that user was restored in the leaderboard */
        responseJSON =
                given().
                        accept(MediaType.APPLICATION_JSON).
                when().
                        get("/leaderboard").
                then().
                        statusCode(200).extract().response().asString();

        /* result is valid json and is deserializable back to the LeaderboardResponse */
        leaderboardChanged = genson.deserialize(responseJSON, LeaderboardResponse.class).getLeaderboard();
        /* leader board is ordered by score descending */
        Ordering.natural().isOrdered(leaderboardChanged);
        /* leader board is ordered by place ascending */
        Ordering.from(placeComparator).isOrdered(leaderboardChanged);
        Assert.assertEquals(leaderboardOriginal.size(), leaderboardChanged.size());
        Assert.assertTrue( leaderboardChanged.stream().anyMatch(p -> p.getId() == bannedUserId) );
    }

}