package uap.edu.bo.escuela_tecnica.comprobante_pago;

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
import uap.edu.bo.escuela_tecnica.detalle_pago.DetallePago;
import uap.edu.bo.escuela_tecnica.detalle_pago.DetallePagoRepository;
import uap.edu.bo.escuela_tecnica.util.CustomCollectors;


@RestController
@RequestMapping(value = "/api/comprobantePagos", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class ComprobantePagoResource {

    private final ComprobantePagoService comprobantePagoService;
    private final DetallePagoRepository detallePagoRepository;

    public ComprobantePagoResource(final ComprobantePagoService comprobantePagoService,
            final DetallePagoRepository detallePagoRepository) {
        this.comprobantePagoService = comprobantePagoService;
        this.detallePagoRepository = detallePagoRepository;
    }

    @GetMapping
    public ResponseEntity<List<ComprobantePagoDTO>> getAllComprobantePagos() {
        return ResponseEntity.ok(comprobantePagoService.findAll());
    }

    @GetMapping("/{idComprobante}")
    public ResponseEntity<ComprobantePagoDTO> getComprobantePago(
            @PathVariable(name = "idComprobante") final Long idComprobante) {
        return ResponseEntity.ok(comprobantePagoService.get(idComprobante));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createComprobantePago(
            @RequestBody @Valid final ComprobantePagoDTO comprobantePagoDTO) {
        final Long createdIdComprobante = comprobantePagoService.create(comprobantePagoDTO);
        return new ResponseEntity<>(createdIdComprobante, HttpStatus.CREATED);
    }

    @PutMapping("/{idComprobante}")
    public ResponseEntity<Long> updateComprobantePago(
            @PathVariable(name = "idComprobante") final Long idComprobante,
            @RequestBody @Valid final ComprobantePagoDTO comprobantePagoDTO) {
        comprobantePagoService.update(idComprobante, comprobantePagoDTO);
        return ResponseEntity.ok(idComprobante);
    }

    @DeleteMapping("/{idComprobante}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteComprobantePago(
            @PathVariable(name = "idComprobante") final Long idComprobante) {
        comprobantePagoService.delete(idComprobante);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listaDetallesPagosValues")
    public ResponseEntity<Map<Long, Long>> getListaDetallesPagosValues() {
        return ResponseEntity.ok(detallePagoRepository.findAll(Sort.by("idDetallePago"))
                .stream()
                .collect(CustomCollectors.toSortedMap(DetallePago::getIdDetallePago, DetallePago::getIdDetallePago)));
    }

}
