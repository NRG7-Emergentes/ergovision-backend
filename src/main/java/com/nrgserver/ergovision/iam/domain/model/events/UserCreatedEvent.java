package com.nrgserver.ergovision.iam.domain.model.events;

import org.springframework.context.ApplicationEvent;

public class UserCreatedEvent extends ApplicationEvent {

    private Long userId;

    public UserCreatedEvent(Object source, Long userId) {
        super(source);
        this.userId = userId;
    }

}
