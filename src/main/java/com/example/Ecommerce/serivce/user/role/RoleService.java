package com.example.Ecommerce.serivce.user.role;

import com.example.Ecommerce.exceptions.user.RoleAlreadyExistsException;
import com.example.Ecommerce.exceptions.user.RoleNotFoundException;
import com.example.Ecommerce.model.entity.Role;
import com.example.Ecommerce.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    private RoleRepository roleRepository;

    @Override
    public Role getRole(String roleName) {
        return Optional.ofNullable(roleRepository.findByName(roleName))
                .orElseThrow(()-> new RoleNotFoundException("Role nod found "+ roleName));
    }

    @Override
    public void saveRole(Role role) {
        Optional.ofNullable(roleRepository.findByName(role.getName()))
                .ifPresentOrElse(
                        (existingRole) -> {
                            throw new RoleAlreadyExistsException("Role Exists : "+ role.getName());
                        },
                        () -> roleRepository.save(role) // Must be a Runnable (a lambda with no arguments)
                );


    }

    @Override
    public void deleteRole(String roleName) {
        Optional.ofNullable(roleRepository.findByName(roleName))
                .ifPresentOrElse(
                        (existingRole) -> {
                            throw new RoleNotFoundException("Role not found: " + roleName);
                        },
                        () -> roleRepository.deleteByName(roleName) // Must be a Runnable (a lambda with no arguments)
                );


    }

    @Override
    public void updateRole(Role role, String roleName) {

    }
}
