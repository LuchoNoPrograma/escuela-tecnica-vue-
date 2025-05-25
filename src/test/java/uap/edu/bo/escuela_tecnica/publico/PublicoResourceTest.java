package uap.edu.bo.escuela_tecnica.publico;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import uap.edu.bo.escuela_tecnica.config.BaseIT;


public class PublicoResourceTest extends BaseIT {

    @Test
    void paginaInicio_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/vista/inicio")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

}
