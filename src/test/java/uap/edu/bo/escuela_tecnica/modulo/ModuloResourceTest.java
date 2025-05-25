package uap.edu.bo.escuela_tecnica.modulo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class ModuloResourceTest extends BaseIT {

    @Test
    @Sql("/data/moduloData.sql")
    void getAllModulos_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/modulos")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).idModulo", Matchers.equalTo(2300));
    }

    @Test
    @Sql("/data/moduloData.sql")
    void getModulo_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/modulos/2300")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("idUsuReg", Matchers.equalTo(36));
    }

    @Test
    void getModulo_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/modulos/2966")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createModulo_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/moduloDTORequest.json"))
                .when()
                    .post("/api/modulos")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, moduloRepository.count());
    }

    @Test
    void createModulo_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/moduloDTORequest_missingField.json"))
                .when()
                    .post("/api/modulos")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("idUsuReg"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql("/data/moduloData.sql")
    void updateModulo_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/moduloDTORequest.json"))
                .when()
                    .put("/api/modulos/2300")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals(((long)21), moduloRepository.findById(((long)2300)).orElseThrow().getIdUsuReg());
        assertEquals(2, moduloRepository.count());
    }

    @Test
    @Sql("/data/moduloData.sql")
    void deleteModulo_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/modulos/2300")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, moduloRepository.count());
    }

}
