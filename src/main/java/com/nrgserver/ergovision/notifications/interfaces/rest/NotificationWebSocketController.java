package com.nrgserver.ergovision.notifications.interfaces.rest;

import com.nrgserver.ergovision.notifications.application.internal.commandservices.NotificationCommandServiceImpl;
import com.nrgserver.ergovision.notifications.application.internal.queryservices.NotificationQueryServiceImpl;
import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;
import com.nrgserver.ergovision.notifications.domain.model.commands.CreateNotificationCommand;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class NotificationWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationCommandServiceImpl commandService;
    private final NotificationQueryServiceImpl queryService;

    public NotificationWebSocketController(
            SimpMessagingTemplate messagingTemplate,
            NotificationCommandServiceImpl commandService,
            NotificationQueryServiceImpl queryService) {

        this.messagingTemplate = messagingTemplate;
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @MessageMapping("/notify")
    public void receiveNotification(CreateNotificationCommand command) {
        System.out.println("🔥 [WS] Received command - Title: " + command.title() +
                ", Message: " + command.message() +
                ", UserId from frontend: " + command.userId());

        try {
            // 🔥 SOLUCIÓN TEMPORAL: Usar el userId del frontend directamente
            Long actualUserId = command.userId();

            if (actualUserId == null || actualUserId == 0) {
                System.err.println("🔥 [WS] ❌ Invalid userId from frontend: " + actualUserId);
                return;
            }

            System.out.println("🔥 [WS] ✅ Using frontend userId: " + actualUserId);

            CreateNotificationCommand finalCommand = new CreateNotificationCommand(
                    actualUserId,
                    command.title(),
                    command.message(),
                    command.type() != null ? command.type() : "INFO",
                    command.channel() != null ? command.channel() : "WEB",
                    command.preferredChannels() != null ? command.preferredChannels() : List.of("WEB"),
                    command.doNotDisturb() != null ? command.doNotDisturb() : false
            );

            // Guardar en BD
            Long id = commandService.handle(finalCommand);
            System.out.println("🔥 [WS] ✅ Notification saved with ID: " + id);

            // Broadcast
            queryService.getById(id).ifPresent(saved -> {
                System.out.println("🔥 [WS] 📢 Broadcasting for userId: " + saved.getUserId());
                messagingTemplate.convertAndSend("/topic/notifications", saved);
            });

        } catch (Exception e) {
            System.err.println("🔥 [WS] ❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 🔥 ESTRATEGIA CON FALLBACKS ROBUSTOS
     */
    private Long getAuthenticatedUserIdWithFallback(StompHeaderAccessor headerAccessor, Long frontendUserId) {
        // 🔥 ESTRATEGIA 1: Autenticación WebSocket
        try {
            Authentication auth = (Authentication) headerAccessor.getUser();
            if (auth != null && auth.isAuthenticated()) {
                System.out.println("🔥 [Auth] User authenticated: " + auth.getName());

                // Intentar extraer userId del principal
                try {
                    Long userId = Long.parseLong(auth.getName());
                    System.out.println("🔥 [Auth] ✅ Using authenticated userId: " + userId);
                    return userId;
                } catch (NumberFormatException e) {
                    System.err.println("🔥 [Auth] Username is not numeric: " + auth.getName());
                }
            }
        } catch (Exception e) {
            System.err.println("🔥 [Auth] Error in authentication: " + e.getMessage());
        }

        // 🔥 ESTRATEGIA 2: Usar userId del frontend (si es válido)
        if (frontendUserId != null && frontendUserId > 0) {
            System.out.println("🔥 [Auth] ⚠️  Using frontend userId as fallback: " + frontendUserId);
            return frontendUserId;
        }

        // 🔥 ESTRATEGIA 3: Fallback hardcodeado (TEMPORAL - solo para desarrollo)
        System.out.println("🔥 [Auth] ⚠️  Using hardcoded fallback userId: 123456");
        return 123456L;
    }

    /**
     * 🔥 MÉTODO ROBUSTO para obtener userId autenticado
     */
    private Long getAuthenticatedUserId(StompHeaderAccessor headerAccessor) {
        try {
            Authentication auth = (Authentication) headerAccessor.getUser();

            if (auth == null) {
                System.err.println("🔥 [Auth] Authentication is NULL");
                return null;
            }

            System.out.println("🔥 [Auth] User authenticated: " + auth.isAuthenticated());
            System.out.println("🔥 [Auth] Principal: " + auth.getPrincipal());
            System.out.println("🔥 [Auth] Name: " + auth.getName());
            System.out.println("🔥 [Auth] Details: " + auth.getDetails());

            // 🔥 ESTRATEGIA 1: UserDetails con userId
            if (auth.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails) {
                Object principal = auth.getPrincipal();
                System.out.println("🔥 [Auth] Principal class: " + principal.getClass().getName());

                // Si tu UserDetails tiene un método para obtener userId
                try {
                    // Intentar usar reflexión para encontrar userId
                    java.lang.reflect.Method getUserIdMethod = principal.getClass().getMethod("getUserId");
                    Object userIdObj = getUserIdMethod.invoke(principal);
                    if (userIdObj instanceof Long) {
                        System.out.println("🔥 [Auth] ✅ Found userId via getUserId(): " + userIdObj);
                        return (Long) userIdObj;
                    }
                } catch (Exception e) {
                    System.out.println("🔥 [Auth] No getUserId() method available");
                }
            }

            // 🔥 ESTRATEGIA 2: El username ES el userId
            try {
                Long userId = Long.parseLong(auth.getName());
                System.out.println("🔥 [Auth] ✅ Using username as userId: " + userId);
                return userId;
            } catch (NumberFormatException e) {
                System.err.println("🔥 [Auth] Username is not numeric: " + auth.getName());
            }

            // 🔥 ESTRATEGIA 3: Buscar en detalles
            if (auth.getDetails() instanceof java.util.Map) {
                java.util.Map<?, ?> details = (java.util.Map<?, ?>) auth.getDetails();
                System.out.println("🔥 [Auth] Details map keys: " + details.keySet());

                for (Object key : details.keySet()) {
                    if (key.toString().toLowerCase().contains("user") ||
                            key.toString().toLowerCase().contains("id")) {
                        Object value = details.get(key);
                        System.out.println("🔥 [Auth] Checking key '" + key + "': " + value);

                        if (value instanceof Number) {
                            Long userId = ((Number) value).longValue();
                            if (userId > 0) {
                                System.out.println("🔥 [Auth] ✅ Found userId in details: " + userId);
                                return userId;
                            }
                        }
                    }
                }
            }

            System.err.println("🔥 [Auth] ❌ No valid userId found in authentication");
            return null;

        } catch (Exception e) {
            System.err.println("🔥 [Auth] ❌ Error getting userId: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}