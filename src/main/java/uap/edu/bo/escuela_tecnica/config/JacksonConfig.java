package uap.edu.bo.escuela_tecnica.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;


@Configuration
@EnableSpringDataWebSupport(
        pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO
)
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder
                .featuresToDisable(
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                        DeserializationFeature.ACCEPT_FLOAT_AS_INT,
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
                );
    }

    @Bean
    public Hibernate6Module hibernate6Module() {
        Hibernate6Module module = new Hibernate6Module();
        // Opción: que Jackson OMITA silenciosamente los proxies lazy no inicializados.
        module.disable(Hibernate6Module.Feature.FORCE_LAZY_LOADING);
        // Si alguna vez quisieras forzar la carga de los lazy (no recomendado para APIs),
        // podrías habilitar esta línea en lugar de la anterior:
        // module.enable(Hibernate6Module.Feature.FORCE_LAZY_LOADING);

        return module;
    }
}
