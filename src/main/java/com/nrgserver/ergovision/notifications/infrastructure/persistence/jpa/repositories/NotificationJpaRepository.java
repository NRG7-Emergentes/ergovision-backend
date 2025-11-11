package com.nrgserver.ergovision.notifications.infrastructure.persistence.jpa.repositories;

import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationJpaRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
    List<Notification> findByStatus(NotificationStatus status);
    List<Notification> findByUserIdAndStatusNot(Long userId, NotificationStatus status);
}

