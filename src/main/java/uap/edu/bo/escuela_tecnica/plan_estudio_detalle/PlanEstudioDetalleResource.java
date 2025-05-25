package uap.edu.bo.escuela_tecnica.plan_estudio_detalle;

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
import uap.edu.bo.escuela_tecnica.modulo.Modulo;
import uap.edu.bo.escuela_tecnica.modulo.ModuloRepository;
import uap.edu.bo.escuela_tecnica.nivel.Nivel;
import uap.edu.bo.escuela_tecnica.nivel.NivelRepository;
import uap.edu.bo.escuela_tecnica.plan_estudio.PlanEstudio;
import uap.edu.bo.escuela_tecnica.plan_estudio.PlanEstudioRepository;
import uap.edu.bo.escuela_tecnica.util.CustomCollectors;
import uap.edu.bo.escuela_tecnica.util.ReferencedException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/planEstudioDetalles", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class PlanEstudioDetalleResource {

    private final PlanEstudioDetalleService planEstudioDetalleService;
    private final NivelRepository nivelRepository;
    private final ModuloRepository moduloRepository;
    private final PlanEstudioRepository planEstudioRepository;

    public PlanEstudioDetalleResource(final PlanEstudioDetalleService planEstudioDetalleService,
            final NivelRepository nivelRepository, final ModuloRepository moduloRepository,
            final PlanEstudioRepository planEstudioRepository) {
        this.planEstudioDetalleService = planEstudioDetalleService;
        this.nivelRepository = nivelRepository;
        this.moduloRepository = moduloRepository;
        this.planEstudioRepository = planEstudioRepository;
    }

    @GetMapping
    public ResponseEntity<List<PlanEstudioDetalleDTO>> getAllPlanEstudioDetalles() {
        return ResponseEntity.ok(planEstudioDetalleService.findAll());
    }

    @GetMapping("/{idPlanEstudioDetalle}")
    public ResponseEntity<PlanEstudioDetalleDTO> getPlanEstudioDetalle(
            @PathVariable(name = "idPlanEstudioDetalle") final Long idPlanEstudioDetalle) {
        return ResponseEntity.ok(planEstudioDetalleService.get(idPlanEstudioDetalle));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPlanEstudioDetalle(
            @RequestBody @Valid final PlanEstudioDetalleDTO planEstudioDetalleDTO) {
        final Long createdIdPlanEstudioDetalle = planEstudioDetalleService.create(planEstudioDetalleDTO);
        return new ResponseEntity<>(createdIdPlanEstudioDetalle, HttpStatus.CREATED);
    }

    @PutMapping("/{idPlanEstudioDetalle}")
    public ResponseEntity<Long> updatePlanEstudioDetalle(
            @PathVariable(name = "idPlanEstudioDetalle") final Long idPlanEstudioDetalle,
            @RequestBody @Valid final PlanEstudioDetalleDTO planEstudioDetalleDTO) {
        planEstudioDetalleService.update(idPlanEstudioDetalle, planEstudioDetalleDTO);
        return ResponseEntity.ok(idPlanEstudioDetalle);
    }

    @DeleteMapping("/{idPlanEstudioDetalle}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePlanEstudioDetalle(
            @PathVariable(name = "idPlanEstudioDetalle") final Long idPlanEstudioDetalle) {
        final ReferencedWarning referencedWarning = planEstudioDetalleService.getReferencedWarning(idPlanEstudioDetalle);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        planEstudioDetalleService.delete(idPlanEstudioDetalle);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nivelValues")
    public ResponseEntity<Map<Long, String>> getNivelValues() {
        return ResponseEntity.ok(nivelRepository.findAll(Sort.by("idNivel"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Nivel::getIdNivel, Nivel::getNombreNivel)));
    }

    @GetMapping("/moduloValues")
    public ResponseEntity<Map<Long, String>> getModuloValues() {
        return ResponseEntity.ok(moduloRepository.findAll(Sort.by("idModulo"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Modulo::getIdModulo, Modulo::getNombreMod)));
    }

    @GetMapping("/planEstudioValues")
    public ResponseEntity<Map<Long, Long>> getPlanEstudioValues() {
        return ResponseEntity.ok(planEstudioRepository.findAll(Sort.by("idPlanEstudio"))
                .stream()
                .collect(CustomCollectors.toSortedMap(PlanEstudio::getIdPlanEstudio, PlanEstudio::getIdPlanEstudio)));
    }

}
