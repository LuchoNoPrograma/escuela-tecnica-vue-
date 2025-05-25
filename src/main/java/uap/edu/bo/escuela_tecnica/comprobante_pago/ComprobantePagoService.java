package uap.edu.bo.escuela_tecnica.comprobante_pago;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.detalle_pago.DetallePagoRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Service
@Transactional
public class ComprobantePagoService {

    private final ComprobantePagoRepository comprobantePagoRepository;
    private final DetallePagoRepository detallePagoRepository;
    private final ComprobantePagoMapper comprobantePagoMapper;

    public ComprobantePagoService(final ComprobantePagoRepository comprobantePagoRepository,
            final DetallePagoRepository detallePagoRepository,
            final ComprobantePagoMapper comprobantePagoMapper) {
        this.comprobantePagoRepository = comprobantePagoRepository;
        this.detallePagoRepository = detallePagoRepository;
        this.comprobantePagoMapper = comprobantePagoMapper;
    }

    public List<ComprobantePagoDTO> findAll() {
        final List<ComprobantePago> comprobantePagoes = comprobantePagoRepository.findAll(Sort.by("idComprobante"));
        return comprobantePagoes.stream()
                .map(comprobantePago -> comprobantePagoMapper.updateComprobantePagoDTO(comprobantePago, new ComprobantePagoDTO()))
                .toList();
    }

    public ComprobantePagoDTO get(final Long idComprobante) {
        return comprobantePagoRepository.findById(idComprobante)
                .map(comprobantePago -> comprobantePagoMapper.updateComprobantePagoDTO(comprobantePago, new ComprobantePagoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ComprobantePagoDTO comprobantePagoDTO) {
        final ComprobantePago comprobantePago = new ComprobantePago();
        comprobantePagoMapper.updateComprobantePago(comprobantePagoDTO, comprobantePago, detallePagoRepository);
        return comprobantePagoRepository.save(comprobantePago).getIdComprobante();
    }

    public void update(final Long idComprobante, final ComprobantePagoDTO comprobantePagoDTO) {
        final ComprobantePago comprobantePago = comprobantePagoRepository.findById(idComprobante)
                .orElseThrow(NotFoundException::new);
        comprobantePagoMapper.updateComprobantePago(comprobantePagoDTO, comprobantePago, detallePagoRepository);
        comprobantePagoRepository.save(comprobantePago);
    }

    public void delete(final Long idComprobante) {
        comprobantePagoRepository.deleteById(idComprobante);
    }

}
