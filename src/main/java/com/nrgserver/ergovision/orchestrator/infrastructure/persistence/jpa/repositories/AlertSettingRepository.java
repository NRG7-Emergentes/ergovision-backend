package com.nrgserver.ergovision.orchestrator.infrastructure.persistence.jpa.repositories;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.AlertSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository: AlertSettingRepository
 * Manages persistence and retrieval of AlertSetting entities.
 */
@Repository
public interface AlertSettingRepository extends JpaRepository<AlertSetting, Long> {
    
    /**
     * Finds alert setting by user ID.
     * @param userId The user ID
     * @return Optional containing the alert setting if found
     */
    Optional<AlertSetting> findByUserId(Long userId);
    
    /**
     * Checks if an alert setting exists for a user.
     * @param userId The user ID
     * @return true if exists, false otherwise
     */
    boolean existsByUserId(Long userId);
}
