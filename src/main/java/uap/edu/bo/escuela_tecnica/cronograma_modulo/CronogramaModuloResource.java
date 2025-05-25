package uap.edu.bo.escuela_tecnica.cronograma_modulo;

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
import uap.edu.bo.escuela_tecnica.docente.Docente;
import uap.edu.bo.escuela_tecnica.docente.DocenteRepository;
import uap.edu.bo.escuela_tecnica.grupo.Grupo;
import uap.edu.bo.escuela_tecnica.grupo.GrupoRepository;
import uap.edu.bo.escuela_tecnica.plan_estudio_detalle.PlanEstudioDetalle;
import uap.edu.bo.escuela_tecnica.plan_estudio_detalle.PlanEstudioDetalleRepository;
import uap.edu.bo.escuela_tecnica.util.CustomCollectors;
import uap.edu.bo.escuela_tecnica.util.ReferencedException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/cronogramaModulos", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class CronogramaModuloResource {

    private final CronogramaModuloService cronogramaModuloService;
    private final PlanEstudioDetalleRepository planEstudioDetalleRepository;
    private final GrupoRepository grupoRepository;
    private final DocenteRepository docenteRepository;

    public CronogramaModuloResource(final CronogramaModuloService cronogramaModuloService,
            final PlanEstudioDetalleRepository planEstudioDetalleRepository,
            final GrupoRepository grupoRepository, final DocenteRepository docenteRepository) {
        this.cronogramaModuloService = cronogramaModuloService;
        this.planEstudioDetalleRepository = planEstudioDetalleRepository;
        this.grupoRepository = grupoRepository;
        this.docenteRepository = docenteRepository;
    }

    @GetMapping
    public ResponseEntity<List<CronogramaModuloDTO>> getAllCronogramaModulos() {
        return ResponseEntity.ok(cronogramaModuloService.findAll());
    }

    @GetMapping("/{idCronogramaMod}")
    public ResponseEntity<CronogramaModuloDTO> getCronogramaModulo(
            @PathVariable(name = "idCronogramaMod") final Long idCronogramaMod) {
        return ResponseEntity.ok(cronogramaModuloService.get(idCronogramaMod));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCronogramaModulo(
            @RequestBody @Valid final CronogramaModuloDTO cronogramaModuloDTO) {
        final Long createdIdCronogramaMod = cronogramaModuloService.create(cronogramaModuloDTO);
        return new ResponseEntity<>(createdIdCronogramaMod, HttpStatus.CREATED);
    }

    @PutMapping("/{idCronogramaMod}")
    public ResponseEntity<Long> updateCronogramaModulo(
            @PathVariable(name = "idCronogramaMod") final Long idCronogramaMod,
            @RequestBody @Valid final CronogramaModuloDTO cronogramaModuloDTO) {
        cronogramaModuloService.update(idCronogramaMod, cronogramaModuloDTO);
        return ResponseEntity.ok(idCronogramaMod);
    }

    @DeleteMapping("/{idCronogramaMod}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCronogramaModulo(
            @PathVariable(name = "idCronogramaMod") final Long idCronogramaMod) {
        final ReferencedWarning referencedWarning = cronogramaModuloService.getReferencedWarning(idCronogramaMod);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        cronogramaModuloService.delete(idCronogramaMod);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/planEstudioDetalleValues")
    public ResponseEntity<Map<Long, String>> getPlanEstudioDetalleValues() {
        return ResponseEntity.ok(planEstudioDetalleRepository.findAll(Sort.by("idPlanEstudioDetalle"))
                .stream()
                .collect(CustomCollectors.toSortedMap(PlanEstudioDetalle::getIdPlanEstudioDetalle, PlanEstudioDetalle::getSigla)));
    }

    @GetMapping("/grupoValues")
    public ResponseEntity<Map<Long, String>> getGrupoValues() {
        return ResponseEntity.ok(grupoRepository.findAll(Sort.by("idGrupo"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Grupo::getIdGrupo, Grupo::getNombreGrupo)));
    }

    @GetMapping("/docenteValues")
    public ResponseEntity<Map<Long, Long>> getDocenteValues() {
        return ResponseEntity.ok(docenteRepository.findAll(Sort.by("idDocente"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Docente::getIdDocente, Docente::getIdDocente)));
    }

}
