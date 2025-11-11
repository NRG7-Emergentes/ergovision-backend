package com.nrgserver.ergovision.notifications.domain.services;

import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;
import com.nrgserver.ergovision.notifications.domain.model.aggregates.UserPreferences;
import com.nrgserver.ergovision.notifications.infrastructure.persistence.jpa.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // Enviar una nueva notificación
    public void sendNotification(Notification n, UserPreferences prefs) {
        try {
            n.send(); // dominio define la lógica principal
            notificationRepository.save(n);
        } catch (Exception e) {
            n.markFailed();
            notificationRepository.save(n);
        }
    }

    // Marcar como leída
    public void markAsRead(Long notificationId) {
        Optional<Notification> found = notificationRepository.findById(notificationId);
        if (found.isEmpty()) return;

        Notification n = found.get();
        n.markAsRead();
        notificationRepository.save(n);
    }

    // Reintentar envío
    public void retryDelivery(Long notificationId) {
        Optional<Notification> found = notificationRepository.findById(notificationId);
        if (found.isEmpty()) return;

        Notification n = found.get();
        try {
            n.resend();
            notificationRepository.save(n);
        } catch (IllegalStateException ex) {
            // No se puede reenviar si no está en FAILED
            System.out.println("Cannot resend notification in state: " + n.getStatus());
        }
    }
}
