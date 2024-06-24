package rca.templates.valens.v1.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateVoteDTO {
    public UUID candidateId;
    public UUID voterId;
    public UUID positionId;
    public UUID votingSessionId;
}
