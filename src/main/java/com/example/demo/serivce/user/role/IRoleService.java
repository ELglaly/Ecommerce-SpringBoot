package com.example.demo.serivce.user.role;

import com.example.demo.model.entity.Role;

public interface IRoleService {
    Role getRole(String roleName);
    void saveRole(Role role);
    void deleteRole(String roleName);
    void updateRole(Role role, String roleName);
}
