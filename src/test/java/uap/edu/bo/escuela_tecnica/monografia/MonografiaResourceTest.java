package uap.edu.bo.escuela_tecnica.monografia;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class MonografiaResourceTest extends BaseIT {

    @Test
    @Sql("/data/monografiaData.sql")
    void getAllMonografias_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/monografias")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).idMonografia", Matchers.equalTo(2400));
    }

    @Test
    @Sql("/data/monografiaData.sql")
    void getMonografia_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/monografias/2400")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("idUsuReg", Matchers.equalTo(36));
    }

    @Test
    void getMonografia_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/monografias/3066")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createMonografia_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/monografiaDTORequest.json"))
                .when()
                    .post("/api/monografias")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, monografiaRepository.count());
    }

    @Test
    void createMonografia_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/monografiaDTORequest_missingField.json"))
                .when()
                    .post("/api/monografias")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("idUsuReg"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql("/data/monografiaData.sql")
    void updateMonografia_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/monografiaDTORequest.json"))
                .when()
                    .put("/api/monografias/2400")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals(((long)21), monografiaRepository.findById(((long)2400)).orElseThrow().getIdUsuReg());
        assertEquals(2, monografiaRepository.count());
    }

    @Test
    @Sql("/data/monografiaData.sql")
    void deleteMonografia_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/monografias/2400")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, monografiaRepository.count());
    }

}
