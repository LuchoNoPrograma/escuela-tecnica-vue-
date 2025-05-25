package uap.edu.bo.escuela_tecnica.modalidad;

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
@RequestMapping(value = "/api/modalidads", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class ModalidadResource {

    private final ModalidadService modalidadService;

    public ModalidadResource(final ModalidadService modalidadService) {
        this.modalidadService = modalidadService;
    }

    @GetMapping
    public ResponseEntity<List<ModalidadDTO>> getAllModalidads() {
        return ResponseEntity.ok(modalidadService.findAll());
    }

    @GetMapping("/{idModalidad}")
    public ResponseEntity<ModalidadDTO> getModalidad(
            @PathVariable(name = "idModalidad") final Long idModalidad) {
        return ResponseEntity.ok(modalidadService.get(idModalidad));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createModalidad(
            @RequestBody @Valid final ModalidadDTO modalidadDTO) {
        final Long createdIdModalidad = modalidadService.create(modalidadDTO);
        return new ResponseEntity<>(createdIdModalidad, HttpStatus.CREATED);
    }

    @PutMapping("/{idModalidad}")
    public ResponseEntity<Long> updateModalidad(
            @PathVariable(name = "idModalidad") final Long idModalidad,
            @RequestBody @Valid final ModalidadDTO modalidadDTO) {
        modalidadService.update(idModalidad, modalidadDTO);
        return ResponseEntity.ok(idModalidad);
    }

    @DeleteMapping("/{idModalidad}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteModalidad(
            @PathVariable(name = "idModalidad") final Long idModalidad) {
        final ReferencedWarning referencedWarning = modalidadService.getReferencedWarning(idModalidad);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        modalidadService.delete(idModalidad);
        return ResponseEntity.noContent().build();
    }

}
