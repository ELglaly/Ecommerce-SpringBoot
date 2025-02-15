package com.example.demo.serivce.user.role;

import com.example.demo.exceptions.user.RoleAlreadyExistsException;
import com.example.demo.exceptions.user.RoleNotFoundException;
import com.example.demo.model.entity.Role;
import com.example.demo.repository.RoleRepository;
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
