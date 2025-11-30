package com.nrgserver.ergovision.orchestrator.domain.model.aggregates;

import com.nrgserver.ergovision.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class NotificationSetting extends AuditableAbstractAggregateRoot<NotificationSetting> {

    private Long userId;
    private Boolean emailNotifications;


    protected NotificationSetting() {
    }

    public NotificationSetting(Long userId) {
        this.userId = userId;
        this.emailNotifications = true;
    }

    public NotificationSetting(Long userId, Boolean emailNotifications) {
        this.userId = userId;
        this.emailNotifications = emailNotifications;
    }



    public void updateSettings(Boolean emailNotifications) {
        this.emailNotifications = emailNotifications;
    }
}
