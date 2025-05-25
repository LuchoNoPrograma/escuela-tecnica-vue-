package uap.edu.bo.escuela_tecnica.detalle_pago;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class DetallePagoResourceTest extends BaseIT {

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql", "/data/planPagoData.sql", "/data/detallePagoData.sql"})
    void getAllDetallePagos_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/detallePagos")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).idDetallePago", Matchers.equalTo(1800));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql", "/data/planPagoData.sql", "/data/detallePagoData.sql"})
    void getDetallePago_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/detallePagos/1800")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("idUsuReg", Matchers.equalTo(36));
    }

    @Test
    void getDetallePago_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/detallePagos/2466")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql", "/data/planPagoData.sql"})
    void createDetallePago_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/detallePagoDTORequest.json"))
                .when()
                    .post("/api/detallePagos")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, detallePagoRepository.count());
    }

    @Test
    void createDetallePago_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/detallePagoDTORequest_missingField.json"))
                .when()
                    .post("/api/detallePagos")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("idUsuReg"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql", "/data/planPagoData.sql", "/data/detallePagoData.sql"})
    void updateDetallePago_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/detallePagoDTORequest.json"))
                .when()
                    .put("/api/detallePagos/1800")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals(((long)21), detallePagoRepository.findById(((long)1800)).orElseThrow().getIdUsuReg());
        assertEquals(2, detallePagoRepository.count());
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql", "/data/planPagoData.sql", "/data/detallePagoData.sql"})
    void deleteDetallePago_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/detallePagos/1800")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, detallePagoRepository.count());
    }

}
