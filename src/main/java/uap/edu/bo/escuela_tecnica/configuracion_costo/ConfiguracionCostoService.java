package uap.edu.bo.escuela_tecnica.configuracion_costo;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.programa.ProgramaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Service
public class ConfiguracionCostoService {

    private final ConfiguracionCostoRepository configuracionCostoRepository;
    private final ProgramaRepository programaRepository;
    private final ConfiguracionCostoMapper configuracionCostoMapper;

    public ConfiguracionCostoService(
            final ConfiguracionCostoRepository configuracionCostoRepository,
            final ProgramaRepository programaRepository,
            final ConfiguracionCostoMapper configuracionCostoMapper) {
        this.configuracionCostoRepository = configuracionCostoRepository;
        this.programaRepository = programaRepository;
        this.configuracionCostoMapper = configuracionCostoMapper;
    }

    public List<ConfiguracionCostoDTO> findAll() {
        final List<ConfiguracionCosto> configuracionCostoes = configuracionCostoRepository.findAll(Sort.by("idConfiguracionCosto"));
        return configuracionCostoes.stream()
                .map(configuracionCosto -> configuracionCostoMapper.updateConfiguracionCostoDTO(configuracionCosto, new ConfiguracionCostoDTO()))
                .toList();
    }

    public ConfiguracionCostoDTO get(final Long idConfiguracionCosto) {
        return configuracionCostoRepository.findById(idConfiguracionCosto)
                .map(configuracionCosto -> configuracionCostoMapper.updateConfiguracionCostoDTO(configuracionCosto, new ConfiguracionCostoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ConfiguracionCostoDTO configuracionCostoDTO) {
        final ConfiguracionCosto configuracionCosto = new ConfiguracionCosto();
        configuracionCostoMapper.updateConfiguracionCosto(configuracionCostoDTO, configuracionCosto, programaRepository);
        return configuracionCostoRepository.save(configuracionCosto).getIdConfiguracionCosto();
    }

    public void update(final Long idConfiguracionCosto,
            final ConfiguracionCostoDTO configuracionCostoDTO) {
        final ConfiguracionCosto configuracionCosto = configuracionCostoRepository.findById(idConfiguracionCosto)
                .orElseThrow(NotFoundException::new);
        configuracionCostoMapper.updateConfiguracionCosto(configuracionCostoDTO, configuracionCosto, programaRepository);
        configuracionCostoRepository.save(configuracionCosto);
    }

    public void delete(final Long idConfiguracionCosto) {
        configuracionCostoRepository.deleteById(idConfiguracionCosto);
    }

}
