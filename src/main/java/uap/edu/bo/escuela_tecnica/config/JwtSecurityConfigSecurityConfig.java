package uap.edu.bo.escuela_tecnica.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.wimdeblauwe.errorhandlingspringbootstarter.UnauthorizedEntryPoint;
import io.github.wimdeblauwe.errorhandlingspringbootstarter.mapper.ErrorCodeMapper;
import io.github.wimdeblauwe.errorhandlingspringbootstarter.mapper.ErrorMessageMapper;
import io.github.wimdeblauwe.errorhandlingspringbootstarter.mapper.HttpStatusMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import uap.edu.bo.escuela_tecnica.security.JwtSecurityConfigTokenService;
import uap.edu.bo.escuela_tecnica.security.JwtSecurityConfigUserDetailsService;
import uap.edu.bo.escuela_tecnica.security.UserRoles;


@Configuration
public class JwtSecurityConfigSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // creates hashes with {bcrypt} prefix
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    public JwtRequestFilter jwtRequestFilter(
            final JwtSecurityConfigUserDetailsService jwtSecurityConfigUserDetailsService,
            final JwtSecurityConfigTokenService jwtSecurityConfigTokenService) {
        return new JwtRequestFilter(jwtSecurityConfigUserDetailsService, jwtSecurityConfigTokenService);
    }

    @Bean
    public UnauthorizedEntryPoint unauthorizedEntryPoint(final HttpStatusMapper httpStatusMapper,
            final ErrorCodeMapper errorCodeMapper, final ErrorMessageMapper errorMessageMapper,
            final ObjectMapper objectMapper) {
        return new UnauthorizedEntryPoint(httpStatusMapper, errorCodeMapper, errorMessageMapper, objectMapper);
    }

    @Bean
    public SecurityFilterChain jwtSecurityConfigFilterChain(final HttpSecurity http,
            final UnauthorizedEntryPoint unauthorizedEntryPoint,
            final JwtSecurityConfigUserDetailsService jwtSecurityConfigUserDetailsService,
            final JwtSecurityConfigTokenService jwtSecurityConfigTokenService) throws Exception {
        return http.cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(
                        new AntPathRequestMatcher("/api/calificacions/**", null),
                        new AntPathRequestMatcher("/api/calificacions/*", "GET")
                    ).hasAnyAuthority(UserRoles.ADMINISTRADOR_SISTEMAS, UserRoles.ADMINISTRATIVO, UserRoles.ESTUDIANTE)
                    .requestMatchers("/api/cronogramaModulos/**").hasAnyAuthority(UserRoles.ADMINISTRADOR_SISTEMAS, UserRoles.ADMINISTRATIVO, UserRoles.DOCENTE)
                    .requestMatchers("/api/planEstudios/**", "/api/monografias/**", "/api/grupos/**", "/api/categorias/**", "/api/programacions/**", "/api/certificados/**", "/api/administrativos/**", "/api/planPagos/**", "/api/matriculas/**", "/api/comprobantePagos/**", "/api/docentes/**", "/api/nivels/**", "/api/criterioEvals/**", "/api/planEstudioDetalles/**", "/api/configuracionCostos/**", "/api/personas/**", "/api/detallePagos/**", "/api/titulacions/**", "/api/observacionMonografias/**", "/api/revisionMonografias/**", "/api/modulos/**", "/api/modalidads/**", "/api/programas/**").hasAnyAuthority(UserRoles.ADMINISTRADOR_SISTEMAS, UserRoles.ADMINISTRATIVO)
                    .requestMatchers("/api/**").hasAuthority(UserRoles.ADMINISTRADOR_SISTEMAS)
                    .anyRequest().permitAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedEntryPoint))
                .addFilterBefore(jwtRequestFilter(jwtSecurityConfigUserDetailsService, jwtSecurityConfigTokenService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
