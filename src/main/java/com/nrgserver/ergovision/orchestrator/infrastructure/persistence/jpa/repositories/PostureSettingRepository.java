package com.nrgserver.ergovision.orchestrator.infrastructure.persistence.jpa.repositories;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.PostureSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostureSettingRepository extends JpaRepository<PostureSetting, Long> {
    
    Optional<PostureSetting> findByUserId(Long userId);
    
    boolean existsByUserId(Long userId);
}
