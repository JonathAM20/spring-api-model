package br.com.domain.controller;

import br.com.domain.domain.Customer;
import br.com.domain.domain.ErrorDetail;
import br.com.domain.domain.User;
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
class CustomerControllerTest {

    private static final String PATH_ENDPOINT_RESOURCE_AUTHENTICATE = "/spring-api-model/authenticate";
    private static final String PATH_ENDPOINT_RESOURCE_CUSTOMER = "/spring-api-model/customer";
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
                .get("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CUSTOMER)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void get() {

        Map<String, String> headers = new HashMap<String, String>(){
            {
                put("Accept","application/json");
                put("Authorization","Bearer " + token);
            }
        };

        given()
                .headers(headers)
                .when()
                .get("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CUSTOMER + "/2")
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

        Customer customer = new Customer(null, "19497768249", "Cleusimar", "Sousa");

        customer.setId(Integer.toUnsignedLong(
                given()
                        .headers(headers)
                        .body(customer)
                        .when()
                        .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CUSTOMER)
                        .jsonPath()
                        .get("id"))
        );

        given()
                .headers(headers)
                .body(customer)
                .when()
                .put("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CUSTOMER)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);

        given()
                .headers(headers)
                .when()
                .delete("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CUSTOMER + "/" + customer.getId())
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

        Customer customer = new Customer(null, null, null, null);

        given()
                .headers(headers)
                .body(customer)
                .when()
                .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CUSTOMER)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(BAD_REQUEST))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(POST))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(ERROR_VALIDATION))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CUSTOMER));
    }

    @Test
    void lengthValuesSaveTest() {

        Map<String, String> headers = new HashMap<String, String>(){
            {
                put("Content-Type","application/json");
                put("Authorization","Bearer " + token);
            }
        };

        Customer customer = new Customer(null, "p", "f", "l");

        given()
                .headers(headers)
                .body(customer)
                .when()
                .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CUSTOMER)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(BAD_REQUEST))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(POST))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(ERROR_VALIDATION))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CUSTOMER));
    }

    @Test
    void existCustomerSaveTest() {

        Map<String, String> headers = new HashMap<String, String>(){
            {
                put("Content-Type","application/json");
                put("Authorization","Bearer " + token);
            }
        };

        Customer customer = new Customer(null, "99501651216", "Jonathan", "Sousa");

        given()
                .headers(headers)
                .body(customer)
                .when()
                .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CUSTOMER)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.CONFLICT.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(CONFLICT))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(POST))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(VIOLATED_CONSTRAINT))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CUSTOMER));
    }

    @Test
    void invalidUpdateDeleteTest() {

        Map<String, String> headers = new HashMap<String, String>(){
            {
                put("Content-Type","application/json");
                put("Authorization","Bearer " + token);
            }
        };

        Customer customer = new Customer(null, "19497768249", "Cleusimar", "Sousa");

        customer.setId(Integer.toUnsignedLong(
                given()
                        .headers(headers)
                        .body(customer)
                        .when()
                        .post("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CUSTOMER)
                        .jsonPath()
                        .get("id"))
        );

        customer.setPersonalCode("99501651215");

        given()
                .headers(headers)
                .body(customer)
                .when()
                .put("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CUSTOMER)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.CONFLICT.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(CONFLICT))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(PUT))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(VIOLATED_CONSTRAINT))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CUSTOMER));

        customer.setPersonalCode("p");
        customer.setFirstName("f");
        customer.setLastName("l");

        given()
                .headers(headers)
                .body(customer)
                .when()
                .put("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CUSTOMER)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(BAD_REQUEST))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(PUT))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(ERROR_VALIDATION))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CUSTOMER));

        customer.setPersonalCode(null);
        customer.setFirstName(null);
        customer.setLastName(null);

        given()
                .headers(headers)
                .body(customer)
                .when()
                .put("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CUSTOMER)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(BAD_REQUEST))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(PUT))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(ERROR_VALIDATION))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CUSTOMER));

        given()
                .headers(headers)
                .when()
                .delete("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CUSTOMER + "/" + 1)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(BAD_REQUEST))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(DELETE))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(INVALID_CUSTOMER))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CUSTOMER + "/1"));

        given()
                .headers(headers)
                .when()
                .delete("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CUSTOMER + "/" + customer.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);

        customer.setId(1L);
        customer.setPersonalCode("19497768249");
        customer.setFirstName("CLeusimar");
        customer.setLastName("Sousa");

        given()
                .headers(headers)
                .body(customer)
                .when()
                .put("http://localhost:" + port + PATH_ENDPOINT_RESOURCE_CUSTOMER)
                .then()
                .body(ErrorDetail.STATUS_CODE, Matchers.comparesEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST.value()))
                .body(ErrorDetail.STATUS_MESSAGE, Matchers.comparesEqualTo(BAD_REQUEST))
                .body(ErrorDetail.HTTP_METHOD, Matchers.comparesEqualTo(PUT))
                .body(ErrorDetail.DETAIL, Matchers.comparesEqualTo(INVALID_CUSTOMER))
                .body(ErrorDetail.PATH, Matchers.comparesEqualTo(PATH_ENDPOINT_RESOURCE_CUSTOMER));
    }
}