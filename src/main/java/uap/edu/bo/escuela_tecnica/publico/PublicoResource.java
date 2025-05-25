package uap.edu.bo.escuela_tecnica.publico;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class PublicoResource {

    @GetMapping("/vista/inicio")
    public ResponseEntity<Void> paginaInicio() {
        return ResponseEntity.ok().build();
    }

}
