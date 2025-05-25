package uap.edu.bo.escuela_tecnica.cronograma_modulo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class CronogramaModuloResourceTest extends BaseIT {

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql", "/data/cronogramaModuloData.sql"})
    void getAllCronogramaModulos_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/cronogramaModulos")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).idCronogramaMod", Matchers.equalTo(1700));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql", "/data/cronogramaModuloData.sql"})
    void getCronogramaModulo_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/cronogramaModulos/1700")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("idUsuReg", Matchers.equalTo(36));
    }

    @Test
    void getCronogramaModulo_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/cronogramaModulos/2366")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql"})
    void createCronogramaModulo_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/cronogramaModuloDTORequest.json"))
                .when()
                    .post("/api/cronogramaModulos")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, cronogramaModuloRepository.count());
    }

    @Test
    void createCronogramaModulo_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/cronogramaModuloDTORequest_missingField.json"))
                .when()
                    .post("/api/cronogramaModulos")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("idUsuReg"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql", "/data/cronogramaModuloData.sql"})
    void updateCronogramaModulo_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/cronogramaModuloDTORequest.json"))
                .when()
                    .put("/api/cronogramaModulos/1700")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals(((long)21), cronogramaModuloRepository.findById(((long)1700)).orElseThrow().getIdUsuReg());
        assertEquals(2, cronogramaModuloRepository.count());
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql", "/data/cronogramaModuloData.sql"})
    void deleteCronogramaModulo_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/cronogramaModulos/1700")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, cronogramaModuloRepository.count());
    }

}
