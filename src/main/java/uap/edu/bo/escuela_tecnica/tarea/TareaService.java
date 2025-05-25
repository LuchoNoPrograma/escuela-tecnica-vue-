package uap.edu.bo.escuela_tecnica.tarea;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.rol.RolRepository;
import uap.edu.bo.escuela_tecnica.usuario.UsuarioRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Service
@Transactional
public class TareaService {

    private final TareaRepository tareaRepository;
    private final RolRepository rolRepository;
    private final TareaMapper tareaMapper;
    private final UsuarioRepository usuarioRepository;

    public TareaService(final TareaRepository tareaRepository, final RolRepository rolRepository,
            final TareaMapper tareaMapper, final UsuarioRepository usuarioRepository) {
        this.tareaRepository = tareaRepository;
        this.rolRepository = rolRepository;
        this.tareaMapper = tareaMapper;
        this.usuarioRepository = usuarioRepository;
    }

    public List<TareaDTO> findAll() {
        final List<Tarea> tareas = tareaRepository.findAll(Sort.by("idTarea"));
        return tareas.stream()
                .map(tarea -> tareaMapper.updateTareaDTO(tarea, new TareaDTO()))
                .toList();
    }

    public TareaDTO get(final Long idTarea) {
        return tareaRepository.findById(idTarea)
                .map(tarea -> tareaMapper.updateTareaDTO(tarea, new TareaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TareaDTO tareaDTO) {
        final Tarea tarea = new Tarea();
        tareaMapper.updateTarea(tareaDTO, tarea, rolRepository);
        return tareaRepository.save(tarea).getIdTarea();
    }

    public void update(final Long idTarea, final TareaDTO tareaDTO) {
        final Tarea tarea = tareaRepository.findById(idTarea)
                .orElseThrow(NotFoundException::new);
        tareaMapper.updateTarea(tareaDTO, tarea, rolRepository);
        tareaRepository.save(tarea);
    }

    public void delete(final Long idTarea) {
        final Tarea tarea = tareaRepository.findById(idTarea)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        usuarioRepository.findAllByListaTareasAsignadas(tarea)
                .forEach(usuario -> usuario.getListaTareasAsignadas().remove(tarea));
        tareaRepository.delete(tarea);
    }

}
