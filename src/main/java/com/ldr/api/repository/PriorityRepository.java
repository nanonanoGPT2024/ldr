package com.ldr.api.repository;

import com.ldr.api.model.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, String> {

    /**
     * Find Priority by code
     * @param code the unique code
     * @return Optional<Priority>
     */
    Optional<Priority> findByCode(String code);

    /**
     * Find all Priority by active status
     * @param isActive the active status
     * @return List<Priority>
     */
    List<Priority> findByIsActive(boolean isActive);

    /**
     * Check if code exists (excluding specific id for updates)
     * @param code the code to check
     * @param id the id to exclude
     * @return true if exists
     */
    @Query("SELECT COUNT(p) > 0 FROM Priority p WHERE p.code = :code AND p.id != :id")
    boolean existsByCodeAndIdNot(@Param("code") String code, @Param("id") String id);
}
