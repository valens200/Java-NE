package rca.templates.valens.v1.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCandidateDTO {
    private String firstName;
    private String lastName;
    private String nationalId;
    private String phoneNumber;
    private String email;
    private String userName;
    private String password;
}
