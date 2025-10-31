package com.ldr.api.repository;

import com.ldr.api.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, String> {

    /**
     * Find OrderStatus by code
     * @param code the unique code
     * @return Optional<OrderStatus>
     */
    Optional<OrderStatus> findByCode(String code);

    /**
     * Find all OrderStatus by active status
     * @param isActive the active status
     * @return List<OrderStatus>
     */
    List<OrderStatus> findByIsActive(boolean isActive);

    /**
     * Find all OrderStatus ordered by sequence order
     * @return List<OrderStatus>
     */
    @Query("SELECT os FROM OrderStatus os ORDER BY os.sequenceOrder ASC")
    List<OrderStatus> findAllOrderedBySequence();

    /**
     * Check if code exists (excluding specific id for updates)
     * @param code the code to check
     * @param id the id to exclude
     * @return true if exists
     */
    @Query("SELECT COUNT(os) > 0 FROM OrderStatus os WHERE os.code = :code AND os.id != :id")
    boolean existsByCodeAndIdNot(@Param("code") String code, @Param("id") String id);
}
