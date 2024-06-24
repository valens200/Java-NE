package rca.templates.valens.v1.services.serviceImpl;

import rca.templates.valens.v1.dtos.response.LoginResponseDTO;
import rca.templates.valens.v1.models.Role;
import rca.templates.valens.v1.models.User;
import rca.templates.valens.v1.security.User.UserSecurityDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ServiceImpl {

    public User user;
    public UserSecurityDetails userSecurityDetails;

    List<GrantedAuthority> authorities;

    LoginResponseDTO loginResponseDTO;

    public Role role;
    public Set<Role> roles;
}
