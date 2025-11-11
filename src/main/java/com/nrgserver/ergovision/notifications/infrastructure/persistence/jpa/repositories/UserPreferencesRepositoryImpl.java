package com.nrgserver.ergovision.notifications.infrastructure.persistence.jpa.repositories;

import com.nrgserver.ergovision.notifications.domain.model.aggregates.UserPreferences;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Implementación en memoria para desarrollo/testing
@Repository
public class UserPreferencesRepositoryImpl implements UserPreferencesRepository {
    private final Map<Long, UserPreferences> store = new ConcurrentHashMap<>();

    @Override
    public UserPreferences findByUserId(Long userId) {
        return store.get(userId);
    }

    @Override
    public void update(Long userId, UserPreferences preferences) {
        if (userId == null || preferences == null) return;
        store.put(userId, preferences);
    }
}
