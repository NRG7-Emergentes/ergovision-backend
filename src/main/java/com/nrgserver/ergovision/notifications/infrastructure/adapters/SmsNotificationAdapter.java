package com.nrgserver.ergovision.notifications.infrastructure.adapters;

import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// Adapter que encapsula el envío SMS (simulado)
@Component
public class SmsNotificationAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(SmsNotificationAdapter.class);

    public void sendSms(Notification notification, String phoneNumber) {
        // En una implementación real aquí se integraría con un proveedor de SMS (Twilio, Nexmo, etc).
        LOG.info("SmsAdapter: sending SMS to phone={} notificationId={} title={}", phoneNumber, notification.getId(), notification.getTitle());
        // simulación: no-op
    }
}
