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
class CardSituationControllerTest {

    private static final String PATH_ENDPOINT_RESOURCE_AUTHENTICATE = "/spring-api-model/authenticate";
    private static final String PATH_ENDPOINT_RESOURCE_CARD_SITUATION = "/spring-api-model/card/situation";
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";
    private static final String BAD_REQUEST = "Bad Request";
    private static final String CONFLICT = "Conflict";
    private static final String ERROR_VALIDATION = "Error validation";
    private static final String VIOLATED_CONSTRAINT = "Violated constraint";
    private static final String INVALID_DATA = "Invalid Data";

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
    void findAll(){

        Map<String, String> headers = new HashMap<String, String>(){
            {
                put("Content-Type", "application/json");
                put("Authorization", "Bearer " + token);
            }
        };

        given()
                .headers(headers)
                .when()
                .get("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD_SITUATION)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void findById(){

        Map<String, String> headers = new HashMap<String, String>(){
            {
                put("Content-Type", "application/json");
                put("Authorization", "Bearer " + token);
            }
        };

        given()
                .headers(headers)
                .when()
                .get("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD_SITUATION + "/1")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void invalidfindByIdTest(){

        Map<String, String> headers = new HashMap<String, String>(){
            {
                put("Content-Type", "application/json");
                put("Authorization", "Bearer " + token);
            }
        };

        given()
                .headers(headers)
                .when()
                .get("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD_SITUATION + "/3")
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(BAD_REQUEST))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(GET))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(INVALID_DATA))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CARD_SITUATION + "/3"));
    }

    @Test
    void validSaveUpdateDeleteTest(){

        Map<String, String> headers = new HashMap<String, String>(){
            {
                put("Content-Type", "application/json");
                put("Authorization", "Bearer " + token);
            }
        };

        CardSituation cardSituation = new CardSituation(null, "TESTE");

        cardSituation.setId(Integer.toUnsignedLong(given()
                .headers(headers)
                .body(cardSituation)
                .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD_SITUATION)
                .jsonPath()
                .get("id"))
        );

        given()
                .headers(headers)
                .body(cardSituation)
                .put("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD_SITUATION)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);

        given()
                .headers(headers)
                .body(cardSituation)
                .delete("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD_SITUATION + "/" + cardSituation.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void nullValuesSaveTest(){

        Map<String, String> headers = new HashMap<String, String>(){
            {
                put("Content-Type", "application/json");
                put("Authorization", "Bearer " + token);
            }
        };

        CardSituation cardSituation = new CardSituation(null, null);

        given()
                .headers(headers)
                .body(cardSituation)
                .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD_SITUATION)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(BAD_REQUEST))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(POST))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(ERROR_VALIDATION))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CARD_SITUATION));
    }

    @Test
    void lengthValuesSaveTest(){

        Map<String, String> headers = new HashMap<String, String>(){
            {
                put("Content-Type", "application/json");
                put("Authorization", "Bearer " + token);
            }
        };

        CardSituation cardSituation = new CardSituation(null, "s");

        given()
                .headers(headers)
                .body(cardSituation)
                .when()
                .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD_SITUATION)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(BAD_REQUEST))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(POST))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(ERROR_VALIDATION))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CARD_SITUATION));
    }

    @Test
    void existCardSituationSaveTest(){

        Map<String, String> headers = new HashMap<String, String>(){
            {
                put("Content-Type", "application/json");
                put("Authorization", "Bearer " + token);
            }
        };

        CardSituation cardSituation = new CardSituation(null, "ATIVO");

        given()
                .headers(headers)
                .body(cardSituation)
                .when()
                .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD_SITUATION)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.CONFLICT.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(CONFLICT))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(POST))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(VIOLATED_CONSTRAINT))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CARD_SITUATION));
    }

    @Test
    void invalidUpdateDeleteTest(){

        Map<String, String> headers = new HashMap<String, String>(){
            {
                put("Content-Type", "application/json");
                put("Authorization", "Bearer " + token);
            }
        };

        CardSituation cardSituation = new CardSituation(null, "TESTE");

        cardSituation.setId(Integer.toUnsignedLong(
                given()
                        .headers(headers)
                        .body(cardSituation)
                        .when()
                        .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD_SITUATION)
                        .jsonPath()
                        .get("id"))
        );

        cardSituation.setSituation(null);

        given()
                .headers(headers)
                .body(cardSituation)
                .when()
                .put("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD_SITUATION)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(BAD_REQUEST))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(PUT))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(ERROR_VALIDATION))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CARD_SITUATION));

        cardSituation.setSituation("s");

        given()
                .headers(headers)
                .body(cardSituation)
                .when()
                .put("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD_SITUATION)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(BAD_REQUEST))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(PUT))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(ERROR_VALIDATION))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CARD_SITUATION));

        cardSituation.setSituation("ATIVO");

        given()
                .headers(headers)
                .body(cardSituation)
                .when()
                .put("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD_SITUATION)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.CONFLICT.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(CONFLICT))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(PUT))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(VIOLATED_CONSTRAINT))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CARD_SITUATION));

        cardSituation.setSituation("TESTE");

        given()
                .headers(headers)
                .when()
                .delete("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD_SITUATION + "/3")
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(BAD_REQUEST))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(DELETE))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(INVALID_DATA))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CARD_SITUATION + "/3"));

        given()
                .headers(headers)
                .when()
                .delete("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CARD_SITUATION + "/" + cardSituation.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }
}