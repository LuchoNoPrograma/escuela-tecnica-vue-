package uap.edu.bo.escuela_tecnica.docente;

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
import uap.edu.bo.escuela_tecnica.persona.Persona;
import uap.edu.bo.escuela_tecnica.persona.PersonaRepository;
import uap.edu.bo.escuela_tecnica.util.CustomCollectors;
import uap.edu.bo.escuela_tecnica.util.ReferencedException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/docentes", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class DocenteResource {

    private final DocenteService docenteService;
    private final PersonaRepository personaRepository;

    public DocenteResource(final DocenteService docenteService,
            final PersonaRepository personaRepository) {
        this.docenteService = docenteService;
        this.personaRepository = personaRepository;
    }

    @GetMapping
    public ResponseEntity<List<DocenteDTO>> getAllDocentes() {
        return ResponseEntity.ok(docenteService.findAll());
    }

    @GetMapping("/{idDocente}")
    public ResponseEntity<DocenteDTO> getDocente(
            @PathVariable(name = "idDocente") final Long idDocente) {
        return ResponseEntity.ok(docenteService.get(idDocente));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDocente(@RequestBody @Valid final DocenteDTO docenteDTO) {
        final Long createdIdDocente = docenteService.create(docenteDTO);
        return new ResponseEntity<>(createdIdDocente, HttpStatus.CREATED);
    }

    @PutMapping("/{idDocente}")
    public ResponseEntity<Long> updateDocente(
            @PathVariable(name = "idDocente") final Long idDocente,
            @RequestBody @Valid final DocenteDTO docenteDTO) {
        docenteService.update(idDocente, docenteDTO);
        return ResponseEntity.ok(idDocente);
    }

    @DeleteMapping("/{idDocente}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDocente(
            @PathVariable(name = "idDocente") final Long idDocente) {
        final ReferencedWarning referencedWarning = docenteService.getReferencedWarning(idDocente);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        docenteService.delete(idDocente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/personaValues")
    public ResponseEntity<Map<Long, String>> getPersonaValues() {
        return ResponseEntity.ok(personaRepository.findAll(Sort.by("idPersona"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Persona::getIdPersona, Persona::getNombre)));
    }

}
