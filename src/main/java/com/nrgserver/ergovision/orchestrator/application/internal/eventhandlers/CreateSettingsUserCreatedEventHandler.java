package com.nrgserver.ergovision.orchestrator.application.internal.eventhandlers;
import com.nrgserver.ergovision.iam.domain.model.events.UserCreatedEvent;
import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.AlertSetting;
import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.PostureSetting;
import com.nrgserver.ergovision.orchestrator.infrastructure.persistence.jpa.repositories.AlertSettingRepository;
import com.nrgserver.ergovision.orchestrator.infrastructure.persistence.jpa.repositories.PostureSettingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateSettingsUserCreatedEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(CreateSettingsUserCreatedEventHandler.class);

    private final AlertSettingRepository alertSettingRepository;
    private final PostureSettingRepository postureSettingRepository;

    public CreateSettingsUserCreatedEventHandler(AlertSettingRepository alertSettingRepository, PostureSettingRepository postureSettingRepository) {
        this.alertSettingRepository = alertSettingRepository;
        this.postureSettingRepository = postureSettingRepository;
    }

    @EventListener(UserCreatedEvent.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void on(UserCreatedEvent event){
        logger.info("UserCreatedEvent received for userId: {}", event.getUserId());

        try {
            var defaultPosture = new PostureSetting(event.getUserId());
            var defaultAlert = new AlertSetting(event.getUserId());

            postureSettingRepository.save(defaultPosture);
            logger.info("Default posture setting created for userId: {}", event.getUserId());

            alertSettingRepository.save(defaultAlert);
            logger.info("Default alert setting created for userId: {}", event.getUserId());

        } catch (Exception e) {
            logger.error("ERROR WHILE Saving POSTURE AND ALERT SETTINGS for userId: {}", event.getUserId(), e);
            throw new IllegalArgumentException("ERROR WHILE Saving POSTURE AND ALERT SETTINGS: " + e.getMessage(), e);
        }
    }

}
