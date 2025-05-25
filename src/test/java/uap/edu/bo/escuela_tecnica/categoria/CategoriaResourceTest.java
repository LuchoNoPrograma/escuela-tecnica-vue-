package uap.edu.bo.escuela_tecnica.categoria;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class CategoriaResourceTest extends BaseIT {

    @Test
    @Sql("/data/categoriaData.sql")
    void getAllCategorias_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/categorias")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", Matchers.equalTo(2))
                    .body("get(0).idCategoria", Matchers.equalTo(1200));
    }

    @Test
    @Sql("/data/categoriaData.sql")
    void getCategoria_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/categorias/1200")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("idUsuReg", Matchers.equalTo(36));
    }

    @Test
    void getCategoria_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/categorias/1866")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createCategoria_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/categoriaDTORequest.json"))
                .when()
                    .post("/api/categorias")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, categoriaRepository.count());
    }

    @Test
    void createCategoria_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/categoriaDTORequest_missingField.json"))
                .when()
                    .post("/api/categorias")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("idUsuReg"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql("/data/categoriaData.sql")
    void updateCategoria_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/categoriaDTORequest.json"))
                .when()
                    .put("/api/categorias/1200")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals(((long)21), categoriaRepository.findById(((long)1200)).orElseThrow().getIdUsuReg());
        assertEquals(2, categoriaRepository.count());
    }

    @Test
    @Sql("/data/categoriaData.sql")
    void deleteCategoria_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, administradorSistemasJwtSecurityConfigToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/categorias/1200")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, categoriaRepository.count());
    }

}
