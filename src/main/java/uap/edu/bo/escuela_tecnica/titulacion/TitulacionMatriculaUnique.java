package uap.edu.bo.escuela_tecnica.titulacion;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the codMatricula value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = TitulacionMatriculaUnique.TitulacionMatriculaUniqueValidator.class
)
public @interface TitulacionMatriculaUnique {

    String message() default "{Exists.titulacion.matricula}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class TitulacionMatriculaUniqueValidator implements ConstraintValidator<TitulacionMatriculaUnique, Long> {

        private final TitulacionService titulacionService;
        private final HttpServletRequest request;

        public TitulacionMatriculaUniqueValidator(final TitulacionService titulacionService,
                final HttpServletRequest request) {
            this.titulacionService = titulacionService;
            this.request = request;
        }

        @Override
        public boolean isValid(final Long value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("idTitulacion");
            if (currentId != null && value.equals(titulacionService.get(Long.parseLong(currentId)).getMatricula())) {
                // value hasn't changed
                return true;
            }
            return !titulacionService.matriculaExists(value);
        }

    }

}
