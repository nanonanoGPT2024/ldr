package com.ldr.api.repository;

import com.ldr.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Find User by username
     * @param username the username
     * @return Optional<User>
     */
    Optional<User> findByUsername(String username);

    /**
     * Find User by email
     * @param email the email
     * @return Optional<User>
     */
    Optional<User> findByEmail(String email);

    /**
     * Find all User by role
     * @param role the role
     * @return List<User>
     */
    List<User> findByRole(String role);

    /**
     * Find all User by active status
     * @param isActive the active status
     * @return List<User>
     */
    List<User> findByIsActive(boolean isActive);

    /**
     * Find all User by department
     * @param department the department
     * @return List<User>
     */
    List<User> findByDepartment(String department);
}
