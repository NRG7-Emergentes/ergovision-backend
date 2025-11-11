package com.nrgserver.ergovision.notifications.infrastructure.persistence.jpa.repositories;

import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationStatus;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository {
    Optional<Notification> findById(Long id);
    void save(Notification notification);
    List<Notification> findByUserId(Long userId);
    List<Notification> findByStatus(NotificationStatus status);
    List<Notification> findByUserIdAndStatusNot(Long userId, NotificationStatus status);
    List<Notification> findAll();
}
