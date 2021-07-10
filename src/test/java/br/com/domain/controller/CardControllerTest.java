package br.com.domain.controller;

import br.com.domain.domain.*;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CardControllerTest {

    private static final String PATH_ENDPOINT_RESOURCE_AUTHENTICATE = "/spring-api-model/authenticate";
    private static final String PATH_ENDPOINT_RESOURCE_CARD = "/spring-api-model/card";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";
    private static final String BAD_REQUEST = "Bad Request";
    private static final String CONFLICT = "Conflict";
    private static final String ERROR_VALIDATION = "Error validation";
    private static final String VIOLATED_CONSTRAINT = "Violated constraint";
    private static final String INVALID_CUSTOMER = "Invalid Customer";

    private String token = "";

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {

        User user = new User(null, "jonathan", "123456", null, null, null);

        token = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_AUTHENTICATE)
                .jsonPath()
                .get("token");

    }

    @Test
    void findAll() {

        Map<String, String> headers = new HashMap<String, String>(){
            {
                put("Accept","application/json");
                put("Authorization","Bearer " + token);
            }
        };

        given()
                .headers(headers)
                .when()
                .get("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void findById() {

        Map<String, String> headers = new HashMap<String, String>(){
            {
                put("Accept","application/json");
                put("Authorization","Bearer " + token);
            }
        };

        given()
                .headers(headers)
                .when()
                .get("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD + "/5")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void validSaveUpdateDeleteTest() {

        Map<String, String> headers = new HashMap<String, String>(){
            {
                put("Content-Type","application/json");
                put("Authorization","Bearer " + token);
            }
        };

        Card card = new Card(null, "1111222233334444", new CardSituation(1L), new Customer(2L));

        card.setId(Integer.toUnsignedLong(
                given()
                        .headers(headers)
                        .body(card)
                        .when()
                        .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD)
                        .jsonPath()
                        .get("id"))
        );

        given()
                .headers(headers)
                .body(card)
                .when()
                .put("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);

        given()
                .headers(headers)
                .when()
                .delete("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD + "/" + card.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void nullValuesSaveTest() {

        Map<String, String> headers = new HashMap<String, String>(){
            {
                put("Content-Type","application/json");
                put("Authorization","Bearer " + token);
            }
        };

        Card card = new Card(null, null, null, null);

        given()
                .headers(headers)
                .body(card)
                .when()
                .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(BAD_REQUEST))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(POST))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(ERROR_VALIDATION))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CARD));
    }

    @Test
    void lengthValuesSaveTest() {

        Map<String, String> headers = new HashMap<String, String>(){
            {
                put("Content-Type","application/json");
                put("Authorization","Bearer " + token);
            }
        };

        Card card = new Card(null, "1", new CardSituation(1L), new Customer(2L));

        given()
                .headers(headers)
                .body(card)
                .when()
                .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(BAD_REQUEST))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(POST))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(ERROR_VALIDATION))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CARD));
    }

    @Test
    void existCardSaveTest() {

        Map<String, String> headers = new HashMap<String, String>(){
            {
                put("Content-Type","application/json");
                put("Authorization","Bearer " + token);
            }
        };

        Card card = new Card(null, "1234567898765432", new CardSituation(1L), new Customer(2L));

        given()
                .headers(headers)
                .body(card)
                .when()
                .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.CONFLICT.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(CONFLICT))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(POST))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(VIOLATED_CONSTRAINT))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CARD));
    }

    @Test
    void invalidForeignKeySaveTest(){

        Map<String, String> headers = new HashMap<String, String>(){
            {
                put("Content-Type","application/json");
                put("Authorization","Bearer " + token);
            }
        };

        Card card = new Card(null, "1111222233334444", new CardSituation(3L), new Customer(1L));

        given()
                .headers(headers)
                .body(card)
                .when()
                .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.CONFLICT.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(CONFLICT))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(POST))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(VIOLATED_CONSTRAINT))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CARD));
    }

    @Test
    void invalidUpdateDeleteTest() {

        Map<String, String> headers = new HashMap<String, String>(){
            {
                put("Content-Type","application/json");
                put("Authorization","Bearer " + token);
            }
        };

        Card card = new Card(null, "1111222233334444", new CardSituation(1L), new Customer(2L));

        card.setId(Integer.toUnsignedLong(
                given()
                        .headers(headers)
                        .body(card)
                        .when()
                        .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD)
                        .jsonPath()
                        .get("id"))
        );

        card.setPan("1234567898765432");

        given()
                .headers(headers)
                .body(card)
                .when()
                .put("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.CONFLICT.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(CONFLICT))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(PUT))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(VIOLATED_CONSTRAINT))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CARD));

        card.setPan("1");

        given()
                .headers(headers)
                .body(card)
                .when()
                .put("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(BAD_REQUEST))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(PUT))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(ERROR_VALIDATION))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CARD));

        card.setPan(null);
        card.setCardSituation(null);
        card.setCustomer(null);

        given()
                .headers(headers)
                .body(card)
                .when()
                .put("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(BAD_REQUEST))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(PUT))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(ERROR_VALIDATION))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CARD));

        card.setPan("1111222233334444");
        card.setCardSituation(new CardSituation(3L));
        card.setCustomer(new Customer(1L));

        given()
                .headers(headers)
                .body(card)
                .when()
                .put("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.CONFLICT.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(CONFLICT))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(PUT))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(VIOLATED_CONSTRAINT))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CARD));

        given()
                .headers(headers)
                .when()
                .delete("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD + "/" + 1)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(BAD_REQUEST))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(DELETE))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(INVALID_CUSTOMER))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CARD + "/1"));

        given()
                .headers(headers)
                .when()
                .delete("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD + "/" + card.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);

        card.setId(1L);
        card.setPan("1111222233334444");
        card.setCardSituation(new CardSituation(1L));
        card.setCustomer(new Customer(2L));

        given()
                .headers(headers)
                .body(card)
                .when()
                .put("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(BAD_REQUEST))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(PUT))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(INVALID_CUSTOMER))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CARD));
    }
}