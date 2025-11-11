package com.nrgserver.ergovision.notifications.application.internal.commandservices;

import com.nrgserver.ergovision.notifications.domain.model.commands.SendNotificationCommand;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/*
 Service responsible for handling the payload that used to live in the CreateNotificationCommand record.
 Keeps validation and normalization out of the record and inside the application service layer.
*/
@Service
public class CreateNotificationCommandServiceImpl {

    // payload record matching the previous CreateNotificationCommand structure
    public record CreateNotificationPayload(
            Long userId,
            String title,
            String message,
            String type,
            String channel,
            List<String> preferredChannels,
            boolean doNotDisturb
    ) {}

    // Validates/normalizes and returns a SendNotificationCommand ready for the command pipeline.
    public SendNotificationCommand buildSendCommand(CreateNotificationPayload payload) {
        Objects.requireNonNull(payload, "payload");
        Long userId = Objects.requireNonNull(payload.userId(), "userId");
        String title = Objects.requireNonNull(payload.title(), "title");
        String message = Objects.requireNonNull(payload.message(), "message");
        String type = Objects.requireNonNull(payload.type(), "type");

        List<String> normalizedPreferredChannels = payload.preferredChannels() == null
                ? List.of()
                : List.copyOf(payload.preferredChannels());

        return new SendNotificationCommand(
                null, // id intentionally left null; caller/command service will generate if needed
                userId,
                title,
                message,
                type,
                payload.channel(),
                normalizedPreferredChannels,
                payload.doNotDisturb()
        );
    }

    // ...existing code...
}
