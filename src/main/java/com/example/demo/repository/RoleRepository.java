package com.example.demo.repository;

import com.example.demo.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    void deleteByName(String roleName);
}
