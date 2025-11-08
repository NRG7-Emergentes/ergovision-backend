package com.nrgserver.ergovision.notifications.infrastructure.persistence.jpa.repositories;

import com.nrgserver.ergovision.notifications.domain.model.aggregates.UserPreferences;

// ...existing code...
public interface UserPreferencesRepository {
    UserPreferences findByUserId(Long userId);
    void update(Long userId, UserPreferences preferences);
}
