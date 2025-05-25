package uap.edu.bo.escuela_tecnica.usuario;

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
import uap.edu.bo.escuela_tecnica.tarea.Tarea;
import uap.edu.bo.escuela_tecnica.tarea.TareaRepository;
import uap.edu.bo.escuela_tecnica.util.CustomCollectors;
import uap.edu.bo.escuela_tecnica.util.ReferencedException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class UsuarioResource {

    private final UsuarioService usuarioService;
    private final TareaRepository tareaRepository;
    private final PersonaRepository personaRepository;

    public UsuarioResource(final UsuarioService usuarioService,
            final TareaRepository tareaRepository, final PersonaRepository personaRepository) {
        this.usuarioService = usuarioService;
        this.tareaRepository = tareaRepository;
        this.personaRepository = personaRepository;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> getUsuario(
            @PathVariable(name = "idUsuario") final Long idUsuario) {
        return ResponseEntity.ok(usuarioService.get(idUsuario));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createUsuario(@RequestBody @Valid final UsuarioDTO usuarioDTO) {
        final Long createdIdUsuario = usuarioService.create(usuarioDTO);
        return new ResponseEntity<>(createdIdUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<Long> updateUsuario(
            @PathVariable(name = "idUsuario") final Long idUsuario,
            @RequestBody @Valid final UsuarioDTO usuarioDTO) {
        usuarioService.update(idUsuario, usuarioDTO);
        return ResponseEntity.ok(idUsuario);
    }

    @DeleteMapping("/{idUsuario}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUsuario(
            @PathVariable(name = "idUsuario") final Long idUsuario) {
        final ReferencedWarning referencedWarning = usuarioService.getReferencedWarning(idUsuario);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        usuarioService.delete(idUsuario);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listaTareasAsignadasValues")
    public ResponseEntity<Map<Long, String>> getListaTareasAsignadasValues() {
        return ResponseEntity.ok(tareaRepository.findAll(Sort.by("idTarea"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Tarea::getIdTarea, Tarea::getNombreTarea)));
    }

    @GetMapping("/personaValues")
    public ResponseEntity<Map<Long, String>> getPersonaValues() {
        return ResponseEntity.ok(personaRepository.findAll(Sort.by("idPersona"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Persona::getIdPersona, Persona::getNombre)));
    }

}
