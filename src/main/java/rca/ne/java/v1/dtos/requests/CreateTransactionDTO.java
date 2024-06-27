package rca.ne.java.v1.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateTransactionDTO {
    private double amount;
    private UUID destination_id;
}
