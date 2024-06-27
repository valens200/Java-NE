package rca.ne.java.v1.audits;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.UUID;
@Getter
@Setter
@MappedSuperclass
@JsonIgnoreProperties(value = {"createdBy", "updatedBy"}, allowGetters = true)
public abstract class InitiatorAudit extends TimeStampAudit {
    private static final Long serialVersionUID = 1L;

    @CreatedBy
    @Column(name = "created_by")
    @JsonIgnore
    private UUID createdBy;

    @LastModifiedBy
    @JsonIgnore
    @Column(name = "updated_by")
    private UUID updatedBy;
}
