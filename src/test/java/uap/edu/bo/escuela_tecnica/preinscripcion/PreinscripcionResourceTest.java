package uap.edu.bo.escuela_tecnica.preinscripcion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class PreinscripcionResourceTest extends BaseIT {

    @Test
    @Sql("/data/preinscripcionData.sql")
    void getAllPreinscripcions_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/preinscripcions")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(2))
                    .body("content.get(0).idPreinscripcion", Matchers.equalTo(4100));
    }

    @Test
    @Sql("/data/preinscripcionData.sql")
    void getAllPreinscripcions_filtered() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/preinscripcions?filter=4101")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("content.get(0).idPreinscripcion", Matchers.equalTo(4101));
    }

    @Test
    @Sql("/data/preinscripcionData.sql")
    void getPreinscripcion_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/preinscripcions/4100")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("idUsuReg", Matchers.equalTo(36));
    }

    @Test
    void getPreinscripcion_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/preinscripcions/4766")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createPreinscripcion_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/preinscripcionDTORequest.json"))
                .when()
                    .post("/api/preinscripcions")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, preinscripcionRepository.count());
    }

    @Test
    void createPreinscripcion_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/preinscripcionDTORequest_missingField.json"))
                .when()
                    .post("/api/preinscripcions")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("idUsuReg"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql("/data/preinscripcionData.sql")
    void updatePreinscripcion_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/preinscripcionDTORequest.json"))
                .when()
                    .put("/api/preinscripcions/4100")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals(((long)21), preinscripcionRepository.findById(((long)4100)).orElseThrow().getIdUsuReg());
        assertEquals(2, preinscripcionRepository.count());
    }

    @Test
    @Sql("/data/preinscripcionData.sql")
    void deletePreinscripcion_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/preinscripcions/4100")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, preinscripcionRepository.count());
    }

}
