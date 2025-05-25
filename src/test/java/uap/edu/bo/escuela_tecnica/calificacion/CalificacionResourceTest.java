package uap.edu.bo.escuela_tecnica.calificacion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class CalificacionResourceTest extends BaseIT {

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql", "/data/cronogramaModuloData.sql", "/data/programacionData.sql", "/data/criterioEvalData.sql", "/data/calificacionData.sql"})
    void getAllCalificacions_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/calificacions")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).idCalificacion", Matchers.equalTo(1100));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql", "/data/cronogramaModuloData.sql", "/data/programacionData.sql", "/data/criterioEvalData.sql", "/data/calificacionData.sql"})
    void getCalificacion_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/calificacions/1100")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("idUsuReg", Matchers.equalTo(36));
    }

    @Test
    void getCalificacion_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/calificacions/1766")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql", "/data/cronogramaModuloData.sql", "/data/programacionData.sql", "/data/criterioEvalData.sql"})
    void createCalificacion_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/calificacionDTORequest.json"))
                .when()
                    .post("/api/calificacions")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, calificacionRepository.count());
    }

    @Test
    void createCalificacion_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/calificacionDTORequest_missingField.json"))
                .when()
                    .post("/api/calificacions")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("idUsuReg"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql", "/data/cronogramaModuloData.sql", "/data/programacionData.sql", "/data/criterioEvalData.sql", "/data/calificacionData.sql"})
    void updateCalificacion_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/calificacionDTORequest.json"))
                .when()
                    .put("/api/calificacions/1100")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals(((long)21), calificacionRepository.findById(((long)1100)).orElseThrow().getIdUsuReg());
        assertEquals(2, calificacionRepository.count());
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql", "/data/cronogramaModuloData.sql", "/data/programacionData.sql", "/data/criterioEvalData.sql", "/data/calificacionData.sql"})
    void deleteCalificacion_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/calificacions/1100")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, calificacionRepository.count());
    }

}
