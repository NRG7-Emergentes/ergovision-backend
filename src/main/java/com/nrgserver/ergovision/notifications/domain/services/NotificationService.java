package com.nrgserver.ergovision.notifications.domain.services;

import com.nrgserver.ergovision.notifications.domain.exceptions.InvalidNotificationException;
import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;
import com.nrgserver.ergovision.notifications.domain.model.aggregates.UserPreferences;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.DeliveryChannel;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationStatus;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationType;
import com.nrgserver.ergovision.notifications.infrastructure.infrastructure.persistenence.jpa.repositories.NotificationRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    public void sendNotification(Notification n, UserPreferences up) {
        if (!validateType(n)) {
            throw new InvalidNotificationException("Invalid notification type or malformed notification");
        }

        applyUserPreferences(n, up);
        persist(n);

        // Intento de envío (simulado); en infraestructura se haría el envío real y se actualizaría el estado.
        n.send();
        persist(n);
    }

    public boolean validateType(Notification n) {
        return n != null && n.getType() != null && (n.getType() == NotificationType.INFO || n.getType() == NotificationType.REMINDER || n.getType() == NotificationType.ALERT);
    }

    public void applyUserPreferences(Notification n, UserPreferences up) {
        if (up == null) return;
        // If DND is active -> keep PENDING and do not send now
        if (up.isDoNotDisturb()) {
            n.markFailed(); // mark as failed/pending for later retry; alternative could be keep PENDING
            return;
        }
        // If current channel not allowed, try to reassign to a preferred allowed channel
        DeliveryChannel current = n.getChannel();
        if (current == null || !up.isChannelAllowed(current)) {
            List<DeliveryChannel> allowed = up.getPreferredChannels().stream().filter(ch -> up.isChannelAllowed(ch)).collect(Collectors.toList());
            if (!allowed.isEmpty()) {
                // assign first allowed channel
                n.setChannel(allowed.get(0));
            } else {
                // no allowed channels -> keep pending/failed
                n.markFailed();
            }
        }
    }

    public void persist(Notification n) {
        repository.save(n);
    }

    public void retryDelivery(Long notificationId) {
        Optional<Notification> maybe = repository.findById(notificationId);
        if (maybe.isEmpty()) return;
        Notification n = maybe.get();
        if (n.getStatus() == NotificationStatus.FAILED || n.getStatus() == NotificationStatus.PENDING) {
            // attempt resend: if FAILED use resend, if PENDING attempt send
            try {
                if (n.getStatus() == NotificationStatus.FAILED) n.resend();
                else n.send();
                repository.save(n);
            } catch (Exception ex) {
                n.markFailed();
                repository.save(n);
            }
        }
    }

    // New: list notifications for a user with optional filters
    public List<Notification> getUserNotifications(Long userId, NotificationStatus statusFilter, NotificationType typeFilter) {
        List<Notification> byUser = repository.findByUserId(userId);
        return byUser.stream()
                .filter(n -> statusFilter == null || n.getStatus() == statusFilter)
                .filter(n -> typeFilter == null || n.getType() == typeFilter)
                .collect(Collectors.toList());
    }

    // New: get by id
    public Optional<Notification> getById(Long id) {
        return repository.findById(id);
    }

    // New: mark as read
    public void markAsRead(Long id) {
        Optional<Notification> maybe = repository.findById(id);
        if (maybe.isEmpty()) return;
        Notification n = maybe.get();
        n.markAsRead();
        repository.save(n);
    }
}