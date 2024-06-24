package rca.templates.valens.v1.services;

import rca.templates.valens.v1.models.Role;

public interface IRoleService {
    public Role getByRoleName(String roleName);
}
