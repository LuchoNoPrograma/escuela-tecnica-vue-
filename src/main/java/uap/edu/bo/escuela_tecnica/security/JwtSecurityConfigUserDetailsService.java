package uap.edu.bo.escuela_tecnica.security;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.usuario.Usuario;
import uap.edu.bo.escuela_tecnica.usuario.UsuarioRepository;


@Service
@Slf4j
public class JwtSecurityConfigUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public JwtSecurityConfigUserDetailsService(final UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public JwtSecurityConfigUserDetails loadUserByUsername(final String username) {
        final Usuario usuario = usuarioRepository.findByNombreUsuarioIgnoreCase(username);
        if (usuario == null) {
            log.warn("Usuario no encontrado: {}", username);
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        final List<SimpleGrantedAuthority> authorities = usuario.getListaTareasAsignadas() == null ? List.of() :
                usuario.getListaTareasAsignadas()
                .stream()
                .map(tareaRef -> new SimpleGrantedAuthority(tareaRef.getNombreTarea()))
                .toList();
        return new JwtSecurityConfigUserDetails(usuario.getIdUsuario(), username, usuario.getContrasenaHash(), authorities);
    }

}
