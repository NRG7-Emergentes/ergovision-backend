package com.nrgserver.ergovision.orchestrator.infrastructure.persistence.jpa.repositories;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.NotificationSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationSettingRepository extends JpaRepository<NotificationSetting, Long> {
    Optional<NotificationSetting> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
