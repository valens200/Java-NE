package rca.ne.java.v1.services.serviceImpl;

import rca.ne.java.v1.exceptions.NotFoundException;
import rca.ne.java.v1.models.Role;
import rca.ne.java.v1.repositories.IRoleRepository;
import rca.ne.java.v1.services.IRoleService;
import rca.ne.java.v1.utils.ExceptionsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {
    private final IRoleRepository roleRepository;
    @Override
    public Role getByRoleName(String roleName) {
        try {
            return this.roleRepository.findByRoleName(roleName).orElseThrow(()-> new NotFoundException("The role with the provided name is not found"));
        }catch (Exception exception){
            ExceptionsUtils.handleServiceExceptions(exception);
            return null;
        }
    }
}
