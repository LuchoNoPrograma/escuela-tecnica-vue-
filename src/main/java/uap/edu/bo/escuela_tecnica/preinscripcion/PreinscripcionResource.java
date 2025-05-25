package uap.edu.bo.escuela_tecnica.preinscripcion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/preinscripcions", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class PreinscripcionResource {

    private final PreinscripcionService preinscripcionService;

    public PreinscripcionResource(final PreinscripcionService preinscripcionService) {
        this.preinscripcionService = preinscripcionService;
    }

    @Operation(
            parameters = {
                    @Parameter(
                            name = "page",
                            in = ParameterIn.QUERY,
                            schema = @Schema(implementation = Integer.class)
                    ),
                    @Parameter(
                            name = "size",
                            in = ParameterIn.QUERY,
                            schema = @Schema(implementation = Integer.class)
                    ),
                    @Parameter(
                            name = "sort",
                            in = ParameterIn.QUERY,
                            schema = @Schema(implementation = String.class)
                    )
            }
    )
    @GetMapping
    public ResponseEntity<Page<PreinscripcionDTO>> getAllPreinscripcions(
            @RequestParam(name = "filter", required = false) final String filter,
            @Parameter(hidden = true) @SortDefault(sort = "idPreinscripcion") @PageableDefault(size = 20) final Pageable pageable) {
        return ResponseEntity.ok(preinscripcionService.findAll(filter, pageable));
    }

    @GetMapping("/{idPreinscripcion}")
    public ResponseEntity<PreinscripcionDTO> getPreinscripcion(
            @PathVariable(name = "idPreinscripcion") final Long idPreinscripcion) {
        return ResponseEntity.ok(preinscripcionService.get(idPreinscripcion));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPreinscripcion(
            @RequestBody @Valid final PreinscripcionDTO preinscripcionDTO) {
        final Long createdIdPreinscripcion = preinscripcionService.create(preinscripcionDTO);
        return new ResponseEntity<>(createdIdPreinscripcion, HttpStatus.CREATED);
    }

    @PutMapping("/{idPreinscripcion}")
    public ResponseEntity<Long> updatePreinscripcion(
            @PathVariable(name = "idPreinscripcion") final Long idPreinscripcion,
            @RequestBody @Valid final PreinscripcionDTO preinscripcionDTO) {
        preinscripcionService.update(idPreinscripcion, preinscripcionDTO);
        return ResponseEntity.ok(idPreinscripcion);
    }

    @DeleteMapping("/{idPreinscripcion}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePreinscripcion(
            @PathVariable(name = "idPreinscripcion") final Long idPreinscripcion) {
        preinscripcionService.delete(idPreinscripcion);
        return ResponseEntity.noContent().build();
    }

}
