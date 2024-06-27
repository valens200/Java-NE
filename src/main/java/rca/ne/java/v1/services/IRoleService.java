package rca.ne.java.v1.services;

import rca.ne.java.v1.models.Role;

public interface IRoleService {
    public Role getByRoleName(String roleName);
}
