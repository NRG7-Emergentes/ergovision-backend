package com.nrgserver.ergovision.orchestrator.infrastructure.persistence.jpa.repositories;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.PostureSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository: PostureSettingRepository
 * Manages persistence and retrieval of PostureSetting entities.
 */
@Repository
public interface PostureSettingRepository extends JpaRepository<PostureSetting, Long> {
    
    /**
     * Finds posture setting by user ID.
     * @param userId The user ID
     * @return Optional containing the posture setting if found
     */
    Optional<PostureSetting> findByUserId(Long userId);
    
    /**
     * Checks if a posture setting exists for a user.
     * @param userId The user ID
     * @return true if exists, false otherwise
     */
    boolean existsByUserId(Long userId);
}
