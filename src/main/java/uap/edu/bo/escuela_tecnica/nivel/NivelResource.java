package uap.edu.bo.escuela_tecnica.nivel;

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
@RequestMapping(value = "/api/nivels", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class NivelResource {

    private final NivelService nivelService;

    public NivelResource(final NivelService nivelService) {
        this.nivelService = nivelService;
    }

    @GetMapping
    public ResponseEntity<List<NivelDTO>> getAllNivels() {
        return ResponseEntity.ok(nivelService.findAll());
    }

    @GetMapping("/{idNivel}")
    public ResponseEntity<NivelDTO> getNivel(@PathVariable(name = "idNivel") final Long idNivel) {
        return ResponseEntity.ok(nivelService.get(idNivel));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createNivel(@RequestBody @Valid final NivelDTO nivelDTO) {
        final Long createdIdNivel = nivelService.create(nivelDTO);
        return new ResponseEntity<>(createdIdNivel, HttpStatus.CREATED);
    }

    @PutMapping("/{idNivel}")
    public ResponseEntity<Long> updateNivel(@PathVariable(name = "idNivel") final Long idNivel,
            @RequestBody @Valid final NivelDTO nivelDTO) {
        nivelService.update(idNivel, nivelDTO);
        return ResponseEntity.ok(idNivel);
    }

    @DeleteMapping("/{idNivel}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteNivel(@PathVariable(name = "idNivel") final Long idNivel) {
        final ReferencedWarning referencedWarning = nivelService.getReferencedWarning(idNivel);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        nivelService.delete(idNivel);
        return ResponseEntity.noContent().build();
    }

}
