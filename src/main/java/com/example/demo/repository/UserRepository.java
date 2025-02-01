package com.example.demo.repository;
import com.example.demo.model.entity.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUsername(String username);

    User findByEmailAndPassword(String email, String password);
    User findByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);


    boolean existsByEmail(@Email(message = "Must be a valid Email") String email);

    boolean existsByEmailAndIdNot(String email, Long excludeUserId);

    boolean existsByUsernameAndIdNot(String username, Long excludeUserId);
}
