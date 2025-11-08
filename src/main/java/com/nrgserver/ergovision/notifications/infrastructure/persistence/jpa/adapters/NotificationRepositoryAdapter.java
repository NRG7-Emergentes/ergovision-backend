package com.nrgserver.ergovision.notifications.infrastructure.persistence.jpa.adapters;

import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationStatus;
import com.nrgserver.ergovision.notifications.infrastructure.persistence.jpa.repositories.NotificationJpaRepository;
import com.nrgserver.ergovision.notifications.infrastructure.persistence.jpa.repositories.NotificationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class NotificationRepositoryAdapter implements NotificationRepository {

    private final NotificationJpaRepository jpaRepository;

    public NotificationRepositoryAdapter(NotificationJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public void save(Notification notification) {
        jpaRepository.save(notification);
    }

    @Override
    public List<Notification> findByUserId(Long userId) {
        return jpaRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> findByStatus(NotificationStatus status) {
        return jpaRepository.findByStatus(status);
    }

    @Override
    public List<Notification> findByUserIdAndStatusNot(Long userId, NotificationStatus status) {
        return jpaRepository.findByUserIdAndStatusNot(userId, status);
    }

    @Override
    public List<Notification> findAll() {
        return jpaRepository.findAll();
    }
}
