package uap.edu.bo.escuela_tecnica.comprobante_pago;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class ComprobantePagoResourceTest extends BaseIT {

    @Test
    @Sql("/data/comprobantePagoData.sql")
    void getAllComprobantePagos_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/comprobantePagos")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).idComprobante", Matchers.equalTo(1400));
    }

    @Test
    @Sql("/data/comprobantePagoData.sql")
    void getComprobantePago_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/comprobantePagos/1400")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("idUsuReg", Matchers.equalTo(36));
    }

    @Test
    void getComprobantePago_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/comprobantePagos/2066")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createComprobantePago_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/comprobantePagoDTORequest.json"))
                .when()
                    .post("/api/comprobantePagos")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, comprobantePagoRepository.count());
    }

    @Test
    void createComprobantePago_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/comprobantePagoDTORequest_missingField.json"))
                .when()
                    .post("/api/comprobantePagos")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("idUsuReg"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql("/data/comprobantePagoData.sql")
    void updateComprobantePago_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/comprobantePagoDTORequest.json"))
                .when()
                    .put("/api/comprobantePagos/1400")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals(((long)21), comprobantePagoRepository.findById(((long)1400)).orElseThrow().getIdUsuReg());
        assertEquals(2, comprobantePagoRepository.count());
    }

    @Test
    @Sql("/data/comprobantePagoData.sql")
    void deleteComprobantePago_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/comprobantePagos/1400")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, comprobantePagoRepository.count());
    }

}
