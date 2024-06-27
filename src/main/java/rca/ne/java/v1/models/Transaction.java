package rca.ne.java.v1.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction extends TransactionDetail{
    @ManyToOne
    @JoinColumn(name = "destination_id")
    @JsonIgnore
    private Account destination;
}