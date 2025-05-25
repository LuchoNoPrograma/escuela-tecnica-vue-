package uap.edu.bo.escuela_tecnica.docente;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class DocenteResourceTest extends BaseIT {

    @Test
    @Sql("/data/docenteData.sql")
    void getAllDocentes_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/docentes")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).idDocente", Matchers.equalTo(1900));
    }

    @Test
    @Sql("/data/docenteData.sql")
    void getDocente_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/docentes/1900")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("idUsuReg", Matchers.equalTo(36));
    }

    @Test
    void getDocente_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/docentes/2566")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createDocente_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/docenteDTORequest.json"))
                .when()
                    .post("/api/docentes")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, docenteRepository.count());
    }

    @Test
    void createDocente_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/docenteDTORequest_missingField.json"))
                .when()
                    .post("/api/docentes")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("idUsuReg"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql("/data/docenteData.sql")
    void updateDocente_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/docenteDTORequest.json"))
                .when()
                    .put("/api/docentes/1900")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals(((long)21), docenteRepository.findById(((long)1900)).orElseThrow().getIdUsuReg());
        assertEquals(2, docenteRepository.count());
    }

    @Test
    @Sql("/data/docenteData.sql")
    void deleteDocente_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/docentes/1900")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, docenteRepository.count());
    }

}
