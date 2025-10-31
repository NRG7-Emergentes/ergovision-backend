package com.nrgserver.ergovision.notifications.application.internal.queryservices;

import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationStatus;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationType;
import com.nrgserver.ergovision.notifications.infrastructure.infrastructure.persistenence.jpa.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// usar los records de queries
import com.nrgserver.ergovision.notifications.domain.model.queries.GetUserNotificationsQuery;
import com.nrgserver.ergovision.notifications.domain.model.queries.GetUnreadNotificationsQuery;
import com.nrgserver.ergovision.notifications.domain.model.queries.GetNotificationsByStatusQuery;

@Service
public class NotificationQueryServiceImpl {
    private final NotificationRepository repository;

    public NotificationQueryServiceImpl(NotificationRepository repository) {
        this.repository = repository;
    }

    // Handlers
    public List<Notification> handle(GetUserNotificationsQuery q) {
        return repository.findByUserId(q.userId()).stream()
                .filter(n -> q.statusFilter() == null || n.getStatus() == q.statusFilter())
                .filter(n -> q.typeFilter() == null || n.getType() == q.typeFilter())
                .collect(Collectors.toList());
    }

    public List<Notification> handle(GetUnreadNotificationsQuery q) {
        try {
            return repository.findByUserIdAndStatusNot(q.userId(), NotificationStatus.READ);
        } catch (NoSuchMethodError | NoClassDefFoundError e) {
            return repository.findByUserId(q.userId()).stream()
                    .filter(n -> n.getStatus() != NotificationStatus.READ)
                    .collect(Collectors.toList());
        }
    }

    public List<Notification> handle(GetNotificationsByStatusQuery q) {
        try {
            return repository.findByStatus(q.status()).stream()
                    .filter(n -> q.userId() == null || n.getUserId().equals(q.userId()))
                    .collect(Collectors.toList());
        } catch (NoSuchMethodError | NoClassDefFoundError e) {
            return repository.findByUserId(q.userId()).stream()
                    .filter(n -> n.getStatus() == q.status())
                    .collect(Collectors.toList());
        }
    }

    // New: get by id
    public Optional<Notification> getById(Long id) {
        return repository.findById(id);
    }
}
