package com.nrgserver.ergovision.monitoring.infrastructure.persistence.jpa.repositories;

import com.nrgserver.ergovision.monitoring.domain.model.entities.PostureSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostureSampleRepository extends JpaRepository<PostureSample, Long> {
}
