package rca.ne.java.v1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private double balance = 0.0;
    @OneToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private Customer owner;

    public Account(Customer customer) {
        this.owner = customer;
    }
}
