package com.nrgserver.ergovision.orchestrator.application.internal.queryservices;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.NotificationSetting;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetNotificationSettingByIdQuery;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetUserNotificationSettingQuery;
import com.nrgserver.ergovision.orchestrator.domain.services.NotificationSettingQueryService;
import com.nrgserver.ergovision.orchestrator.infrastructure.persistence.jpa.repositories.NotificationSettingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationSettingQueryServiceImpl implements NotificationSettingQueryService {

    private final NotificationSettingRepository notificationSettingRepository;

    public NotificationSettingQueryServiceImpl(NotificationSettingRepository notificationSettingRepository) {
        this.notificationSettingRepository = notificationSettingRepository;
    }

    @Override
    public Optional<NotificationSetting> handle(GetUserNotificationSettingQuery query) {
        return notificationSettingRepository.findByUserId(query.userId());
    }

    @Override
    public Optional<NotificationSetting> handle(GetNotificationSettingByIdQuery query) {
        return notificationSettingRepository.findById(query.notificationSettingId());
    }
}
