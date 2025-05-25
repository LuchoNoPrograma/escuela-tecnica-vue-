package uap.edu.bo.escuela_tecnica.persona;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
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
import uap.edu.bo.escuela_tecnica.util.ReferencedException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/personas", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class PersonaResource {

    private final PersonaService personaService;

    public PersonaResource(final PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping
    public ResponseEntity<List<PersonaDTO>> getAllPersonas() {
        return ResponseEntity.ok(personaService.findAll());
    }

    @GetMapping("/{idPersona}")
    public ResponseEntity<PersonaDTO> getPersona(
            @PathVariable(name = "idPersona") final Long idPersona) {
        return ResponseEntity.ok(personaService.get(idPersona));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPersona(@RequestBody @Valid final PersonaDTO personaDTO) {
        final Long createdIdPersona = personaService.create(personaDTO);
        return new ResponseEntity<>(createdIdPersona, HttpStatus.CREATED);
    }

    @PutMapping("/{idPersona}")
    public ResponseEntity<Long> updatePersona(
            @PathVariable(name = "idPersona") final Long idPersona,
            @RequestBody @Valid final PersonaDTO personaDTO) {
        personaService.update(idPersona, personaDTO);
        return ResponseEntity.ok(idPersona);
    }

    @DeleteMapping("/{idPersona}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePersona(
            @PathVariable(name = "idPersona") final Long idPersona) {
        final ReferencedWarning referencedWarning = personaService.getReferencedWarning(idPersona);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        personaService.delete(idPersona);
        return ResponseEntity.noContent().build();
    }

}
