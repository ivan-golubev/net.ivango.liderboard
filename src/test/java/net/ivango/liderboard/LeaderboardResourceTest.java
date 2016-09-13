package net.ivango.liderboard;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import com.owlike.genson.Genson;
import net.ivango.liderboard.rest.types.requests.ChangeBanStatusRequest;
import net.ivango.liderboard.rest.types.requests.LiderboardRangeRequest;
import net.ivango.liderboard.rest.types.requests.LiderboardTimedRequest;
import net.ivango.liderboard.rest.types.responses.LiderboardResponse;
import net.ivango.liderboard.storage.types.Player;
import org.eclipse.jetty.server.Server;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.util.Comparator;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LeaderboardResourceTest {

    private Server server;
    private Genson genson;

    private Logger logger = LoggerFactory.getLogger(LeaderboardResourceTest.class);

    private Comparator<Player> placeComparator = (o1, o2) ->
            o1.getPlace() > o2.getPlace() ? 1 :
                    o1.getPlace() < o2.getPlace() ? -1 : 0;

    @BeforeClass
    public void init() throws InterruptedException {
        try {
            genson = new Genson();
            server = new StartServer().startServerAsync();
            logger.info("server started.");
        } catch (Exception e) {
            logger.info("failed to start the server.");
        }
    }

    @AfterClass
    public void destroy() {
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
    private void getDefaultLiderboard(){
        logger.info("Checking that the default liderboard response is valid...");
        String responseJSON =
                given().
                        accept(MediaType.APPLICATION_JSON).
                        when().
                        get("/leaderboard").
                        then().
                        body("status", equalTo("OK")).
                        statusCode(200).extract().response().asString();

        /* result is valid json and is deserializable back to the LiderboardResponse */
        List<Player> liderboard = genson.deserialize(responseJSON, LiderboardResponse.class).getLiderboard();
        /* lider board is not empty */
        Assert.assertFalse(liderboard.isEmpty());
        /* lider board is ordered by score descending */
        Ordering.natural().isOrdered(liderboard);
        /* lider board is ordered by place ascending */
        Ordering.from(placeComparator).isOrdered(liderboard);
    }

    @Test
    private void getLiderboardRanged(){
        logger.info("Checking that the ranged liderboard response is valid...");
        int from = 1, to = 2;

        String responseJSON =
                given().
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON).
                        body( genson.serialize(new LiderboardRangeRequest(from, to)) ).
                        when().
                        post("/leaderboard").
                        then().
                        body("status", equalTo("OK")).
                        statusCode(200).extract().response().asString();

        /* result is valid json and is deserializable back to the LiderboardResponse */
        List<Player> liderboard = genson.deserialize(responseJSON, LiderboardResponse.class).getLiderboard();
        /* lider board is not empty */
        Assert.assertFalse(liderboard.isEmpty());
        /* lider board is ordered by score descending */
        Ordering.natural().isOrdered(liderboard);
        /* lider board is ordered by place ascending */
        Ordering.from(placeComparator).isOrdered(liderboard);
        /* result starts with the "from" inclusive */
        Assert.assertEquals(liderboard.get(0).getPlace(), from);
        /* result ends with the "end" exclusive */
        Assert.assertEquals(liderboard.get(liderboard.size()-1).getPlace(), to-1);
    }

    @Test
    private void getLiderboardTimed() {
        logger.info("Checking that the timed liderboard response is valid...");


        /* first of all get the lider board for the last hour */
        DateTime to = new DateTime(), from = new DateTime().minusHours(1);
        String responseJSON =
                given().
                        accept(MediaType.APPLICATION_JSON).
                        contentType(MediaType.APPLICATION_JSON).
                        body( genson.serialize(new LiderboardTimedRequest(from.getMillis(), to.getMillis()))).
                when().
                        post("/leaderboard/timed").
                then().
                        body("status", equalTo("OK")).
                        statusCode(200).extract().response().asString();

        /* result is valid json and is deserializable back to the LiderboardResponse */
        List<Player> liderboard = genson.deserialize(responseJSON, LiderboardResponse.class).getLiderboard();
        Assert.assertTrue( !liderboard.isEmpty() );
        /* lider board is ordered by score descending */
        Ordering.natural().isOrdered(liderboard);
        /* lider board is ordered by place ascending */
        Ordering.from(placeComparator).isOrdered(liderboard);

        /* try to get liderboard from the past - should be empty */

        DateTime fromPast = new DateTime().minusDays(10);
        DateTime toPast = new DateTime().minusDays(8);

        responseJSON =
                given().
                        accept(MediaType.APPLICATION_JSON).
                        contentType(MediaType.APPLICATION_JSON).
                        body( genson.serialize(new LiderboardTimedRequest(fromPast.getMillis(), toPast.getMillis()))).
                when().
                        post("/leaderboard/timed").
                then().
                        body("status", equalTo("OK")).
                        statusCode(200).extract().response().asString();

        List<Player> liderboardPast = genson.deserialize(responseJSON, LiderboardResponse.class).getLiderboard();
        Assert.assertTrue( liderboardPast.isEmpty() );
    }

    @Test
    private void testPlayerBanning() {
        logger.info("Testing the banning feature...");
        String responseJSON =
                given().
                        accept(MediaType.APPLICATION_JSON).
                        when().
                        get("/leaderboard").
                        then().
                        statusCode(200).extract().response().asString();

        /* result is valid json and is deserializable back to the LiderboardResponse */
        List<Player> liderboardOriginal = genson.deserialize(responseJSON, LiderboardResponse.class).getLiderboard();

        int bannedUserId = liderboardOriginal.get(0).getId();
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

        /* checking that user is not present in the liderboard */
        responseJSON =
                given().
                        accept(MediaType.APPLICATION_JSON).
                        when().
                        get("/leaderboard").
                        then().
                        statusCode(200).extract().response().asString();

        /* result is valid json and is deserializable back to the LiderboardResponse */
        List<Player> liderboardChanged = genson.deserialize(responseJSON, LiderboardResponse.class).getLiderboard();
        /* lider board is ordered by score descending */
        Ordering.natural().isOrdered(liderboardChanged);
        /* lider board is ordered by place ascending */
        Ordering.from(placeComparator).isOrdered(liderboardChanged);
        /* lider board does not contain the banned user */
        Assert.assertNotEquals(liderboardOriginal.size(), liderboardChanged.size());
        Assert.assertFalse( liderboardChanged.stream().anyMatch(p -> p.getId() == bannedUserId) );

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

        /* checking that user was restored in the liderboard */
        responseJSON =
                given().
                        accept(MediaType.APPLICATION_JSON).
                        when().
                        get("/leaderboard").
                        then().
                        statusCode(200).extract().response().asString();

        /* result is valid json and is deserializable back to the LiderboardResponse */
        liderboardChanged = genson.deserialize(responseJSON, LiderboardResponse.class).getLiderboard();
        /* lider board is ordered by score descending */
        Ordering.natural().isOrdered(liderboardChanged);
        /* lider board is ordered by place ascending */
        Ordering.from(placeComparator).isOrdered(liderboardChanged);
        Assert.assertEquals(liderboardOriginal.size(), liderboardChanged.size());
        Assert.assertTrue( liderboardChanged.stream().anyMatch(p -> p.getId() == bannedUserId) );
    }

}