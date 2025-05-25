package uap.edu.bo.escuela_tecnica.observacion_monografia;

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
import uap.edu.bo.escuela_tecnica.revision_monografia.RevisionMonografia;
import uap.edu.bo.escuela_tecnica.revision_monografia.RevisionMonografiaRepository;
import uap.edu.bo.escuela_tecnica.util.CustomCollectors;


@RestController
@RequestMapping(value = "/api/observacionMonografias", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class ObservacionMonografiaResource {

    private final ObservacionMonografiaService observacionMonografiaService;
    private final RevisionMonografiaRepository revisionMonografiaRepository;

    public ObservacionMonografiaResource(
            final ObservacionMonografiaService observacionMonografiaService,
            final RevisionMonografiaRepository revisionMonografiaRepository) {
        this.observacionMonografiaService = observacionMonografiaService;
        this.revisionMonografiaRepository = revisionMonografiaRepository;
    }

    @GetMapping
    public ResponseEntity<List<ObservacionMonografiaDTO>> getAllObservacionMonografias() {
        return ResponseEntity.ok(observacionMonografiaService.findAll());
    }

    @GetMapping("/{idObservacionMonografia}")
    public ResponseEntity<ObservacionMonografiaDTO> getObservacionMonografia(
            @PathVariable(name = "idObservacionMonografia") final Long idObservacionMonografia) {
        return ResponseEntity.ok(observacionMonografiaService.get(idObservacionMonografia));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createObservacionMonografia(
            @RequestBody @Valid final ObservacionMonografiaDTO observacionMonografiaDTO) {
        final Long createdIdObservacionMonografia = observacionMonografiaService.create(observacionMonografiaDTO);
        return new ResponseEntity<>(createdIdObservacionMonografia, HttpStatus.CREATED);
    }

    @PutMapping("/{idObservacionMonografia}")
    public ResponseEntity<Long> updateObservacionMonografia(
            @PathVariable(name = "idObservacionMonografia") final Long idObservacionMonografia,
            @RequestBody @Valid final ObservacionMonografiaDTO observacionMonografiaDTO) {
        observacionMonografiaService.update(idObservacionMonografia, observacionMonografiaDTO);
        return ResponseEntity.ok(idObservacionMonografia);
    }

    @DeleteMapping("/{idObservacionMonografia}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteObservacionMonografia(
            @PathVariable(name = "idObservacionMonografia") final Long idObservacionMonografia) {
        observacionMonografiaService.delete(idObservacionMonografia);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/revisionMonografiaValues")
    public ResponseEntity<Map<Long, String>> getRevisionMonografiaValues() {
        return ResponseEntity.ok(revisionMonografiaRepository.findAll(Sort.by("idRevisionMonografia"))
                .stream()
                .collect(CustomCollectors.toSortedMap(RevisionMonografia::getIdRevisionMonografia, RevisionMonografia::getEstRevisionMoografia)));
    }

}
