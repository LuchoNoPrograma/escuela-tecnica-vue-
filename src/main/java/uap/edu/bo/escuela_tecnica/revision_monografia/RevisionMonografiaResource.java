package uap.edu.bo.escuela_tecnica.revision_monografia;

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
import uap.edu.bo.escuela_tecnica.administrativo.Administrativo;
import uap.edu.bo.escuela_tecnica.administrativo.AdministrativoRepository;
import uap.edu.bo.escuela_tecnica.docente.Docente;
import uap.edu.bo.escuela_tecnica.docente.DocenteRepository;
import uap.edu.bo.escuela_tecnica.monografia.Monografia;
import uap.edu.bo.escuela_tecnica.monografia.MonografiaRepository;
import uap.edu.bo.escuela_tecnica.util.CustomCollectors;
import uap.edu.bo.escuela_tecnica.util.ReferencedException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/revisionMonografias", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class RevisionMonografiaResource {

    private final RevisionMonografiaService revisionMonografiaService;
    private final MonografiaRepository monografiaRepository;
    private final AdministrativoRepository administrativoRepository;
    private final DocenteRepository docenteRepository;

    public RevisionMonografiaResource(final RevisionMonografiaService revisionMonografiaService,
            final MonografiaRepository monografiaRepository,
            final AdministrativoRepository administrativoRepository,
            final DocenteRepository docenteRepository) {
        this.revisionMonografiaService = revisionMonografiaService;
        this.monografiaRepository = monografiaRepository;
        this.administrativoRepository = administrativoRepository;
        this.docenteRepository = docenteRepository;
    }

    @GetMapping
    public ResponseEntity<List<RevisionMonografiaDTO>> getAllRevisionMonografias() {
        return ResponseEntity.ok(revisionMonografiaService.findAll());
    }

    @GetMapping("/{idRevisionMonografia}")
    public ResponseEntity<RevisionMonografiaDTO> getRevisionMonografia(
            @PathVariable(name = "idRevisionMonografia") final Long idRevisionMonografia) {
        return ResponseEntity.ok(revisionMonografiaService.get(idRevisionMonografia));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createRevisionMonografia(
            @RequestBody @Valid final RevisionMonografiaDTO revisionMonografiaDTO) {
        final Long createdIdRevisionMonografia = revisionMonografiaService.create(revisionMonografiaDTO);
        return new ResponseEntity<>(createdIdRevisionMonografia, HttpStatus.CREATED);
    }

    @PutMapping("/{idRevisionMonografia}")
    public ResponseEntity<Long> updateRevisionMonografia(
            @PathVariable(name = "idRevisionMonografia") final Long idRevisionMonografia,
            @RequestBody @Valid final RevisionMonografiaDTO revisionMonografiaDTO) {
        revisionMonografiaService.update(idRevisionMonografia, revisionMonografiaDTO);
        return ResponseEntity.ok(idRevisionMonografia);
    }

    @DeleteMapping("/{idRevisionMonografia}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRevisionMonografia(
            @PathVariable(name = "idRevisionMonografia") final Long idRevisionMonografia) {
        final ReferencedWarning referencedWarning = revisionMonografiaService.getReferencedWarning(idRevisionMonografia);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        revisionMonografiaService.delete(idRevisionMonografia);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/monografiaValues")
    public ResponseEntity<Map<Long, String>> getMonografiaValues() {
        return ResponseEntity.ok(monografiaRepository.findAll(Sort.by("idMonografia"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Monografia::getIdMonografia, Monografia::getTitulo)));
    }

    @GetMapping("/administrativoValues")
    public ResponseEntity<Map<Long, Long>> getAdministrativoValues() {
        return ResponseEntity.ok(administrativoRepository.findAll(Sort.by("idAdministrativo"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Administrativo::getIdAdministrativo, Administrativo::getIdAdministrativo)));
    }

    @GetMapping("/docenteValues")
    public ResponseEntity<Map<Long, Long>> getDocenteValues() {
        return ResponseEntity.ok(docenteRepository.findAll(Sort.by("idDocente"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Docente::getIdDocente, Docente::getIdDocente)));
    }

}
