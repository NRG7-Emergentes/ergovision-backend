package com.nrgserver.ergovision.notifications.infrastructure.infrastructure.persistenence.jpa.repositories;

import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationStatus;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.Comparator;

public interface NotificationRepository {
    Optional<Notification> findById(Long id);
    void save(Notification notification);
    List<Notification> findByUserId(Long userId);
    List<Notification> findByStatus(NotificationStatus status);
    List<Notification> findByUserIdAndStatusNot(Long userId, NotificationStatus status);

    // In-memory implementation registered as a Spring bean so injection succeeds.
    @Repository
    class InMemoryImpl implements NotificationRepository {
        private final Map<Long, Notification> store = new ConcurrentHashMap<>();

        @Override
        public Optional<Notification> findById(Long id) {
            return Optional.ofNullable(store.get(id));
        }

        @Override
        public void save(Notification notification) {
            if (notification == null) return;
            try {
                Long id = notification.getId();
                if (id == null) return;
                store.put(id, notification);
            } catch (Exception ignored) {}
        }

        @Override
        public List<Notification> findByUserId(Long userId) {
            if (userId == null) return Collections.emptyList();
            return store.values().stream()
                    .filter(n -> n.getUserId() != null && n.getUserId().equals(userId))
                    .sorted(Comparator.comparing(Notification::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                    .collect(Collectors.toList());
        }

        @Override
        public List<Notification> findByStatus(NotificationStatus status) {
            if (status == null) return Collections.emptyList();
            return store.values().stream()
                    .filter(n -> n.getStatus() == status)
                    .sorted(Comparator.comparing(Notification::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                    .collect(Collectors.toList());
        }

        @Override
        public List<Notification> findByUserIdAndStatusNot(Long userId, NotificationStatus status) {
            if (userId == null) return Collections.emptyList();
            return store.values().stream()
                    .filter(n -> n.getUserId() != null && n.getUserId().equals(userId))
                    .filter(n -> n.getStatus() != status)
                    .sorted(Comparator.comparing(Notification::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                    .collect(Collectors.toList());
        }
    }
}
