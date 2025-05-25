package uap.edu.bo.escuela_tecnica.programacion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class ProgramacionResourceTest extends BaseIT {

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql", "/data/cronogramaModuloData.sql", "/data/programacionData.sql"})
    void getAllProgramacions_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/programacions")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).idProgramacion", Matchers.equalTo(3300));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql", "/data/cronogramaModuloData.sql", "/data/programacionData.sql"})
    void getProgramacion_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/programacions/3300")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("idUsuReg", Matchers.equalTo(36));
    }

    @Test
    void getProgramacion_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/programacions/3966")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql", "/data/cronogramaModuloData.sql"})
    void createProgramacion_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/programacionDTORequest.json"))
                .when()
                    .post("/api/programacions")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, programacionRepository.count());
    }

    @Test
    void createProgramacion_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/programacionDTORequest_missingField.json"))
                .when()
                    .post("/api/programacions")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("idUsuReg"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql", "/data/cronogramaModuloData.sql", "/data/programacionData.sql"})
    void updateProgramacion_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/programacionDTORequest.json"))
                .when()
                    .put("/api/programacions/3300")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals(((long)21), programacionRepository.findById(((long)3300)).orElseThrow().getIdUsuReg());
        assertEquals(2, programacionRepository.count());
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql", "/data/cronogramaModuloData.sql", "/data/programacionData.sql"})
    void deleteProgramacion_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/programacions/3300")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, programacionRepository.count());
    }

}
