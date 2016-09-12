package net.ivango.liderboard;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class LiderboardResourceTest {

    private Server server;

    private Logger logger = LoggerFactory.getLogger(LiderboardResourceTest.class);

    @BeforeClass
    public void init() throws InterruptedException {
        try {
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
        given().
                accept(MediaType.APPLICATION_JSON).
        when().
                get("/leaderboard").
        then().
                body("status", equalTo("OK")).
                body("liderboard[0].id", equalTo(5)).
                body("liderboard[0].name", equalTo("Elisa")).
                body("liderboard[0].place", equalTo(1)).
                body("liderboard[0].score", equalTo(50)).
                body("liderboard[1].place", equalTo(2)).
                body("liderboard[1].score", lessThan(50)).
                body("liderboard[2].place", equalTo(3)).
                body("liderboard[2].score", lessThan(50)).
                statusCode(200);
    }

    @Test
    private void getLiderboardRanged(){
//        logger.info("Checking that the ranged liderboard response is valid...");
//        given().
//                contentType(MediaType.APPLICATION_JSON).
//                accept(MediaType.APPLICATION_JSON).
//        when().
//                post("/leaderboard").
    }

}
