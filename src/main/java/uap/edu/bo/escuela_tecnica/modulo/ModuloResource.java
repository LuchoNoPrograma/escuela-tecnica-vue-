package uap.edu.bo.escuela_tecnica.modulo;

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
@RequestMapping(value = "/api/modulos", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class ModuloResource {

    private final ModuloService moduloService;

    public ModuloResource(final ModuloService moduloService) {
        this.moduloService = moduloService;
    }

    @GetMapping
    public ResponseEntity<List<ModuloDTO>> getAllModulos() {
        return ResponseEntity.ok(moduloService.findAll());
    }

    @GetMapping("/{idModulo}")
    public ResponseEntity<ModuloDTO> getModulo(
            @PathVariable(name = "idModulo") final Long idModulo) {
        return ResponseEntity.ok(moduloService.get(idModulo));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createModulo(@RequestBody @Valid final ModuloDTO moduloDTO) {
        final Long createdIdModulo = moduloService.create(moduloDTO);
        return new ResponseEntity<>(createdIdModulo, HttpStatus.CREATED);
    }

    @PutMapping("/{idModulo}")
    public ResponseEntity<Long> updateModulo(@PathVariable(name = "idModulo") final Long idModulo,
            @RequestBody @Valid final ModuloDTO moduloDTO) {
        moduloService.update(idModulo, moduloDTO);
        return ResponseEntity.ok(idModulo);
    }

    @DeleteMapping("/{idModulo}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteModulo(@PathVariable(name = "idModulo") final Long idModulo) {
        final ReferencedWarning referencedWarning = moduloService.getReferencedWarning(idModulo);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        moduloService.delete(idModulo);
        return ResponseEntity.noContent().build();
    }

}
