package uap.edu.bo.escuela_tecnica.revision_monografia;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class RevisionMonografiaResourceTest extends BaseIT {

    @Test
    @Sql({"/data/monografiaData.sql", "/data/revisionMonografiaData.sql"})
    void getAllRevisionMonografias_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/revisionMonografias")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).idRevisionMonografia", Matchers.equalTo(3400));
    }

    @Test
    @Sql({"/data/monografiaData.sql", "/data/revisionMonografiaData.sql"})
    void getRevisionMonografia_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/revisionMonografias/3400")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("idUsuReg", Matchers.equalTo(36));
    }

    @Test
    void getRevisionMonografia_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/revisionMonografias/4066")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    @Sql("/data/monografiaData.sql")
    void createRevisionMonografia_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/revisionMonografiaDTORequest.json"))
                .when()
                    .post("/api/revisionMonografias")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, revisionMonografiaRepository.count());
    }

    @Test
    void createRevisionMonografia_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/revisionMonografiaDTORequest_missingField.json"))
                .when()
                    .post("/api/revisionMonografias")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("idUsuReg"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql({"/data/monografiaData.sql", "/data/revisionMonografiaData.sql"})
    void updateRevisionMonografia_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/revisionMonografiaDTORequest.json"))
                .when()
                    .put("/api/revisionMonografias/3400")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals(((long)21), revisionMonografiaRepository.findById(((long)3400)).orElseThrow().getIdUsuReg());
        assertEquals(2, revisionMonografiaRepository.count());
    }

    @Test
    @Sql({"/data/monografiaData.sql", "/data/revisionMonografiaData.sql"})
    void deleteRevisionMonografia_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/revisionMonografias/3400")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, revisionMonografiaRepository.count());
    }

}
