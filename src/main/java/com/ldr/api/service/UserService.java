package com.ldr.api.service;

import com.ldr.api.dto.RegisterRequest;
import com.ldr.api.exception.DuplicateResourceException;
import com.ldr.api.exception.ResourceNotFoundException;
import com.ldr.api.exception.ValidationException;
import com.ldr.api.model.User;
import com.ldr.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Find all User entities
     * @return List<User>
     */
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Find User by ID
     * @param id the User ID
     * @return User
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    /**
     * Save a new User
     * @param user the User to save
     * @return saved User
     * @throws ValidationException if validation fails
     */
    public User save(User user) {
        validateUser(user);

        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to save User due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Update an existing User
     * @param id the User ID
     * @param user the updated User
     * @return updated User
     * @throws ResourceNotFoundException if not found
     * @throws ValidationException if validation fails
     */
    public User update(String id, User user) {
        User existingUser = findById(id);

        validateUser(user);

        // Update fields
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());
        existingUser.setFullName(user.getFullName());
        existingUser.setRole(user.getRole());
        existingUser.setDepartment(user.getDepartment());
        existingUser.setPosition(user.getPosition());
        existingUser.setPhone(user.getPhone());
        existingUser.setActive(user.isActive());

        try {
            return userRepository.save(existingUser);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to update User due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Delete User by ID
     * @param id the User ID
     * @throws ResourceNotFoundException if not found
     */
    public void delete(String id) {
        User user = findById(id);
        try {
            userRepository.delete(user);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Cannot delete User as it is referenced by other records: " + id);
        }
    }

    /**
     * Find User by username
     * @param username the username
     * @return User
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    /**
     * Find User by email
     * @param email the email
     * @return User
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    /**
     * Find all User by role
     * @param role the role
     * @return List<User>
     */
    @Transactional(readOnly = true)
    public List<User> findByRole(String role) {
        return userRepository.findByRole(role);
    }

    /**
     * Find all User by active status
     * @param isActive the active status
     * @return List<User>
     */
    @Transactional(readOnly = true)
    public List<User> findByIsActive(boolean isActive) {
        return userRepository.findByIsActive(isActive);
    }

    /**
     * Find all User by department
     * @param department the department
     * @return List<User>
     */
    @Transactional(readOnly = true)
    public List<User> findByDepartment(String department) {
        return userRepository.findByDepartment(department);
    }

    /**
     * Register a new user
     * @param registerRequest the registration request
     * @return registered User
     * @throws DuplicateResourceException if username or email already exists
     * @throws ValidationException if validation fails
     */
    public User register(RegisterRequest registerRequest) {
        // Validate unique username
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new DuplicateResourceException("Username already exists: " + registerRequest.getUsername());
        }

        // Validate unique email
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists: " + registerRequest.getEmail());
        }

        // Create new user
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setFullName(registerRequest.getFullName());
        user.setRole(registerRequest.getRole()); // User-defined role
        user.setDepartment(registerRequest.getDepartment());
        user.setPosition(registerRequest.getPosition());
        user.setPhone(registerRequest.getPhone());
        user.setActive(true);

        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Failed to register user due to data integrity violation: " + e.getMessage());
        }
    }

    /**
     * Validate User entity
     * @param user the User to validate
     * @throws ValidationException if validation fails
     */
    private void validateUser(User user) {
        if (user == null) {
            throw new ValidationException("User cannot be null");
        }

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new ValidationException("Username is required");
        }

        if (user.getUsername().length() > 50) {
            throw new ValidationException("Username cannot exceed 50 characters");
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new ValidationException("Password is required");
        }

        if (user.getPassword().length() > 255) {
            throw new ValidationException("Password cannot exceed 255 characters");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new ValidationException("Email is required");
        }

        if (user.getEmail().length() > 100) {
            throw new ValidationException("Email cannot exceed 100 characters");
        }

        if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ValidationException("Invalid email format");
        }

        if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
            throw new ValidationException("Full name is required");
        }

        if (user.getFullName().length() > 255) {
            throw new ValidationException("Full name cannot exceed 255 characters");
        }

        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            throw new ValidationException("Role is required");
        }

        if (user.getRole().length() > 50) {
            throw new ValidationException("Role cannot exceed 50 characters");
        }

        if (user.getDepartment() != null && user.getDepartment().length() > 100) {
            throw new ValidationException("Department cannot exceed 100 characters");
        }

        if (user.getPosition() != null && user.getPosition().length() > 100) {
            throw new ValidationException("Position cannot exceed 100 characters");
        }

        if (user.getPhone() != null && user.getPhone().length() > 20) {
            throw new ValidationException("Phone cannot exceed 20 characters");
        }
    }
}
