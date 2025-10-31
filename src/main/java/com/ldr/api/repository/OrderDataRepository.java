package com.ldr.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ldr.api.model.OrderData;

@Repository
public interface OrderDataRepository extends JpaRepository<OrderData, String> {

    /**
     * Find OrderData by deleted status
     * @param isDeleted the deleted status
     * @return List<OrderData>
     */
    List<OrderData> findByIsDeleted(boolean isDeleted);

    /**
     * Find OrderData with pagination and search
     * @param searchTerm search term for title, description, client name
     * @param statusId status ID filter
     * @param priorityId priority ID filter
     * @param requestorId requestor ID filter
     * @param startDate submission date from
     * @param endDate submission date to
     * @param pageable pagination information
     * @return Page<OrderData>
     */
    @Query("""
           SELECT o FROM OrderData o WHERE
           (:searchTerm IS NULL OR :searchTerm = '' OR
            LOWER(o.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
            LOWER(o.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
            LOWER(o.clientName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND
           (:statusId IS NULL OR o.currentStatusCode = :statusId) AND
           (:priorityId IS NULL OR o.priority.id = :priorityId) AND
           (:requestorId IS NULL OR o.requestor.id = :requestorId) AND
           (:currentRole IS NULL OR o.currentRole = :currentRole) AND
           (:startDate IS NULL OR o.submissionDate >= :startDate) AND
           (:endDate IS NULL OR o.submissionDate <= :endDate) AND
           o.isDeleted = false
           ORDER BY o.createdAt DESC
           """)
    Page<OrderData> findWithFilters(@Param("searchTerm") String searchTerm,
                                   @Param("statusId") String statusId,
                                   @Param("priorityId") String priorityId,
                                   @Param("requestorId") String requestorId,
                                   @Param("currentRole") String currentRole,
                                   @Param("startDate") LocalDate startDate,
                                   @Param("endDate") LocalDate endDate,
                                   Pageable pageable);
}
