package uap.edu.bo.escuela_tecnica.detalle_pago;

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
import uap.edu.bo.escuela_tecnica.plan_pago.PlanPago;
import uap.edu.bo.escuela_tecnica.plan_pago.PlanPagoRepository;
import uap.edu.bo.escuela_tecnica.util.CustomCollectors;


@RestController
@RequestMapping(value = "/api/detallePagos", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class DetallePagoResource {

    private final DetallePagoService detallePagoService;
    private final PlanPagoRepository planPagoRepository;

    public DetallePagoResource(final DetallePagoService detallePagoService,
            final PlanPagoRepository planPagoRepository) {
        this.detallePagoService = detallePagoService;
        this.planPagoRepository = planPagoRepository;
    }

    @GetMapping
    public ResponseEntity<List<DetallePagoDTO>> getAllDetallePagos() {
        return ResponseEntity.ok(detallePagoService.findAll());
    }

    @GetMapping("/{idDetallePago}")
    public ResponseEntity<DetallePagoDTO> getDetallePago(
            @PathVariable(name = "idDetallePago") final Long idDetallePago) {
        return ResponseEntity.ok(detallePagoService.get(idDetallePago));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDetallePago(
            @RequestBody @Valid final DetallePagoDTO detallePagoDTO) {
        final Long createdIdDetallePago = detallePagoService.create(detallePagoDTO);
        return new ResponseEntity<>(createdIdDetallePago, HttpStatus.CREATED);
    }

    @PutMapping("/{idDetallePago}")
    public ResponseEntity<Long> updateDetallePago(
            @PathVariable(name = "idDetallePago") final Long idDetallePago,
            @RequestBody @Valid final DetallePagoDTO detallePagoDTO) {
        detallePagoService.update(idDetallePago, detallePagoDTO);
        return ResponseEntity.ok(idDetallePago);
    }

    @DeleteMapping("/{idDetallePago}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDetallePago(
            @PathVariable(name = "idDetallePago") final Long idDetallePago) {
        detallePagoService.delete(idDetallePago);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/planPagoValues")
    public ResponseEntity<Map<Long, String>> getPlanPagoValues() {
        return ResponseEntity.ok(planPagoRepository.findAll(Sort.by("idPlanPago"))
                .stream()
                .collect(CustomCollectors.toSortedMap(PlanPago::getIdPlanPago, PlanPago::getEstPlanPago)));
    }

}
