package uap.edu.bo.escuela_tecnica.modalidad;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class ModalidadResourceTest extends BaseIT {

    @Test
    @Sql("/data/modalidadData.sql")
    void getAllModalidads_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/modalidads")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).idModalidad", Matchers.equalTo(2200));
    }

    @Test
    @Sql("/data/modalidadData.sql")
    void getModalidad_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/modalidads/2200")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("idUsuReg", Matchers.equalTo(36));
    }

    @Test
    void getModalidad_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/modalidads/2866")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createModalidad_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/modalidadDTORequest.json"))
                .when()
                    .post("/api/modalidads")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, modalidadRepository.count());
    }

    @Test
    void createModalidad_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/modalidadDTORequest_missingField.json"))
                .when()
                    .post("/api/modalidads")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("idUsuReg"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql("/data/modalidadData.sql")
    void updateModalidad_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/modalidadDTORequest.json"))
                .when()
                    .put("/api/modalidads/2200")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals(((long)21), modalidadRepository.findById(((long)2200)).orElseThrow().getIdUsuReg());
        assertEquals(2, modalidadRepository.count());
    }

    @Test
    @Sql("/data/modalidadData.sql")
    void deleteModalidad_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/modalidads/2200")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, modalidadRepository.count());
    }

}
