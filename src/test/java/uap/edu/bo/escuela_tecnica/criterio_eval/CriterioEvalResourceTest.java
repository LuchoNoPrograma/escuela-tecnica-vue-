package uap.edu.bo.escuela_tecnica.criterio_eval;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class CriterioEvalResourceTest extends BaseIT {

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql", "/data/cronogramaModuloData.sql", "/data/criterioEvalData.sql"})
    void getAllCriterioEvals_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/criterioEvals")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).idCriterioEval", Matchers.equalTo(1600));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql", "/data/cronogramaModuloData.sql", "/data/criterioEvalData.sql"})
    void getCriterioEval_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/criterioEvals/1600")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("idUsuReg", Matchers.equalTo(36));
    }

    @Test
    void getCriterioEval_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/criterioEvals/2266")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql", "/data/cronogramaModuloData.sql"})
    void createCriterioEval_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/criterioEvalDTORequest.json"))
                .when()
                    .post("/api/criterioEvals")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, criterioEvalRepository.count());
    }

    @Test
    void createCriterioEval_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/criterioEvalDTORequest_missingField.json"))
                .when()
                    .post("/api/criterioEvals")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("idUsuReg"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql", "/data/cronogramaModuloData.sql", "/data/criterioEvalData.sql"})
    void updateCriterioEval_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/criterioEvalDTORequest.json"))
                .when()
                    .put("/api/criterioEvals/1600")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals(((long)21), criterioEvalRepository.findById(((long)1600)).orElseThrow().getIdUsuReg());
        assertEquals(2, criterioEvalRepository.count());
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql", "/data/cronogramaModuloData.sql", "/data/criterioEvalData.sql"})
    void deleteCriterioEval_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/criterioEvals/1600")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, criterioEvalRepository.count());
    }

}
