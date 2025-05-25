package uap.edu.bo.escuela_tecnica.persona;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;
import uap.edu.bo.escuela_tecnica.auditoria.Auditoria;


@Entity
@Getter
@Setter
public class Persona extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersona;

    @Column(nullable = false, length = 35)
    private String nombre;

    @Column(nullable = false, length = 55)
    private String apPaterno;

    @Column(length = 55)
    private String apMaterno;

    @Column(nullable = false, length = 20)
    private String ci;

    @Column(nullable = false, length = 20)
    private String nroCelular;

    @Column(length = 55)
    private String correo;

}
