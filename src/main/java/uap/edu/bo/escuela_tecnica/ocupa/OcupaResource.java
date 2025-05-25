package uap.edu.bo.escuela_tecnica.ocupa;

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
import uap.edu.bo.escuela_tecnica.rol.Rol;
import uap.edu.bo.escuela_tecnica.rol.RolRepository;
import uap.edu.bo.escuela_tecnica.usuario.Usuario;
import uap.edu.bo.escuela_tecnica.usuario.UsuarioRepository;
import uap.edu.bo.escuela_tecnica.util.CustomCollectors;


@RestController
@RequestMapping(value = "/api/ocupas", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class OcupaResource {

    private final OcupaService ocupaService;
    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;

    public OcupaResource(final OcupaService ocupaService, final RolRepository rolRepository,
            final UsuarioRepository usuarioRepository) {
        this.ocupaService = ocupaService;
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<List<OcupaDTO>> getAllOcupas() {
        return ResponseEntity.ok(ocupaService.findAll());
    }

    @GetMapping("/{estOcupa}")
    public ResponseEntity<OcupaDTO> getOcupa(
            @PathVariable(name = "estOcupa") final String estOcupa) {
        return ResponseEntity.ok(ocupaService.get(estOcupa));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createOcupa(@RequestBody @Valid final OcupaDTO ocupaDTO) {
        final String createdEstOcupa = ocupaService.create(ocupaDTO);
        return new ResponseEntity<>('"' + createdEstOcupa + '"', HttpStatus.CREATED);
    }

    @PutMapping("/{estOcupa}")
    public ResponseEntity<String> updateOcupa(
            @PathVariable(name = "estOcupa") final String estOcupa,
            @RequestBody @Valid final OcupaDTO ocupaDTO) {
        ocupaService.update(estOcupa, ocupaDTO);
        return ResponseEntity.ok('"' + estOcupa + '"');
    }

    @DeleteMapping("/{estOcupa}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteOcupa(
            @PathVariable(name = "estOcupa") final String estOcupa) {
        ocupaService.delete(estOcupa);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rolValues")
    public ResponseEntity<Map<Long, String>> getRolValues() {
        return ResponseEntity.ok(rolRepository.findAll(Sort.by("idPermiso"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Rol::getIdRol, Rol::getNombre)));
    }

    @GetMapping("/usuarioValues")
    public ResponseEntity<Map<Long, Long>> getUsuarioValues() {
        return ResponseEntity.ok(usuarioRepository.findAll(Sort.by("idUsuario"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getIdUsuario, Usuario::getIdUsuario)));
    }

}
