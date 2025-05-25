package uap.edu.bo.escuela_tecnica.programa;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.categoria.CategoriaRepository;
import uap.edu.bo.escuela_tecnica.configuracion_costo.ConfiguracionCosto;
import uap.edu.bo.escuela_tecnica.configuracion_costo.ConfiguracionCostoRepository;
import uap.edu.bo.escuela_tecnica.grupo.Grupo;
import uap.edu.bo.escuela_tecnica.grupo.GrupoRepository;
import uap.edu.bo.escuela_tecnica.modalidad.ModalidadRepository;
import uap.edu.bo.escuela_tecnica.plan_estudio.PlanEstudio;
import uap.edu.bo.escuela_tecnica.plan_estudio.PlanEstudioRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;
import uap.edu.bo.escuela_tecnica.version.Version;
import uap.edu.bo.escuela_tecnica.version.VersionRepository;


@Service
public class ProgramaService {

    private final ProgramaRepository programaRepository;
    private final ModalidadRepository modalidadRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProgramaMapper programaMapper;
    private final ConfiguracionCostoRepository configuracionCostoRepository;
    private final GrupoRepository grupoRepository;
    private final PlanEstudioRepository planEstudioRepository;
    private final VersionRepository versionRepository;

    public ProgramaService(final ProgramaRepository programaRepository,
            final ModalidadRepository modalidadRepository,
            final CategoriaRepository categoriaRepository, final ProgramaMapper programaMapper,
            final ConfiguracionCostoRepository configuracionCostoRepository,
            final GrupoRepository grupoRepository,
            final PlanEstudioRepository planEstudioRepository,
            final VersionRepository versionRepository) {
        this.programaRepository = programaRepository;
        this.modalidadRepository = modalidadRepository;
        this.categoriaRepository = categoriaRepository;
        this.programaMapper = programaMapper;
        this.configuracionCostoRepository = configuracionCostoRepository;
        this.grupoRepository = grupoRepository;
        this.planEstudioRepository = planEstudioRepository;
        this.versionRepository = versionRepository;
    }

    public List<ProgramaDTO> findAll() {
        final List<Programa> programas = programaRepository.findAll(Sort.by("idPrograma"));
        return programas.stream()
                .map(programa -> programaMapper.updateProgramaDTO(programa, new ProgramaDTO()))
                .toList();
    }

    public ProgramaDTO get(final Long idPrograma) {
        return programaRepository.findById(idPrograma)
                .map(programa -> programaMapper.updateProgramaDTO(programa, new ProgramaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ProgramaDTO programaDTO) {
        final Programa programa = new Programa();
        programaMapper.updatePrograma(programaDTO, programa, modalidadRepository, categoriaRepository);
        return programaRepository.save(programa).getIdPrograma();
    }

    public void update(final Long idPrograma, final ProgramaDTO programaDTO) {
        final Programa programa = programaRepository.findById(idPrograma)
                .orElseThrow(NotFoundException::new);
        programaMapper.updatePrograma(programaDTO, programa, modalidadRepository, categoriaRepository);
        programaRepository.save(programa);
    }

    public void delete(final Long idPrograma) {
        programaRepository.deleteById(idPrograma);
    }

    public ReferencedWarning getReferencedWarning(final Long idPrograma) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Programa programa = programaRepository.findById(idPrograma)
                .orElseThrow(NotFoundException::new);
        final ConfiguracionCosto programaConfiguracionCosto = configuracionCostoRepository.findFirstByPrograma(programa);
        if (programaConfiguracionCosto != null) {
            referencedWarning.setKey("programa.configuracionCosto.programa.referenced");
            referencedWarning.addParam(programaConfiguracionCosto.getIdConfiguracionCosto());
            return referencedWarning;
        }
        final Grupo programaGrupo = grupoRepository.findFirstByPrograma(programa);
        if (programaGrupo != null) {
            referencedWarning.setKey("programa.grupo.programa.referenced");
            referencedWarning.addParam(programaGrupo.getIdGrupo());
            return referencedWarning;
        }
        final PlanEstudio programaPlanEstudio = planEstudioRepository.findFirstByPrograma(programa);
        if (programaPlanEstudio != null) {
            referencedWarning.setKey("programa.planEstudio.programa.referenced");
            referencedWarning.addParam(programaPlanEstudio.getIdPlanEstudio());
            return referencedWarning;
        }
        final Version programaVersion = versionRepository.findFirstByPrograma(programa);
        if (programaVersion != null) {
            referencedWarning.setKey("programa.version.programa.referenced");
            referencedWarning.addParam(programaVersion.getIdVersion());
            return referencedWarning;
        }
        return null;
    }

}
