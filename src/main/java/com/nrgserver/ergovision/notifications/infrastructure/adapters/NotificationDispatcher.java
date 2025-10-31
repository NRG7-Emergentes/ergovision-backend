package com.nrgserver.ergovision.notifications.infrastructure.adapters;

import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;
import com.nrgserver.ergovision.notifications.domain.model.aggregates.UserPreferences;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.DeliveryChannel;
import com.nrgserver.ergovision.notifications.infrastructure.infrastructure.persistenence.jpa.repositories.NotificationRepository;
import com.nrgserver.ergovision.notifications.infrastructure.infrastructure.persistenence.jpa.repositories.UserPreferencesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

// Orquesta envío según preferencias y actualiza persistencia
@Component
public class NotificationDispatcher {
    private static final Logger LOG = LoggerFactory.getLogger(NotificationDispatcher.class);

    private final FirebaseNotificationAdapter firebaseAdapter;
    private final SmsNotificationAdapter smsAdapter;
    private final NotificationRepository notificationRepository;
    private final UserPreferencesRepository preferencesRepository;

    public NotificationDispatcher(FirebaseNotificationAdapter firebaseAdapter,
                                  SmsNotificationAdapter smsAdapter,
                                  NotificationRepository notificationRepository,
                                  UserPreferencesRepository preferencesRepository) {
        this.firebaseAdapter = firebaseAdapter;
        this.smsAdapter = smsAdapter;
        this.notificationRepository = notificationRepository;
        this.preferencesRepository = preferencesRepository;
    }

    public void dispatch(Notification notification, UserPreferences preferences) {
        if (notification == null) return;

        // Resolve preferences if null
        if (preferences == null) {
            preferences = preferencesRepository.findByUserId(notification.getUserId());
        }

        List<DeliveryChannel> preferred = preferences == null ? List.of() : preferences.getPreferredChannels();

        // Choose channel: prefer PUSH, then SMS, then notification.channel if set
        DeliveryChannel chosen = null;
        if (preferred.contains(DeliveryChannel.PUSH)) chosen = DeliveryChannel.PUSH;
        else if (preferred.contains(DeliveryChannel.SMS)) chosen = DeliveryChannel.SMS;
        else if (notification.getChannel() != null) chosen = notification.getChannel();

        if (chosen == null) {
            LOG.warn("No delivery channel available for notificationId={} userId={}", notification.getId(), notification.getUserId());
            notification.markFailed();
            notificationRepository.save(notification);
            return;
        }

        notification.setChannel(chosen);
        notificationRepository.save(notification); // persist chosen channel

        try {
            if (chosen == DeliveryChannel.PUSH) {
                // deviceToken resolution omitted — in real app fetch from user profile
                String deviceToken = "device-token-placeholder";
                firebaseAdapter.sendPush(notification, deviceToken);
            } else if (chosen == DeliveryChannel.SMS) {
                String phone = "phone-placeholder";
                smsAdapter.sendSms(notification, phone);
            }
            // mark as sent and persist
            notification.markAsRead(); // note: domain may have a different rule; if you want SENT use a domain method or setter
            notificationRepository.save(notification);
        } catch (Exception ex) {
            LOG.error("Dispatch failed for notificationId={}", notification.getId(), ex);
            notification.markFailed();
            notificationRepository.save(notification);
        }
    }
}
