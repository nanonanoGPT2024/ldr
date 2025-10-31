package com.ldr.api.util;

import com.ldr.api.dto.UserDto;
import com.ldr.api.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EntityMapperTest {

    @Autowired
    private EntityMapper entityMapper;

    private User testUser;
    private UserDto testUserDto;

    @BeforeEach
    void setUp() {
        // Create test User entity
        testUser = new User();
        testUser.setId("test-id-123");
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
        testUser.setEmail("test@example.com");
        testUser.setFullName("Test User");
        testUser.setRole("USER");
        testUser.setDepartment("IT");
        testUser.setPosition("Developer");
        testUser.setPhone("1234567890");
        testUser.setActive(true);
        testUser.setDeleted(false);
        testUser.setFailedLoginAttempts(0);
        testUser.setCreatedAt(LocalDateTime.now());
        testUser.setUpdatedAt(LocalDateTime.now());
        testUser.setVersion(1L);

        // Create test UserDto
        testUserDto = new UserDto();
        testUserDto.setId("dto-id-456");
        testUserDto.setUsername("dtouser");
        testUserDto.setPassword("dtoPassword123");
        testUserDto.setEmail("dto@example.com");
        testUserDto.setFullName("DTO User");
        testUserDto.setRole("ADMIN");
        testUserDto.setDepartment("HR");
        testUserDto.setPosition("Manager");
        testUserDto.setPhone("0987654321");
        testUserDto.setIsActive(false);
        testUserDto.setIsDeleted(true);
        testUserDto.setFailedLoginAttempts(2);
        testUserDto.setCreatedAt(LocalDateTime.now().minusDays(1));
        testUserDto.setUpdatedAt(LocalDateTime.now().minusHours(1));
        testUserDto.setVersion(2L);
    }

    @Test
    void testMapToDto_NullEntity_ShouldReturnNull() {
        UserDto result = entityMapper.mapToDto(null, UserDto.class);
        assertNull(result);
    }

    @Test
    void testMapToDto_ValidEntity_ShouldMapCorrectly() {
        UserDto result = entityMapper.mapToDto(testUser, UserDto.class);

        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getUsername(), result.getUsername());
        assertEquals(testUser.getEmail(), result.getEmail());
        assertEquals(testUser.getFullName(), result.getFullName());
        assertEquals(testUser.getRole(), result.getRole());
        assertEquals(testUser.getDepartment(), result.getDepartment());
        assertEquals(testUser.getPosition(), result.getPosition());
        assertEquals(testUser.getPhone(), result.getPhone());
        assertEquals(testUser.isActive(), result.getIsActive());
        assertEquals(testUser.isDeleted(), result.getIsDeleted());
        assertEquals(testUser.getFailedLoginAttempts(), result.getFailedLoginAttempts());
        assertEquals(testUser.getCreatedAt(), result.getCreatedAt());
        assertEquals(testUser.getUpdatedAt(), result.getUpdatedAt());
        assertEquals(testUser.getVersion(), result.getVersion());
    }

    @Test
    void testMapToEntity_NullDto_ShouldReturnNull() {
        User result = entityMapper.mapToEntity(null, User.class);
        assertNull(result);
    }

    @Test
    void testMapToEntity_ValidDto_ShouldMapCorrectly() {
        User result = entityMapper.mapToEntity(testUserDto, User.class);

        assertNotNull(result);
        assertEquals(testUserDto.getId(), result.getId());
        assertEquals(testUserDto.getUsername(), result.getUsername());
        assertEquals(testUserDto.getEmail(), result.getEmail());
        assertEquals(testUserDto.getFullName(), result.getFullName());
        assertEquals(testUserDto.getRole(), result.getRole());
        assertEquals(testUserDto.getDepartment(), result.getDepartment());
        assertEquals(testUserDto.getPosition(), result.getPosition());
        assertEquals(testUserDto.getPhone(), result.getPhone());
        assertEquals(testUserDto.getIsActive(), result.isActive());
        assertEquals(testUserDto.getIsDeleted(), result.isDeleted());
        assertEquals(testUserDto.getFailedLoginAttempts(), result.getFailedLoginAttempts());
        assertEquals(testUserDto.getCreatedAt(), result.getCreatedAt());
        assertEquals(testUserDto.getUpdatedAt(), result.getUpdatedAt());
        assertEquals(testUserDto.getVersion(), result.getVersion());
    }

    @Test
    void testMapToUserDto_SpecificMethod_ShouldWork() {
        UserDto result = entityMapper.mapToUserDto(testUser);

        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getUsername(), result.getUsername());
        assertEquals(testUser.getEmail(), result.getEmail());
        assertEquals(testUser.getFullName(), result.getFullName());
        assertEquals(testUser.getRole(), result.getRole());
    }

    @Test
    void testMapToUserDto_NullUser_ShouldReturnNull() {
        UserDto result = entityMapper.mapToUserDto(null);
        assertNull(result);
    }

    @Test
    void testMapToDtoList_NullList_ShouldReturnNull() {
        List<UserDto> result = entityMapper.mapToDtoList(null, UserDto.class);
        assertNull(result);
    }

    @Test
    void testMapToDtoList_ValidList_ShouldMapCorrectly() {
        List<User> users = Arrays.asList(testUser, testUser);
        List<UserDto> result = entityMapper.mapToDtoList(users, UserDto.class);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testUser.getId(), result.get(0).getId());
        assertEquals(testUser.getUsername(), result.get(0).getUsername());
    }

    @Test
    void testMapToEntityList_NullList_ShouldReturnNull() {
        List<User> result = entityMapper.mapToEntityList(null, User.class);
        assertNull(result);
    }

    @Test
    void testMapToEntityList_ValidList_ShouldMapCorrectly() {
        List<UserDto> dtos = Arrays.asList(testUserDto, testUserDto);
        List<User> result = entityMapper.mapToEntityList(dtos, User.class);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testUserDto.getId(), result.get(0).getId());
        assertEquals(testUserDto.getUsername(), result.get(0).getUsername());
    }

    @Test
    void testMapWithCustomLogic_ShouldApplyCustomMapping() {
        EntityMapper.FieldMapper<User, UserDto> customMapper = (user, dto) -> {
            // Custom logic: uppercase the username
            dto.setUsername(user.getUsername().toUpperCase());
            // Custom logic: add prefix to role
            dto.setRole("ROLE_" + user.getRole());
        };

        UserDto result = entityMapper.mapWithCustomLogic(testUser, UserDto.class, customMapper);

        assertNotNull(result);
        assertEquals("TESTUSER", result.getUsername()); // Uppercased
        assertEquals("ROLE_USER", result.getRole()); // With prefix
        assertEquals(testUser.getEmail(), result.getEmail()); // Other fields mapped normally
    }

    @Test
    void testMapWithCustomLogic_NullCustomMapper_ShouldMapNormally() {
        UserDto result = entityMapper.mapWithCustomLogic(testUser, UserDto.class, null);

        assertNotNull(result);
        assertEquals(testUser.getUsername(), result.getUsername()); // No custom logic applied
        assertEquals(testUser.getRole(), result.getRole());
    }
}