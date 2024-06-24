package rca.templates.valens.v1.services;
import rca.templates.valens.v1.dtos.requests.LoginDTO;
import rca.templates.valens.v1.dtos.response.LoginResponseDTO;

public interface IAuthService {

    public LoginResponseDTO login(LoginDTO dto);

}
