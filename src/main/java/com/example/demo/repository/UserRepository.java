package com.example.demo.repository;
import com.example.demo.model.entity.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUsername(String username);

    User findByEmailAndPassword(String email, String password);
    User findByUsernameAndPassword(String username, String password);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    User findByUsernameOrEmail(String usernameOrEmail, String usernameOrEmail1);
}
