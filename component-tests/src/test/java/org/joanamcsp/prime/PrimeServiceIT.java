package org.joanamcsp.prime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
public class PrimeServiceIT {

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void prime_service_should_return_list_of_primes() {
        given()
            .when()
                .get("/primes/10")
            .then().
                statusCode(200)
            .assertThat()
                .body(equalTo("[2,3,5,7]"));
    }

    @Test
    public void prime_service_should_return_list_with_one_prime() {
        given()
            .when()
                .get("/primes/2")
            .then().
                statusCode(200)
            .assertThat()
                .body(equalTo("[2]"));
    }

    @Test
    public void prime_service_should_not_accept_bound_under_2() {
        given()
            .when()
                .get("/primes/-1")
            .then().
                statusCode(400)
            .assertThat()
                .body("message", equalTo("INVALID_ARGUMENT: Bound cannot be less than 2"));
    }

    @Test
    public void prime_service_should_not_accept_missing_bound() {
        given()
            .when()
                .get("/primes/")
            .then().
                statusCode(404);
    }

    @Test
    public void prime_service_should_not_accept_invalid_bound_type() {
        given()
            .when()
                .get("/primes/notnumber")
            .then().
                statusCode(400);
    }
}
