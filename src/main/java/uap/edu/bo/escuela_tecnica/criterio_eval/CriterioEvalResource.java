package uap.edu.bo.escuela_tecnica.criterio_eval;

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
import uap.edu.bo.escuela_tecnica.cronograma_modulo.CronogramaModulo;
import uap.edu.bo.escuela_tecnica.cronograma_modulo.CronogramaModuloRepository;
import uap.edu.bo.escuela_tecnica.util.CustomCollectors;
import uap.edu.bo.escuela_tecnica.util.ReferencedException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/criterioEvals", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class CriterioEvalResource {

    private final CriterioEvalService criterioEvalService;
    private final CronogramaModuloRepository cronogramaModuloRepository;

    public CriterioEvalResource(final CriterioEvalService criterioEvalService,
            final CronogramaModuloRepository cronogramaModuloRepository) {
        this.criterioEvalService = criterioEvalService;
        this.cronogramaModuloRepository = cronogramaModuloRepository;
    }

    @GetMapping
    public ResponseEntity<List<CriterioEvalDTO>> getAllCriterioEvals() {
        return ResponseEntity.ok(criterioEvalService.findAll());
    }

    @GetMapping("/{idCriterioEval}")
    public ResponseEntity<CriterioEvalDTO> getCriterioEval(
            @PathVariable(name = "idCriterioEval") final Long idCriterioEval) {
        return ResponseEntity.ok(criterioEvalService.get(idCriterioEval));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCriterioEval(
            @RequestBody @Valid final CriterioEvalDTO criterioEvalDTO) {
        final Long createdIdCriterioEval = criterioEvalService.create(criterioEvalDTO);
        return new ResponseEntity<>(createdIdCriterioEval, HttpStatus.CREATED);
    }

    @PutMapping("/{idCriterioEval}")
    public ResponseEntity<Long> updateCriterioEval(
            @PathVariable(name = "idCriterioEval") final Long idCriterioEval,
            @RequestBody @Valid final CriterioEvalDTO criterioEvalDTO) {
        criterioEvalService.update(idCriterioEval, criterioEvalDTO);
        return ResponseEntity.ok(idCriterioEval);
    }

    @DeleteMapping("/{idCriterioEval}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCriterioEval(
            @PathVariable(name = "idCriterioEval") final Long idCriterioEval) {
        final ReferencedWarning referencedWarning = criterioEvalService.getReferencedWarning(idCriterioEval);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        criterioEvalService.delete(idCriterioEval);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cronogramaModValues")
    public ResponseEntity<Map<Long, String>> getCronogramaModValues() {
        return ResponseEntity.ok(cronogramaModuloRepository.findAll(Sort.by("idCronogramaMod"))
                .stream()
                .collect(CustomCollectors.toSortedMap(CronogramaModulo::getIdCronogramaMod, CronogramaModulo::getEstado)));
    }

}
