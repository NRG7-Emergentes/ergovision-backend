package com.nrgserver.ergovision.notifications.infrastructure.persistence.jpa.repositories;

import com.nrgserver.ergovision.notifications.domain.model.aggregates.NotificationSent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationSentRepository extends JpaRepository<NotificationSent, Long> { }
