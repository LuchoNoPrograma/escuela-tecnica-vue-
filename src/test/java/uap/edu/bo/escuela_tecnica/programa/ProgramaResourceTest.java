package uap.edu.bo.escuela_tecnica.programa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class ProgramaResourceTest extends BaseIT {

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql"})
    void getAllProgramas_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/programas")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).idPrograma", Matchers.equalTo(3200));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql"})
    void getPrograma_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/programas/3200")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("idUsuReg", Matchers.equalTo(36));
    }

    @Test
    void getPrograma_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/programas/3866")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql"})
    void createPrograma_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/programaDTORequest.json"))
                .when()
                    .post("/api/programas")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, programaRepository.count());
    }

    @Test
    void createPrograma_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/programaDTORequest_missingField.json"))
                .when()
                    .post("/api/programas")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("idUsuReg"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql"})
    void updatePrograma_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/programaDTORequest.json"))
                .when()
                    .put("/api/programas/3200")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals(((long)21), programaRepository.findById(((long)3200)).orElseThrow().getIdUsuReg());
        assertEquals(2, programaRepository.count());
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql"})
    void deletePrograma_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/programas/3200")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, programaRepository.count());
    }

}
