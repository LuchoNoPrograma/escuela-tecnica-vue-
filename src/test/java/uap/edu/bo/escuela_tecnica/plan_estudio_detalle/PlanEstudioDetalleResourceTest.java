package uap.edu.bo.escuela_tecnica.plan_estudio_detalle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class PlanEstudioDetalleResourceTest extends BaseIT {

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql"})
    void getAllPlanEstudioDetalles_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/planEstudioDetalles")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).idPlanEstudioDetalle", Matchers.equalTo(3000));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql"})
    void getPlanEstudioDetalle_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/planEstudioDetalles/3000")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("idUsuReg", Matchers.equalTo(36));
    }

    @Test
    void getPlanEstudioDetalle_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/planEstudioDetalles/3666")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql"})
    void createPlanEstudioDetalle_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/planEstudioDetalleDTORequest.json"))
                .when()
                    .post("/api/planEstudioDetalles")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, planEstudioDetalleRepository.count());
    }

    @Test
    void createPlanEstudioDetalle_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/planEstudioDetalleDTORequest_missingField.json"))
                .when()
                    .post("/api/planEstudioDetalles")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("idUsuReg"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql"})
    void updatePlanEstudioDetalle_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/planEstudioDetalleDTORequest.json"))
                .when()
                    .put("/api/planEstudioDetalles/3000")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals(((long)21), planEstudioDetalleRepository.findById(((long)3000)).orElseThrow().getIdUsuReg());
        assertEquals(2, planEstudioDetalleRepository.count());
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/nivelData.sql", "/data/moduloData.sql", "/data/planEstudioData.sql", "/data/planEstudioDetalleData.sql"})
    void deletePlanEstudioDetalle_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/planEstudioDetalles/3000")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, planEstudioDetalleRepository.count());
    }

}
