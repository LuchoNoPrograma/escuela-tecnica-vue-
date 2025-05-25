package uap.edu.bo.escuela_tecnica.administrativo;

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
@RequestMapping(value = "/api/administrativos", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class AdministrativoResource {

    private final AdministrativoService administrativoService;
    private final PersonaRepository personaRepository;

    public AdministrativoResource(final AdministrativoService administrativoService,
            final PersonaRepository personaRepository) {
        this.administrativoService = administrativoService;
        this.personaRepository = personaRepository;
    }

    @GetMapping
    public ResponseEntity<List<AdministrativoDTO>> getAllAdministrativos() {
        return ResponseEntity.ok(administrativoService.findAll());
    }

    @GetMapping("/{idAdministrativo}")
    public ResponseEntity<AdministrativoDTO> getAdministrativo(
            @PathVariable(name = "idAdministrativo") final Long idAdministrativo) {
        return ResponseEntity.ok(administrativoService.get(idAdministrativo));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createAdministrativo(
            @RequestBody @Valid final AdministrativoDTO administrativoDTO) {
        final Long createdIdAdministrativo = administrativoService.create(administrativoDTO);
        return new ResponseEntity<>(createdIdAdministrativo, HttpStatus.CREATED);
    }

    @PutMapping("/{idAdministrativo}")
    public ResponseEntity<Long> updateAdministrativo(
            @PathVariable(name = "idAdministrativo") final Long idAdministrativo,
            @RequestBody @Valid final AdministrativoDTO administrativoDTO) {
        administrativoService.update(idAdministrativo, administrativoDTO);
        return ResponseEntity.ok(idAdministrativo);
    }

    @DeleteMapping("/{idAdministrativo}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAdministrativo(
            @PathVariable(name = "idAdministrativo") final Long idAdministrativo) {
        final ReferencedWarning referencedWarning = administrativoService.getReferencedWarning(idAdministrativo);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        administrativoService.delete(idAdministrativo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/personaValues")
    public ResponseEntity<Map<Long, String>> getPersonaValues() {
        return ResponseEntity.ok(personaRepository.findAll(Sort.by("idPersona"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Persona::getIdPersona, Persona::getNombre)));
    }

}
