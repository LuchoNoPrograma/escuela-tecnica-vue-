package uap.edu.bo.escuela_tecnica.calificacion;

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
import uap.edu.bo.escuela_tecnica.criterio_eval.CriterioEval;
import uap.edu.bo.escuela_tecnica.criterio_eval.CriterioEvalRepository;
import uap.edu.bo.escuela_tecnica.programacion.Programacion;
import uap.edu.bo.escuela_tecnica.programacion.ProgramacionRepository;
import uap.edu.bo.escuela_tecnica.util.CustomCollectors;


@RestController
@RequestMapping(value = "/api/calificacions", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class CalificacionResource {

    private final CalificacionService calificacionService;
    private final CriterioEvalRepository criterioEvalRepository;
    private final ProgramacionRepository programacionRepository;

    public CalificacionResource(final CalificacionService calificacionService,
            final CriterioEvalRepository criterioEvalRepository,
            final ProgramacionRepository programacionRepository) {
        this.calificacionService = calificacionService;
        this.criterioEvalRepository = criterioEvalRepository;
        this.programacionRepository = programacionRepository;
    }

    @GetMapping
    public ResponseEntity<List<CalificacionDTO>> getAllCalificacions() {
        return ResponseEntity.ok(calificacionService.findAll());
    }

    @GetMapping("/{idCalificacion}")
    public ResponseEntity<CalificacionDTO> getCalificacion(
            @PathVariable(name = "idCalificacion") final Long idCalificacion) {
        return ResponseEntity.ok(calificacionService.get(idCalificacion));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCalificacion(
            @RequestBody @Valid final CalificacionDTO calificacionDTO) {
        final Long createdIdCalificacion = calificacionService.create(calificacionDTO);
        return new ResponseEntity<>(createdIdCalificacion, HttpStatus.CREATED);
    }

    @PutMapping("/{idCalificacion}")
    public ResponseEntity<Long> updateCalificacion(
            @PathVariable(name = "idCalificacion") final Long idCalificacion,
            @RequestBody @Valid final CalificacionDTO calificacionDTO) {
        calificacionService.update(idCalificacion, calificacionDTO);
        return ResponseEntity.ok(idCalificacion);
    }

    @DeleteMapping("/{idCalificacion}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCalificacion(
            @PathVariable(name = "idCalificacion") final Long idCalificacion) {
        calificacionService.delete(idCalificacion);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/criterioEvalValues")
    public ResponseEntity<Map<Long, String>> getCriterioEvalValues() {
        return ResponseEntity.ok(criterioEvalRepository.findAll(Sort.by("idCriterioEval"))
                .stream()
                .collect(CustomCollectors.toSortedMap(CriterioEval::getIdCriterioEval, CriterioEval::getNombreCrit)));
    }

    @GetMapping("/programacionValues")
    public ResponseEntity<Map<Long, String>> getProgramacionValues() {
        return ResponseEntity.ok(programacionRepository.findAll(Sort.by("idProgramacion"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Programacion::getIdProgramacion, Programacion::getEstCalificacion)));
    }

}
