package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    void deleteByName(String roleName);
}
