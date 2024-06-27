package rca.ne.java.v1.models;

import lombok.Getter;
import lombok.Setter;
import rca.ne.java.v1.audits.InitiatorAudit;
import rca.ne.java.v1.models.enums.EBankingType;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Banking extends TransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    private double amount;
    @Enumerated(EnumType.STRING)
    private EBankingType bankingType;

}
