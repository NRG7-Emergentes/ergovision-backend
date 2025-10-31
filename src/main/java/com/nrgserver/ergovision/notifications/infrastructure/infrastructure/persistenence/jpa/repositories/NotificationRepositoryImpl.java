package com.nrgserver.ergovision.notifications.infrastructure.infrastructure.persistenence.jpa.repositories;

import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

// This implementation is kept as a non-bean helper to avoid colliding with the Spring Data repository bean
public class NotificationRepositoryImpl {
    private final Map<Long, Notification> store = new ConcurrentHashMap<>();

    public void save(Notification notification) {
        if (notification == null || notification.getId() == null) return;
        store.put(notification.getId(), notification);
    }

    public Optional<Notification> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Notification> findByUserId(Long userId) {
        return store.values().stream()
                .filter(n -> n.getUserId() != null && n.getUserId().equals(userId))
                .sorted(Comparator.comparingLong(NotificationRepositoryImpl::getTimestampMillis).reversed())
                .collect(Collectors.toList());
    }

    public List<Notification> findUnreadByUserId(Long userId) {
        return store.values().stream()
                .filter(n -> n.getUserId() != null && n.getUserId().equals(userId))
                .filter(n -> n.getStatus() != null && !n.getStatus().name().equals("READ"))
                .sorted(Comparator.comparingLong(NotificationRepositoryImpl::getTimestampMillis).reversed())
                .collect(Collectors.toList());
    }

    public void updateStatus(Long notificationId, String status) {
        Notification n = store.get(notificationId);
        if (n == null) return;
        try {
            java.lang.reflect.Field statusField = n.getClass().getDeclaredField("status");
            statusField.setAccessible(true);
            if ("READ".equals(status)) {
                n.markAsRead();
            } else if ("FAILED".equals(status)) {
                n.markFailed();
            } else {
                try {
                    Class<?> statusEnum = statusField.getType();
                    Object enumVal = Enum.valueOf((Class<Enum>) statusEnum, status);
                    statusField.set(n, enumVal);
                } catch (Exception ex) {
                    // ignore invalid statuses
                }
            }
        } catch (NoSuchFieldException ignored) {
        }
        store.put(notificationId, n);
    }

    // Helper: try to extract a timestamp as epoch milliseconds from common methods/fields.
    private static long getTimestampMillis(Notification n) {
        if (n == null) return 0L;
        try {
            String[] methodCandidates = {"getTimestamp", "getCreatedAt", "getCreatedDate", "getCreated", "getSentAt", "getDate", "getTime"};
            for (String mName : methodCandidates) {
                try {
                    java.lang.reflect.Method m = n.getClass().getMethod(mName);
                    Object val = m.invoke(n);
                    long millis = toMillis(val);
                    if (millis != Long.MIN_VALUE) return millis;
                } catch (NoSuchMethodException ignored) {
                }
            }

            String[] fieldCandidates = {"timestamp", "createdAt", "createdDate", "created", "sentAt", "date", "time"};
            for (String fName : fieldCandidates) {
                try {
                    java.lang.reflect.Field f = n.getClass().getDeclaredField(fName);
                    f.setAccessible(true);
                    Object val = f.get(n);
                    long millis = toMillis(val);
                    if (millis != Long.MIN_VALUE) return millis;
                } catch (NoSuchFieldException ignored) {
                }
            }
        } catch (Exception ignored) {
        }
        return 0L;
    }

    private static long toMillis(Object val) {
        if (val == null) return Long.MIN_VALUE;
        if (val instanceof Long) return (Long) val;
        if (val instanceof Number) return ((Number) val).longValue();
        if (val instanceof java.util.Date) return ((java.util.Date) val).getTime();
        if (val instanceof java.time.Instant) return ((java.time.Instant) val).toEpochMilli();
        if (val instanceof java.time.LocalDateTime) {
            return ((java.time.LocalDateTime) val).toInstant(java.time.ZoneOffset.UTC).toEpochMilli();
        }
        if (val instanceof java.time.LocalDate) {
            return ((java.time.LocalDate) val).atStartOfDay(java.time.ZoneOffset.UTC).toInstant().toEpochMilli();
        }
        if (val instanceof java.sql.Timestamp) return ((java.sql.Timestamp) val).getTime();
        return Long.MIN_VALUE;
    }
}