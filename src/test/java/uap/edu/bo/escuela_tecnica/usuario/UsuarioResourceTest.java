package uap.edu.bo.escuela_tecnica.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class UsuarioResourceTest extends BaseIT {

    @Test
    void getAllUsuarios_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/usuarios")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(4))
                    .body("get(0).idUsuario", Matchers.equalTo(3800));
    }

    @Test
    void getUsuario_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/usuarios/3800")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("idUsuReg", Matchers.equalTo(36));
    }

    @Test
    void getUsuario_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/usuarios/4466")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createUsuario_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/usuarioDTORequest.json"))
                .when()
                    .post("/api/usuarios")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(5, usuarioRepository.count());
    }

    @Test
    void createUsuario_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/usuarioDTORequest_missingField.json"))
                .when()
                    .post("/api/usuarios")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("idUsuReg"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    void updateUsuario_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/usuarioDTORequest.json"))
                .when()
                    .put("/api/usuarios/3800")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals(((long)21), usuarioRepository.findById(((long)3800)).orElseThrow().getIdUsuReg());
        assertEquals(4, usuarioRepository.count());
    }

    @Test
    void deleteUsuario_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/usuarios/3800")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(3, usuarioRepository.count());
    }

}
