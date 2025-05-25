package uap.edu.bo.escuela_tecnica.programacion;

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
import uap.edu.bo.escuela_tecnica.matricula.Matricula;
import uap.edu.bo.escuela_tecnica.matricula.MatriculaRepository;
import uap.edu.bo.escuela_tecnica.util.CustomCollectors;
import uap.edu.bo.escuela_tecnica.util.ReferencedException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/programacions", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class ProgramacionResource {

    private final ProgramacionService programacionService;
    private final CronogramaModuloRepository cronogramaModuloRepository;
    private final MatriculaRepository matriculaRepository;

    public ProgramacionResource(final ProgramacionService programacionService,
            final CronogramaModuloRepository cronogramaModuloRepository,
            final MatriculaRepository matriculaRepository) {
        this.programacionService = programacionService;
        this.cronogramaModuloRepository = cronogramaModuloRepository;
        this.matriculaRepository = matriculaRepository;
    }

    @GetMapping
    public ResponseEntity<List<ProgramacionDTO>> getAllProgramacions() {
        return ResponseEntity.ok(programacionService.findAll());
    }

    @GetMapping("/{idProgramacion}")
    public ResponseEntity<ProgramacionDTO> getProgramacion(
            @PathVariable(name = "idProgramacion") final Long idProgramacion) {
        return ResponseEntity.ok(programacionService.get(idProgramacion));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createProgramacion(
            @RequestBody @Valid final ProgramacionDTO programacionDTO) {
        final Long createdIdProgramacion = programacionService.create(programacionDTO);
        return new ResponseEntity<>(createdIdProgramacion, HttpStatus.CREATED);
    }

    @PutMapping("/{idProgramacion}")
    public ResponseEntity<Long> updateProgramacion(
            @PathVariable(name = "idProgramacion") final Long idProgramacion,
            @RequestBody @Valid final ProgramacionDTO programacionDTO) {
        programacionService.update(idProgramacion, programacionDTO);
        return ResponseEntity.ok(idProgramacion);
    }

    @DeleteMapping("/{idProgramacion}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteProgramacion(
            @PathVariable(name = "idProgramacion") final Long idProgramacion) {
        final ReferencedWarning referencedWarning = programacionService.getReferencedWarning(idProgramacion);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        programacionService.delete(idProgramacion);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cronogramaModuloValues")
    public ResponseEntity<Map<Long, String>> getCronogramaModuloValues() {
        return ResponseEntity.ok(cronogramaModuloRepository.findAll(Sort.by("idCronogramaMod"))
                .stream()
                .collect(CustomCollectors.toSortedMap(CronogramaModulo::getIdCronogramaMod, CronogramaModulo::getEstado)));
    }

    @GetMapping("/matriculaValues")
    public ResponseEntity<Map<Long, String>> getMatriculaValues() {
        return ResponseEntity.ok(matriculaRepository.findAll(Sort.by("codMatricula"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Matricula::getCodMatricula, Matricula::getEstMatricula)));
    }

}
