package uap.edu.bo.escuela_tecnica.configuracion_costo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import uap.edu.bo.escuela_tecnica.auditoria.Auditoria;
import uap.edu.bo.escuela_tecnica.programa.Programa;


@Entity
@Getter
@Setter
public class ConfiguracionCosto extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConfiguracionCosto;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal montoConfig;

    @Column(nullable = false)
    private LocalDate fecInicioVig;

    @Column
    private LocalDate fecFinVig;

    @Column(nullable = false)
    private Boolean estConfig;

    @Column(nullable = false, length = 55)
    private String concepto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_programa", nullable = false)
    private Programa programa;

}
