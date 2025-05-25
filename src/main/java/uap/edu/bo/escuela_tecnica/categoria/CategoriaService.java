package uap.edu.bo.escuela_tecnica.categoria;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.programa.Programa;
import uap.edu.bo.escuela_tecnica.programa.ProgramaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;
    private final ProgramaRepository programaRepository;

    public CategoriaService(final CategoriaRepository categoriaRepository,
            final CategoriaMapper categoriaMapper, final ProgramaRepository programaRepository) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
        this.programaRepository = programaRepository;
    }

    public List<CategoriaDTO> findAll() {
        final List<Categoria> categorias = categoriaRepository.findAll(Sort.by("idCategoria"));
        return categorias.stream()
                .map(categoria -> categoriaMapper.updateCategoriaDTO(categoria, new CategoriaDTO()))
                .toList();
    }

    public CategoriaDTO get(final Long idCategoria) {
        return categoriaRepository.findById(idCategoria)
                .map(categoria -> categoriaMapper.updateCategoriaDTO(categoria, new CategoriaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CategoriaDTO categoriaDTO) {
        final Categoria categoria = new Categoria();
        categoriaMapper.updateCategoria(categoriaDTO, categoria);
        return categoriaRepository.save(categoria).getIdCategoria();
    }

    public void update(final Long idCategoria, final CategoriaDTO categoriaDTO) {
        final Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(NotFoundException::new);
        categoriaMapper.updateCategoria(categoriaDTO, categoria);
        categoriaRepository.save(categoria);
    }

    public void delete(final Long idCategoria) {
        categoriaRepository.deleteById(idCategoria);
    }

    public ReferencedWarning getReferencedWarning(final Long idCategoria) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(NotFoundException::new);
        final Programa categoriaPrograma = programaRepository.findFirstByCategoria(categoria);
        if (categoriaPrograma != null) {
            referencedWarning.setKey("categoria.programa.categoria.referenced");
            referencedWarning.addParam(categoriaPrograma.getIdPrograma());
            return referencedWarning;
        }
        return null;
    }

}
