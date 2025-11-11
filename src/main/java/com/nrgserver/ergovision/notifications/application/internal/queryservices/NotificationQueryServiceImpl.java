package com.nrgserver.ergovision.notifications.application.internal.queryservices;

import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;
import com.nrgserver.ergovision.notifications.domain.model.queries.GetUserNotificationsQuery;
import com.nrgserver.ergovision.notifications.domain.model.queries.GetUnreadNotificationsQuery;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationStatus;
import com.nrgserver.ergovision.notifications.infrastructure.persistence.jpa.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationQueryServiceImpl {

    private final NotificationRepository repository;

    public NotificationQueryServiceImpl(NotificationRepository repository) {
        this.repository = repository;
    }

    // ✅ NUEVO: Obtener todas las notificaciones
    public List<Notification> getAllNotifications() {
        // Use repository abstraction (JPA adapter provides findAll)
        return repository.findAll();
    }

    // ✅ NUEVO: Obtener todas las notificaciones por estado
    public List<Notification> findByStatus(NotificationStatus status) {
        return repository.findByStatus(status);
    }

    // EXISTENTE: Obtener notificaciones por usuario (con filtros)
    public List<Notification> handle(GetUserNotificationsQuery query) {
        List<Notification> all = repository.findByUserId(query.userId());
        return all.stream()
                .filter(n -> query.status() == null || n.getStatus() == query.status())
                .filter(n -> query.type() == null || n.getType() == query.type())
                .sorted(Comparator.comparing(Notification::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .collect(Collectors.toList());
    }

    // EXISTENTE: Obtener no leídas
    public List<Notification> handle(GetUnreadNotificationsQuery query) {
        return repository.findByUserIdAndStatusNot(query.userId(), NotificationStatus.READ);
    }

    // EXISTENTE: Obtener por ID
    public Optional<Notification> getById(Long id) {
        return repository.findById(id);
    }
}
