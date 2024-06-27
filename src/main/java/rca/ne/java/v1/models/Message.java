package rca.ne.java.v1.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "messagee")
    private String message;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
