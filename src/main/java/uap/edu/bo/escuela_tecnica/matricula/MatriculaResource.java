package uap.edu.bo.escuela_tecnica.matricula;

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
import uap.edu.bo.escuela_tecnica.grupo.Grupo;
import uap.edu.bo.escuela_tecnica.grupo.GrupoRepository;
import uap.edu.bo.escuela_tecnica.persona.Persona;
import uap.edu.bo.escuela_tecnica.persona.PersonaRepository;
import uap.edu.bo.escuela_tecnica.util.CustomCollectors;
import uap.edu.bo.escuela_tecnica.util.ReferencedException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/matriculas", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class MatriculaResource {

    private final MatriculaService matriculaService;
    private final GrupoRepository grupoRepository;
    private final PersonaRepository personaRepository;

    public MatriculaResource(final MatriculaService matriculaService,
            final GrupoRepository grupoRepository, final PersonaRepository personaRepository) {
        this.matriculaService = matriculaService;
        this.grupoRepository = grupoRepository;
        this.personaRepository = personaRepository;
    }

    @GetMapping
    public ResponseEntity<List<MatriculaDTO>> getAllMatriculas() {
        return ResponseEntity.ok(matriculaService.findAll());
    }

    @GetMapping("/{codMatricula}")
    public ResponseEntity<MatriculaDTO> getMatricula(
            @PathVariable(name = "codMatricula") final Long codMatricula) {
        return ResponseEntity.ok(matriculaService.get(codMatricula));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createMatricula(
            @RequestBody @Valid final MatriculaDTO matriculaDTO) {
        final Long createdCodMatricula = matriculaService.create(matriculaDTO);
        return new ResponseEntity<>(createdCodMatricula, HttpStatus.CREATED);
    }

    @PutMapping("/{codMatricula}")
    public ResponseEntity<Long> updateMatricula(
            @PathVariable(name = "codMatricula") final Long codMatricula,
            @RequestBody @Valid final MatriculaDTO matriculaDTO) {
        matriculaService.update(codMatricula, matriculaDTO);
        return ResponseEntity.ok(codMatricula);
    }

    @DeleteMapping("/{codMatricula}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMatricula(
            @PathVariable(name = "codMatricula") final Long codMatricula) {
        final ReferencedWarning referencedWarning = matriculaService.getReferencedWarning(codMatricula);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        matriculaService.delete(codMatricula);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/grupoValues")
    public ResponseEntity<Map<Long, String>> getGrupoValues() {
        return ResponseEntity.ok(grupoRepository.findAll(Sort.by("idGrupo"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Grupo::getIdGrupo, Grupo::getNombreGrupo)));
    }

    @GetMapping("/personaValues")
    public ResponseEntity<Map<Long, String>> getPersonaValues() {
        return ResponseEntity.ok(personaRepository.findAll(Sort.by("idPersona"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Persona::getIdPersona, Persona::getNombre)));
    }

}
