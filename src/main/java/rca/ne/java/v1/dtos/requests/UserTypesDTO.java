package rca.ne.java.v1.dtos.requests;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTypesDTO {
    private UUID user_id;
    private UUID role_id;
    private String role_name;
}
