package uap.edu.bo.escuela_tecnica.titulacion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class TitulacionResourceTest extends BaseIT {

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql", "/data/titulacionData.sql"})
    void getAllTitulacions_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/titulacions")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).idTitulacion", Matchers.equalTo(3700));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql", "/data/titulacionData.sql"})
    void getTitulacion_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/titulacions/3700")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("idUsuReg", Matchers.equalTo(36));
    }

    @Test
    void getTitulacion_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/titulacions/4366")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql"})
    void createTitulacion_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/titulacionDTORequest.json"))
                .when()
                    .post("/api/titulacions")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, titulacionRepository.count());
    }

    @Test
    void createTitulacion_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/titulacionDTORequest_missingField.json"))
                .when()
                    .post("/api/titulacions")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("idUsuReg"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql", "/data/titulacionData.sql"})
    void updateTitulacion_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/titulacionDTORequest.json"))
                .when()
                    .put("/api/titulacions/3700")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals(((long)21), titulacionRepository.findById(((long)3700)).orElseThrow().getIdUsuReg());
        assertEquals(2, titulacionRepository.count());
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql", "/data/titulacionData.sql"})
    void deleteTitulacion_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/titulacions/3700")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, titulacionRepository.count());
    }

}
