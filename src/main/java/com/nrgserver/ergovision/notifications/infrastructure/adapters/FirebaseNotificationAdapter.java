package com.nrgserver.ergovision.notifications.infrastructure.adapters;

import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// Adapter que encapsula el envío push (simulado)
@Component
public class FirebaseNotificationAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(FirebaseNotificationAdapter.class);

    public void sendPush(Notification notification, String deviceToken) {
        // En una implementación real aquí se integraría con Firebase Admin SDK.
        // Este método simula el envío y registra el intento.
        LOG.info("FirebaseAdapter: sending PUSH to token={} notificationId={} title={}", deviceToken, notification.getId(), notification.getTitle());
        // simulación: no-op
    }
}
