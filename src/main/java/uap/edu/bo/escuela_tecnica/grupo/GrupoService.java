package uap.edu.bo.escuela_tecnica.grupo;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.cronograma_modulo.CronogramaModulo;
import uap.edu.bo.escuela_tecnica.cronograma_modulo.CronogramaModuloRepository;
import uap.edu.bo.escuela_tecnica.matricula.Matricula;
import uap.edu.bo.escuela_tecnica.matricula.MatriculaRepository;
import uap.edu.bo.escuela_tecnica.programa.ProgramaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;
import uap.edu.bo.escuela_tecnica.version.VersionRepository;


@Service
public class GrupoService {

    private final GrupoRepository grupoRepository;
    private final VersionRepository versionRepository;
    private final ProgramaRepository programaRepository;
    private final GrupoMapper grupoMapper;
    private final CronogramaModuloRepository cronogramaModuloRepository;
    private final MatriculaRepository matriculaRepository;

    public GrupoService(final GrupoRepository grupoRepository,
            final VersionRepository versionRepository, final ProgramaRepository programaRepository,
            final GrupoMapper grupoMapper,
            final CronogramaModuloRepository cronogramaModuloRepository,
            final MatriculaRepository matriculaRepository) {
        this.grupoRepository = grupoRepository;
        this.versionRepository = versionRepository;
        this.programaRepository = programaRepository;
        this.grupoMapper = grupoMapper;
        this.cronogramaModuloRepository = cronogramaModuloRepository;
        this.matriculaRepository = matriculaRepository;
    }

    public List<GrupoDTO> findAll() {
        final List<Grupo> grupoes = grupoRepository.findAll(Sort.by("idGrupo"));
        return grupoes.stream()
                .map(grupo -> grupoMapper.updateGrupoDTO(grupo, new GrupoDTO()))
                .toList();
    }

    public GrupoDTO get(final Long idGrupo) {
        return grupoRepository.findById(idGrupo)
                .map(grupo -> grupoMapper.updateGrupoDTO(grupo, new GrupoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final GrupoDTO grupoDTO) {
        final Grupo grupo = new Grupo();
        grupoMapper.updateGrupo(grupoDTO, grupo, versionRepository, programaRepository);
        return grupoRepository.save(grupo).getIdGrupo();
    }

    public void update(final Long idGrupo, final GrupoDTO grupoDTO) {
        final Grupo grupo = grupoRepository.findById(idGrupo)
                .orElseThrow(NotFoundException::new);
        grupoMapper.updateGrupo(grupoDTO, grupo, versionRepository, programaRepository);
        grupoRepository.save(grupo);
    }

    public void delete(final Long idGrupo) {
        grupoRepository.deleteById(idGrupo);
    }

    public ReferencedWarning getReferencedWarning(final Long idGrupo) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Grupo grupo = grupoRepository.findById(idGrupo)
                .orElseThrow(NotFoundException::new);
        final CronogramaModulo grupoCronogramaModulo = cronogramaModuloRepository.findFirstByGrupo(grupo);
        if (grupoCronogramaModulo != null) {
            referencedWarning.setKey("grupo.cronogramaModulo.grupo.referenced");
            referencedWarning.addParam(grupoCronogramaModulo.getIdCronogramaMod());
            return referencedWarning;
        }
        final Matricula grupoMatricula = matriculaRepository.findFirstByGrupo(grupo);
        if (grupoMatricula != null) {
            referencedWarning.setKey("grupo.matricula.grupo.referenced");
            referencedWarning.addParam(grupoMatricula.getCodMatricula());
            return referencedWarning;
        }
        return null;
    }

}
