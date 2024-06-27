package rca.ne.java.v1.dtos.response;
import rca.ne.java.v1.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponseDTO {

    private String token;
    private User user;
}
