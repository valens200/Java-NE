package com.templates.valens.v1.services;
import com.templates.valens.v1.dtos.requests.LoginDTO;
import com.templates.valens.v1.dtos.response.LoginResponseDTO;

public interface IAuthService {

    public LoginResponseDTO login(LoginDTO dto);

}
