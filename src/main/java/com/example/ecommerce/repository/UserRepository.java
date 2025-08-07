package com.example.ecommerce.repository;
import com.example.ecommerce.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUsername(String username);

    User findByEmailAndPassword(String email, String password);
    User findByUsernameAndPassword(String username, String password);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    User findByUsernameOrEmail(String usernameOrEmail, String usernameOrEmail1);
}
