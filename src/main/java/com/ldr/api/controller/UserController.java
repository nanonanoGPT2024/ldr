// package com.ldr.api.controller;

// import com.ldr.api.exception.ResourceNotFoundException;
// import com.ldr.api.exception.ValidationException;
// import com.ldr.api.model.User;
// import com.ldr.api.service.UserService;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import jakarta.validation.Valid;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/users")
// @Tag(name = "User", description = "User management APIs")
// public class UserController {

//     private final UserService userService;

//     @Autowired
//     public UserController(UserService userService) {
//         this.userService = userService;
//     }

//     @GetMapping
//     @Operation(summary = "Get all Users", description = "Retrieve a list of all users")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<User>> getAllUsers() {
//         List<User> users = userService.findAll();
//         return ResponseEntity.ok(users);
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get User by ID", description = "Retrieve a specific user by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
//         @ApiResponse(responseCode = "404", description = "User not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<User> getUserById(
//             @Parameter(description = "User ID") @PathVariable String id) {
//         try {
//             User user = userService.findById(id);
//             return ResponseEntity.ok(user);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @PostMapping
//     @Operation(summary = "Create User", description = "Create a new user")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "201", description = "User created successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<User> createUser(
//             @Parameter(description = "User object") @Valid @RequestBody User user) {
//         try {
//             User createdUser = userService.save(user);
//             return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update User", description = "Update an existing user")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "User updated successfully"),
//         @ApiResponse(responseCode = "400", description = "Invalid input data"),
//         @ApiResponse(responseCode = "404", description = "User not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<User> updateUser(
//             @Parameter(description = "User ID") @PathVariable String id,
//             @Parameter(description = "Updated User object") @Valid @RequestBody User user) {
//         try {
//             User updatedUser = userService.update(id, user);
//             return ResponseEntity.ok(updatedUser);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete User", description = "Delete a user by its ID")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "204", description = "User deleted successfully"),
//         @ApiResponse(responseCode = "404", description = "User not found"),
//         @ApiResponse(responseCode = "409", description = "Cannot delete user as it is referenced by other records"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<Void> deleteUser(
//             @Parameter(description = "User ID") @PathVariable String id) {
//         try {
//             userService.delete(id);
//             return ResponseEntity.noContent().build();
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         } catch (ValidationException e) {
//             return ResponseEntity.status(HttpStatus.CONFLICT).build();
//         }
//     }

//     @GetMapping("/username/{username}")
//     @Operation(summary = "Get User by Username", description = "Retrieve a user by username")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
//         @ApiResponse(responseCode = "404", description = "User not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<User> getUserByUsername(
//             @Parameter(description = "Username") @PathVariable String username) {
//         try {
//             User user = userService.findByUsername(username);
//             return ResponseEntity.ok(user);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @GetMapping("/email/{email}")
//     @Operation(summary = "Get User by Email", description = "Retrieve a user by email")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
//         @ApiResponse(responseCode = "404", description = "User not found"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<User> getUserByEmail(
//             @Parameter(description = "Email") @PathVariable String email) {
//         try {
//             User user = userService.findByEmail(email);
//             return ResponseEntity.ok(user);
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @GetMapping("/role/{role}")
//     @Operation(summary = "Get Users by Role", description = "Retrieve all users by role")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<User>> getUsersByRole(
//             @Parameter(description = "Role") @PathVariable String role) {
//         List<User> users = userService.findByRole(role);
//         return ResponseEntity.ok(users);
//     }

//     @GetMapping("/active")
//     @Operation(summary = "Get Active Users", description = "Retrieve all active users")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<User>> getActiveUsers() {
//         List<User> activeUsers = userService.findByIsActive(true);
//         return ResponseEntity.ok(activeUsers);
//     }

//     @GetMapping("/department/{department}")
//     @Operation(summary = "Get Users by Department", description = "Retrieve all users by department")
//     @ApiResponses(value = {
//         @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//         @ApiResponse(responseCode = "500", description = "Internal server error")
//     })
//     public ResponseEntity<List<User>> getUsersByDepartment(
//             @Parameter(description = "Department") @PathVariable String department) {
//         List<User> users = userService.findByDepartment(department);
//         return ResponseEntity.ok(users);
//     }
// }
