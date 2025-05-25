package uap.edu.bo.escuela_tecnica.certificado;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class CertificadoResourceTest extends BaseIT {

    @Test
    @Sql({"/data/administrativoData.sql", "/data/certificadoData.sql"})
    void getAllCertificados_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/certificados")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).idCertificado", Matchers.equalTo(1300));
    }

    @Test
    @Sql({"/data/administrativoData.sql", "/data/certificadoData.sql"})
    void getCertificado_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/certificados/1300")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("idUsuReg", Matchers.equalTo(36));
    }

    @Test
    void getCertificado_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/certificados/1966")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    @Sql("/data/administrativoData.sql")
    void createCertificado_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/certificadoDTORequest.json"))
                .when()
                    .post("/api/certificados")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, certificadoRepository.count());
    }

    @Test
    void createCertificado_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/certificadoDTORequest_missingField.json"))
                .when()
                    .post("/api/certificados")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("idUsuReg"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql({"/data/administrativoData.sql", "/data/certificadoData.sql"})
    void updateCertificado_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/certificadoDTORequest.json"))
                .when()
                    .put("/api/certificados/1300")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals(((long)21), certificadoRepository.findById(((long)1300)).orElseThrow().getIdUsuReg());
        assertEquals(2, certificadoRepository.count());
    }

    @Test
    @Sql({"/data/administrativoData.sql", "/data/certificadoData.sql"})
    void deleteCertificado_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/certificados/1300")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, certificadoRepository.count());
    }

}
