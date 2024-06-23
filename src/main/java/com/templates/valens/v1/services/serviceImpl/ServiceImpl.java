package com.templates.valens.v1.services.serviceImpl;

import com.templates.valens.v1.dtos.response.LoginResponseDTO;
import com.templates.valens.v1.models.User;
import com.templates.valens.v1.security.User.UserSecurityDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceImpl {

    public User user;
    public UserSecurityDetails userSecurityDetails;

    List<GrantedAuthority> authorities;

    LoginResponseDTO loginResponseDTO;
}
