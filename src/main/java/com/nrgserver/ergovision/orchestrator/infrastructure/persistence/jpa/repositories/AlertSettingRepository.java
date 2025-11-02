package com.nrgserver.ergovision.orchestrator.infrastructure.persistence.jpa.repositories;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.AlertSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlertSettingRepository extends JpaRepository<AlertSetting, Long> {
    
    Optional<AlertSetting> findByUserId(Long userId);
    
    boolean existsByUserId(Long userId);
}
