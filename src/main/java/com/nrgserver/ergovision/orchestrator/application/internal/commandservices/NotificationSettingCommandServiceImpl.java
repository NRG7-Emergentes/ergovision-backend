package com.nrgserver.ergovision.orchestrator.application.internal.commandservices;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.NotificationSetting;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.CreateNotificationSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.DeleteNotificationSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.UpdateNotificationSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.services.NotificationSettingCommandService;
import com.nrgserver.ergovision.orchestrator.infrastructure.persistence.jpa.repositories.NotificationSettingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationSettingCommandServiceImpl implements NotificationSettingCommandService {

    private final NotificationSettingRepository notificationSettingRepository;

    public NotificationSettingCommandServiceImpl(NotificationSettingRepository notificationSettingRepository) {
        this.notificationSettingRepository = notificationSettingRepository;
    }

    @Override
    @Transactional
    public Long handle(CreateNotificationSettingCommand command) {
        if (notificationSettingRepository.existsByUserId(command.userId())) {
            throw new IllegalArgumentException("Notification settings already exist for user: " + command.userId());
        }

        NotificationSetting notificationSetting = new NotificationSetting(
                command.userId(),
                command.emailNotifications()
        );

        NotificationSetting savedSetting = notificationSettingRepository.save(notificationSetting);
        return savedSetting.getId();
    }

    @Override
    @Transactional
    public Long handle(UpdateNotificationSettingCommand command) {
        NotificationSetting notificationSetting = notificationSettingRepository.findById(command.notificationSettingId())
                .orElseThrow(() -> new IllegalArgumentException("Notification setting not found with id: " + command.notificationSettingId()));

        notificationSetting.updateSettings(
                command.emailNotifications()
        );

        NotificationSetting updatedSetting = notificationSettingRepository.save(notificationSetting);
        return updatedSetting.getId();
    }

    @Override
    @Transactional
    public void handle(DeleteNotificationSettingCommand command) {
        if (!notificationSettingRepository.existsById(command.notificationSettingId())) {
            throw new IllegalArgumentException("Notification setting not found with id: " + command.notificationSettingId());
        }
        notificationSettingRepository.deleteById(command.notificationSettingId());
    }
}
