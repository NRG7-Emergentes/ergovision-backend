package com.nrgserver.ergovision.orchestrator.infrastructure.persistence.jpa.repositories;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.CalibrationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CalibrationDetailsRepository extends JpaRepository<CalibrationDetails, Long> {
    Optional<CalibrationDetails> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
