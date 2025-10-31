package com.ldr.api.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * Generic Entity to DTO mapper utility class
 * Provides dynamic mapping using reflection and manual mapping capabilities
 */
@Component
public class EntityMapper {

    /**
     * Generic method to map entity to DTO using reflection
     * Maps fields with same names and compatible types
     *
     * @param entity the source entity
     * @param dtoClass the target DTO class
     * @param <E> Entity type
     * @param <D> DTO type
     * @return mapped DTO instance
     */
    public <E, D> D mapToDto(E entity, Class<D> dtoClass) {
        if (entity == null) {
            return null;
        }

        try {
            D dto = dtoClass.getDeclaredConstructor().newInstance();

            Field[] entityFields = entity.getClass().getDeclaredFields();
            Field[] dtoFields = dtoClass.getDeclaredFields();

            Map<String, Field> dtoFieldMap = new HashMap<>();
            for (Field dtoField : dtoFields) {
                dtoFieldMap.put(dtoField.getName(), dtoField);
            }

            for (Field entityField : entityFields) {
                entityField.setAccessible(true);
                String fieldName = entityField.getName();

                if (dtoFieldMap.containsKey(fieldName)) {
                    Field dtoField = dtoFieldMap.get(fieldName);
                    dtoField.setAccessible(true);

                    // Check if types are compatible
                    if (isCompatibleType(entityField.getType(), dtoField.getType())) {
                        Object value = entityField.get(entity);
                        dtoField.set(dto, value);
                    }
                }
            }

            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Failed to map entity to DTO", e);
        }
    }

    /**
     * Generic method to map DTO to entity using reflection
     *
     * @param dto the source DTO
     * @param entityClass the target entity class
     * @param <D> DTO type
     * @param <E> Entity type
     * @return mapped entity instance
     */
    public <D, E> E mapToEntity(D dto, Class<E> entityClass) {
        if (dto == null) {
            return null;
        }

        try {
            E entity = entityClass.getDeclaredConstructor().newInstance();

            Field[] dtoFields = dto.getClass().getDeclaredFields();
            Field[] entityFields = entityClass.getDeclaredFields();

            Map<String, Field> entityFieldMap = new HashMap<>();
            for (Field entityField : entityFields) {
                entityFieldMap.put(entityField.getName(), entityField);
            }

            for (Field dtoField : dtoFields) {
                dtoField.setAccessible(true);
                String fieldName = dtoField.getName();

                if (entityFieldMap.containsKey(fieldName)) {
                    Field entityField = entityFieldMap.get(fieldName);
                    entityField.setAccessible(true);

                    if (isCompatibleType(dtoField.getType(), entityField.getType())) {
                        Object value = dtoField.get(dto);
                        entityField.set(entity, value);
                    }
                }
            }

            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Failed to map DTO to entity", e);
        }
    }

    /**
     * Specific method to map User entity to UserDto
     * Includes custom mapping logic if needed
     *
     * @param user the User entity
     * @return UserDto instance
     */
    public com.ldr.api.dto.UserDto mapToUserDto(com.ldr.api.model.User user) {
        if (user == null) {
            return null;
        }

        com.ldr.api.dto.UserDto dto = new com.ldr.api.dto.UserDto();

        // Map basic fields using reflection
        dto = mapToDto(user, com.ldr.api.dto.UserDto.class);

        // Custom mapping logic can be added here if needed
        // For example, if field names differ or require transformation

        return dto;
    }

    /**
     * Generic method to map a list of entities to DTOs
     *
     * @param entities list of entities
     * @param dtoClass DTO class
     * @param <E> Entity type
     * @param <D> DTO type
     * @return list of DTOs
     */
    public <E, D> java.util.List<D> mapToDtoList(java.util.List<E> entities, Class<D> dtoClass) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(entity -> mapToDto(entity, dtoClass))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Generic method to map a list of DTOs to entities
     *
     * @param dtos list of DTOs
     * @param entityClass entity class
     * @param <D> DTO type
     * @param <E> Entity type
     * @return list of entities
     */
    public <D, E> java.util.List<E> mapToEntityList(java.util.List<D> dtos, Class<E> entityClass) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(dto -> mapToEntity(dto, entityClass))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Check if two types are compatible for mapping
     *
     * @param sourceType source field type
     * @param targetType target field type
     * @return true if compatible
     */
    private boolean isCompatibleType(Class<?> sourceType, Class<?> targetType) {
        // Same type
        if (sourceType.equals(targetType)) {
            return true;
        }

        // Primitive to wrapper and vice versa
        if (isPrimitiveWrapper(sourceType, targetType)) {
            return true;
        }

        // Number types
        if (Number.class.isAssignableFrom(sourceType) && Number.class.isAssignableFrom(targetType)) {
            return true;
        }

        // String compatibility
        if (sourceType.equals(String.class) && targetType.equals(String.class)) {
            return true;
        }

        return false;
    }

    /**
     * Check if types are primitive-wrapper pairs
     */
    private boolean isPrimitiveWrapper(Class<?> type1, Class<?> type2) {
        Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<>();
        primitiveWrapperMap.put(boolean.class, Boolean.class);
        primitiveWrapperMap.put(byte.class, Byte.class);
        primitiveWrapperMap.put(char.class, Character.class);
        primitiveWrapperMap.put(double.class, Double.class);
        primitiveWrapperMap.put(float.class, Float.class);
        primitiveWrapperMap.put(int.class, Integer.class);
        primitiveWrapperMap.put(long.class, Long.class);
        primitiveWrapperMap.put(short.class, Short.class);

        return (primitiveWrapperMap.get(type1) != null && primitiveWrapperMap.get(type1).equals(type2)) ||
               (primitiveWrapperMap.get(type2) != null && primitiveWrapperMap.get(type2).equals(type1));
    }

    /**
     * Register custom field mapping for specific entity-DTO pairs
     * This allows for custom mapping logic when field names differ
     */
    public interface FieldMapper<E, D> {
        void map(E entity, D dto);
    }

    /**
     * Map with custom field mapping
     *
     * @param entity the entity
     * @param dtoClass DTO class
     * @param customMapper custom mapping logic
     * @param <E> Entity type
     * @param <D> DTO type
     * @return mapped DTO
     */
    public <E, D> D mapWithCustomLogic(E entity, Class<D> dtoClass, FieldMapper<E, D> customMapper) {
        D dto = mapToDto(entity, dtoClass);
        if (customMapper != null && entity != null) {
            customMapper.map(entity, dto);
        }
        return dto;
    }
}