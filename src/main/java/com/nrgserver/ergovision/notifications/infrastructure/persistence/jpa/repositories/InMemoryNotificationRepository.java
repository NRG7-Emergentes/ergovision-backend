package com.nrgserver.ergovision.notifications.infrastructure.persistence.jpa.repositories;

import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

// This helper is intentionally NOT annotated with @Repository to avoid creating a Spring bean
public class InMemoryNotificationRepository {
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
                .sorted(Comparator.comparingLong(InMemoryNotificationRepository::getTimestampMillis).reversed())
                .collect(Collectors.toList());
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
        return switch (val) {
            case Long l -> l;
            case Number number -> number.longValue();
            case Date date -> date.getTime();
            case java.time.Instant instant -> instant.toEpochMilli();
            case java.time.LocalDateTime localDateTime ->
                    localDateTime.toInstant(java.time.ZoneOffset.UTC).toEpochMilli();
            case java.time.LocalDate localDate ->
                    localDate.atStartOfDay(java.time.ZoneOffset.UTC).toInstant().toEpochMilli();
            case null, default -> Long.MIN_VALUE;
        };
    }
}