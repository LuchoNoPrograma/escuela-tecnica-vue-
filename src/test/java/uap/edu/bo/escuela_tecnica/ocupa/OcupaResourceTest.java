package uap.edu.bo.escuela_tecnica.ocupa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class OcupaResourceTest extends BaseIT {

    @Test
    @Sql("/data/ocupaData.sql")
    void getAllOcupas_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ocupas")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).estOcupa", Matchers.equalTo("pk2700"));
    }

    @Test
    @Sql("/data/ocupaData.sql")
    void getOcupa_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ocupas/pk2700")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("idUsuReg", Matchers.equalTo(36));
    }

    @Test
    void getOcupa_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ocupas/pk3366")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createOcupa_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ocupaDTORequest.json"))
                .when()
                    .post("/api/ocupas")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, ocupaRepository.count());
    }

    @Test
    void createOcupa_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ocupaDTORequest_missingField.json"))
                .when()
                    .post("/api/ocupas")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("idUsuReg"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql("/data/ocupaData.sql")
    void updateOcupa_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ocupaDTORequest.json"))
                .when()
                    .put("/api/ocupas/pk2700")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals(((long)21), ocupaRepository.findById("pk2700").orElseThrow().getIdUsuReg());
        assertEquals(2, ocupaRepository.count());
    }

    @Test
    @Sql("/data/ocupaData.sql")
    void deleteOcupa_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/ocupas/pk2700")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, ocupaRepository.count());
    }

}
