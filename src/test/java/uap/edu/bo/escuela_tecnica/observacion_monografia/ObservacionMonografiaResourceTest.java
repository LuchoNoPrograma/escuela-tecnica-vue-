package uap.edu.bo.escuela_tecnica.observacion_monografia;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class ObservacionMonografiaResourceTest extends BaseIT {

    @Test
    @Sql({"/data/monografiaData.sql", "/data/revisionMonografiaData.sql", "/data/observacionMonografiaData.sql"})
    void getAllObservacionMonografias_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/observacionMonografias")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).idObservacionMonografia", Matchers.equalTo(2600));
    }

    @Test
    @Sql({"/data/monografiaData.sql", "/data/revisionMonografiaData.sql", "/data/observacionMonografiaData.sql"})
    void getObservacionMonografia_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/observacionMonografias/2600")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("idUsuReg", Matchers.equalTo(36));
    }

    @Test
    void getObservacionMonografia_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/observacionMonografias/3266")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    @Sql({"/data/monografiaData.sql", "/data/revisionMonografiaData.sql"})
    void createObservacionMonografia_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/observacionMonografiaDTORequest.json"))
                .when()
                    .post("/api/observacionMonografias")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, observacionMonografiaRepository.count());
    }

    @Test
    void createObservacionMonografia_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/observacionMonografiaDTORequest_missingField.json"))
                .when()
                    .post("/api/observacionMonografias")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("idUsuReg"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql({"/data/monografiaData.sql", "/data/revisionMonografiaData.sql", "/data/observacionMonografiaData.sql"})
    void updateObservacionMonografia_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/observacionMonografiaDTORequest.json"))
                .when()
                    .put("/api/observacionMonografias/2600")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals(((long)21), observacionMonografiaRepository.findById(((long)2600)).orElseThrow().getIdUsuReg());
        assertEquals(2, observacionMonografiaRepository.count());
    }

    @Test
    @Sql({"/data/monografiaData.sql", "/data/revisionMonografiaData.sql", "/data/observacionMonografiaData.sql"})
    void deleteObservacionMonografia_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/observacionMonografias/2600")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, observacionMonografiaRepository.count());
    }

}
