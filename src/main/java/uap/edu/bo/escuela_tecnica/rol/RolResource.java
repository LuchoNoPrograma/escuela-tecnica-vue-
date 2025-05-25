package uap.edu.bo.escuela_tecnica.rol;

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
@RequestMapping(value = "/api/rols", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class RolResource {

    private final RolService rolService;

    public RolResource(final RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public ResponseEntity<List<RolDTO>> getAllRols() {
        return ResponseEntity.ok(rolService.findAll());
    }

    @GetMapping("/{idPermiso}")
    public ResponseEntity<RolDTO> getRol(@PathVariable(name = "idPermiso") final Long idPermiso) {
        return ResponseEntity.ok(rolService.get(idPermiso));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createRol(@RequestBody @Valid final RolDTO rolDTO) {
        final Long createdIdPermiso = rolService.create(rolDTO);
        return new ResponseEntity<>(createdIdPermiso, HttpStatus.CREATED);
    }

    @PutMapping("/{idPermiso}")
    public ResponseEntity<Long> updateRol(@PathVariable(name = "idPermiso") final Long idPermiso,
            @RequestBody @Valid final RolDTO rolDTO) {
        rolService.update(idPermiso, rolDTO);
        return ResponseEntity.ok(idPermiso);
    }

    @DeleteMapping("/{idPermiso}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRol(@PathVariable(name = "idPermiso") final Long idPermiso) {
        final ReferencedWarning referencedWarning = rolService.getReferencedWarning(idPermiso);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        rolService.delete(idPermiso);
        return ResponseEntity.noContent().build();
    }

}
