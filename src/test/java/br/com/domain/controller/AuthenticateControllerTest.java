package br.com.domain.controller;

import br.com.domain.domain.ErrorDetail;
import br.com.domain.domain.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticateControllerTest {

    private final String PATH_ENDPOINT_RESOURCE = "/spring-api-model/authenticate";
    private final String POST = "POST";
    private final String BAD_REQUEST = "Bad Request";
    private final String ERROR_VALIDATION = "Error validation";
    private final String FORBIDDEN = "Forbidden";
    private final String INVALID_USER = "Invalid User";

    @LocalServerPort
    private Integer port;

    @Test
    public void nullValuesTest() {

        User user = new User(null, null, null, null, null, null);

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(user)
                .when()
                    .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE)
                .then()
                    .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(HttpStatus.BAD_REQUEST.value()))
                    .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(BAD_REQUEST))
                    .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(POST))
                    .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(ERROR_VALIDATION))
                    .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE));
    }

    @Test
    public void lengthValuesTest() {

        User user = new User(null, "u", "p", null, null, null);

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(user)
                .when()
                    .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE)
                .then()
                    .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(HttpStatus.BAD_REQUEST.value()))
                    .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(BAD_REQUEST))
                    .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(POST))
                    .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(ERROR_VALIDATION))
                    .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE));
    }

    @Test
    public void invalidUsernameTest() {

        User user = new User(null, "123456", "123456", null, null, null);

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(user)
                .when()
                    .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE)
                .then()
                    .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(HttpStatus.FORBIDDEN.value()))
                    .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(FORBIDDEN))
                    .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(POST))
                    .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(INVALID_USER))
                    .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE));
    }

    @Test
    public void invalidPasswordTest() {

        User user = new User(null, "jonathan", "jonathan", null, null, null);

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(user)
                .when()
                    .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE)
                .then()
                    .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(HttpStatus.FORBIDDEN.value()))
                    .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(FORBIDDEN))
                    .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(POST))
                    .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(INVALID_USER))
                    .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE));
    }

    @Test
    public void validCredentialsTest() {

        User user = new User(null, "jonathan", "123456", null, null, null);

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(user)
                .when()
                    .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE)
                .then()
                    .body("id", Matchers.nullValue())
                    .body("username", Matchers.notNullValue())
                    .body("passwotd", Matchers.nullValue())
                    .body("userProfile", Matchers.nullValue())
                    .body("userSituation", Matchers.nullValue())
                    .body("token", Matchers.notNullValue());
    }
}