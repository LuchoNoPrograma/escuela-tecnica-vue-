package uap.edu.bo.escuela_tecnica.auditoria;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class Auditoria {

    @CreatedBy
    @Column(nullable = false)
    private Long idUsuReg;

    @LastModifiedBy
    @Column
    private Long idUsuMod;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime fecReg;

    @LastModifiedDate
    @Column
    private OffsetDateTime fecMod;

}
