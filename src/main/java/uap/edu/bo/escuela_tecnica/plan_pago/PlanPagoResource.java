package uap.edu.bo.escuela_tecnica.plan_pago;

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
@RequestMapping(value = "/api/planPagos", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class PlanPagoResource {

    private final PlanPagoService planPagoService;
    private final MatriculaRepository matriculaRepository;

    public PlanPagoResource(final PlanPagoService planPagoService,
            final MatriculaRepository matriculaRepository) {
        this.planPagoService = planPagoService;
        this.matriculaRepository = matriculaRepository;
    }

    @GetMapping
    public ResponseEntity<List<PlanPagoDTO>> getAllPlanPagos() {
        return ResponseEntity.ok(planPagoService.findAll());
    }

    @GetMapping("/{idPlanPago}")
    public ResponseEntity<PlanPagoDTO> getPlanPago(
            @PathVariable(name = "idPlanPago") final Long idPlanPago) {
        return ResponseEntity.ok(planPagoService.get(idPlanPago));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPlanPago(@RequestBody @Valid final PlanPagoDTO planPagoDTO) {
        final Long createdIdPlanPago = planPagoService.create(planPagoDTO);
        return new ResponseEntity<>(createdIdPlanPago, HttpStatus.CREATED);
    }

    @PutMapping("/{idPlanPago}")
    public ResponseEntity<Long> updatePlanPago(
            @PathVariable(name = "idPlanPago") final Long idPlanPago,
            @RequestBody @Valid final PlanPagoDTO planPagoDTO) {
        planPagoService.update(idPlanPago, planPagoDTO);
        return ResponseEntity.ok(idPlanPago);
    }

    @DeleteMapping("/{idPlanPago}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePlanPago(
            @PathVariable(name = "idPlanPago") final Long idPlanPago) {
        final ReferencedWarning referencedWarning = planPagoService.getReferencedWarning(idPlanPago);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        planPagoService.delete(idPlanPago);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/matriculaValues")
    public ResponseEntity<Map<Long, String>> getMatriculaValues() {
        return ResponseEntity.ok(matriculaRepository.findAll(Sort.by("codMatricula"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Matricula::getCodMatricula, Matricula::getEstMatricula)));
    }

}
