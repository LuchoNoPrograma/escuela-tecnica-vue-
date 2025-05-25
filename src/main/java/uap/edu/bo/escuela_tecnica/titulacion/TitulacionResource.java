package uap.edu.bo.escuela_tecnica.titulacion;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uap.edu.bo.escuela_tecnica.matricula.Matricula;
import uap.edu.bo.escuela_tecnica.matricula.MatriculaRepository;
import uap.edu.bo.escuela_tecnica.util.CustomCollectors;


@RestController
@RequestMapping(value = "/api/titulacions", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class TitulacionResource {

    private final TitulacionService titulacionService;
    private final MatriculaRepository matriculaRepository;

    public TitulacionResource(final TitulacionService titulacionService,
            final MatriculaRepository matriculaRepository) {
        this.titulacionService = titulacionService;
        this.matriculaRepository = matriculaRepository;
    }

    @GetMapping
    public ResponseEntity<List<TitulacionDTO>> getAllTitulacions() {
        return ResponseEntity.ok(titulacionService.findAll());
    }

    @GetMapping("/{idTitulacion}")
    public ResponseEntity<TitulacionDTO> getTitulacion(
            @PathVariable(name = "idTitulacion") final Long idTitulacion) {
        return ResponseEntity.ok(titulacionService.get(idTitulacion));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createTitulacion(
            @RequestBody @Valid final TitulacionDTO titulacionDTO) {
        final Long createdIdTitulacion = titulacionService.create(titulacionDTO);
        return new ResponseEntity<>(createdIdTitulacion, HttpStatus.CREATED);
    }

    @PutMapping("/{idTitulacion}")
    public ResponseEntity<Long> updateTitulacion(
            @PathVariable(name = "idTitulacion") final Long idTitulacion,
            @RequestBody @Valid final TitulacionDTO titulacionDTO) {
        titulacionService.update(idTitulacion, titulacionDTO);
        return ResponseEntity.ok(idTitulacion);
    }

    @DeleteMapping("/{idTitulacion}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTitulacion(
            @PathVariable(name = "idTitulacion") final Long idTitulacion) {
        titulacionService.delete(idTitulacion);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/matriculaValues")
    public ResponseEntity<Map<Long, String>> getMatriculaValues() {
        return ResponseEntity.ok(matriculaRepository.findAll(Sort.by("codMatricula"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Matricula::getCodMatricula, Matricula::getEstMatricula)));
    }

}
