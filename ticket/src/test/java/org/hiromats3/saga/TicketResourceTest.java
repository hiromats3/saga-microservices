package org.hiromats3.saga;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class TicketResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/ticket")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}