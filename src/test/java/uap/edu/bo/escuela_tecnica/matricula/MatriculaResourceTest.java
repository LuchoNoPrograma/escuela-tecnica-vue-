package uap.edu.bo.escuela_tecnica.matricula;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class MatriculaResourceTest extends BaseIT {

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql"})
    void getAllMatriculas_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/matriculas")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).codMatricula", Matchers.equalTo(2100));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql"})
    void getMatricula_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/matriculas/2100")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("idUsuReg", Matchers.equalTo(36));
    }

    @Test
    void getMatricula_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/matriculas/2766")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql"})
    void createMatricula_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/matriculaDTORequest.json"))
                .when()
                    .post("/api/matriculas")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, matriculaRepository.count());
    }

    @Test
    void createMatricula_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/matriculaDTORequest_missingField.json"))
                .when()
                    .post("/api/matriculas")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("idUsuReg"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql"})
    void updateMatricula_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/matriculaDTORequest.json"))
                .when()
                    .put("/api/matriculas/2100")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals(((long)21), matriculaRepository.findById(((long)2100)).orElseThrow().getIdUsuReg());
        assertEquals(2, matriculaRepository.count());
    }

    @Test
    @Sql({"/data/modalidadData.sql", "/data/categoriaData.sql", "/data/programaData.sql", "/data/grupoData.sql", "/data/matriculaData.sql"})
    void deleteMatricula_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/matriculas/2100")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, matriculaRepository.count());
    }

}
