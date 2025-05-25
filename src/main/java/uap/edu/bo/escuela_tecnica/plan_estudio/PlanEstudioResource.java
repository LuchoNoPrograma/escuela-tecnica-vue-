package uap.edu.bo.escuela_tecnica.plan_estudio;

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
import uap.edu.bo.escuela_tecnica.programa.Programa;
import uap.edu.bo.escuela_tecnica.programa.ProgramaRepository;
import uap.edu.bo.escuela_tecnica.util.CustomCollectors;
import uap.edu.bo.escuela_tecnica.util.ReferencedException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/planEstudios", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class PlanEstudioResource {

    private final PlanEstudioService planEstudioService;
    private final ProgramaRepository programaRepository;

    public PlanEstudioResource(final PlanEstudioService planEstudioService,
            final ProgramaRepository programaRepository) {
        this.planEstudioService = planEstudioService;
        this.programaRepository = programaRepository;
    }

    @GetMapping
    public ResponseEntity<List<PlanEstudioDTO>> getAllPlanEstudios() {
        return ResponseEntity.ok(planEstudioService.findAll());
    }

    @GetMapping("/{idPlanEstudio}")
    public ResponseEntity<PlanEstudioDTO> getPlanEstudio(
            @PathVariable(name = "idPlanEstudio") final Long idPlanEstudio) {
        return ResponseEntity.ok(planEstudioService.get(idPlanEstudio));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPlanEstudio(
            @RequestBody @Valid final PlanEstudioDTO planEstudioDTO) {
        final Long createdIdPlanEstudio = planEstudioService.create(planEstudioDTO);
        return new ResponseEntity<>(createdIdPlanEstudio, HttpStatus.CREATED);
    }

    @PutMapping("/{idPlanEstudio}")
    public ResponseEntity<Long> updatePlanEstudio(
            @PathVariable(name = "idPlanEstudio") final Long idPlanEstudio,
            @RequestBody @Valid final PlanEstudioDTO planEstudioDTO) {
        planEstudioService.update(idPlanEstudio, planEstudioDTO);
        return ResponseEntity.ok(idPlanEstudio);
    }

    @DeleteMapping("/{idPlanEstudio}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePlanEstudio(
            @PathVariable(name = "idPlanEstudio") final Long idPlanEstudio) {
        final ReferencedWarning referencedWarning = planEstudioService.getReferencedWarning(idPlanEstudio);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        planEstudioService.delete(idPlanEstudio);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/programaValues")
    public ResponseEntity<Map<Long, String>> getProgramaValues() {
        return ResponseEntity.ok(programaRepository.findAll(Sort.by("idPrograma"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Programa::getIdPrograma, Programa::getNombrePrograma)));
    }

}
