package rca.ne.java.v1.services;
import rca.ne.java.v1.dtos.requests.LoginDTO;
import rca.ne.java.v1.dtos.response.LoginResponseDTO;

public interface IAuthService {

    public LoginResponseDTO login(LoginDTO dto);

}
