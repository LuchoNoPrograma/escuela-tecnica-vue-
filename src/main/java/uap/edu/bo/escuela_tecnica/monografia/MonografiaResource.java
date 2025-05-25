package uap.edu.bo.escuela_tecnica.monografia;

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
import uap.edu.bo.escuela_tecnica.util.ReferencedException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/monografias", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class MonografiaResource {

    private final MonografiaService monografiaService;
    private final MatriculaRepository matriculaRepository;

    public MonografiaResource(final MonografiaService monografiaService,
            final MatriculaRepository matriculaRepository) {
        this.monografiaService = monografiaService;
        this.matriculaRepository = matriculaRepository;
    }

    @GetMapping
    public ResponseEntity<List<MonografiaDTO>> getAllMonografias() {
        return ResponseEntity.ok(monografiaService.findAll());
    }

    @GetMapping("/{idMonografia}")
    public ResponseEntity<MonografiaDTO> getMonografia(
            @PathVariable(name = "idMonografia") final Long idMonografia) {
        return ResponseEntity.ok(monografiaService.get(idMonografia));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createMonografia(
            @RequestBody @Valid final MonografiaDTO monografiaDTO) {
        final Long createdIdMonografia = monografiaService.create(monografiaDTO);
        return new ResponseEntity<>(createdIdMonografia, HttpStatus.CREATED);
    }

    @PutMapping("/{idMonografia}")
    public ResponseEntity<Long> updateMonografia(
            @PathVariable(name = "idMonografia") final Long idMonografia,
            @RequestBody @Valid final MonografiaDTO monografiaDTO) {
        monografiaService.update(idMonografia, monografiaDTO);
        return ResponseEntity.ok(idMonografia);
    }

    @DeleteMapping("/{idMonografia}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMonografia(
            @PathVariable(name = "idMonografia") final Long idMonografia) {
        final ReferencedWarning referencedWarning = monografiaService.getReferencedWarning(idMonografia);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        monografiaService.delete(idMonografia);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/matriculaValues")
    public ResponseEntity<Map<Long, String>> getMatriculaValues() {
        return ResponseEntity.ok(matriculaRepository.findAll(Sort.by("codMatricula"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Matricula::getCodMatricula, Matricula::getEstMatricula)));
    }

}
