package com.example.Ecommerce.serivce.user.role;

import com.example.Ecommerce.model.entity.Role;

public interface IRoleService {
    Role getRole(String roleName);
    void saveRole(Role role);
    void deleteRole(String roleName);
    void updateRole(Role role, String roleName);
}
