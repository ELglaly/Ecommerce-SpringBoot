package com.example.ecommerce.repository.user;
import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.request.user.UpdateUserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.concurrent.ScheduledExecutorTask;

public interface UserRepository extends JpaRepository<User, Long> {
    <T> T  findByUserContactEmail(String email, Class<T> type);
    <T> T findById(Long id, Class<T> type);
    <T> T findByUsername(String username, Class<T> type);

    @Modifying
    @Query("UPDATE User u SET u.firstName = :#{#updateUserRequest.firstName}, " +
            "u.lastName = :#{#updateUserRequest.lastName}, " +
            "u.birthDate = :#{#updateUserRequest.birthDate}, " +
            "u.userContact.address = :#{#updateUserRequest.addresses}, " +
            "u.userContact.phoneNumber = :#{#updateUserRequest.phoneNumbers} " +
            "WHERE u.id = :#{#updateUserRequest.id}")
    UpdateUserRequest save(UpdateUserRequest updateUserRequest);

    boolean existsByUserContactEmail(String email);

    boolean existsByUsername(String username);

    <T> T findByUsernameOrUserContactEmail(String usernameOrEmail, String usernameOrEmail1,
                                           Class<T> type);
}
