package rca.ne.java.v1.services.serviceImpl;

import rca.ne.java.v1.dtos.response.LoginResponseDTO;
import rca.ne.java.v1.models.*;
import rca.ne.java.v1.security.User.UserSecurityDetails;
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
    public Customer customer;
    public Account account;
    public Transaction transaction;
    public User profile;
}
