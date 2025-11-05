package com.nrgserver.ergovision.notifications.interfaces.rest.transform;

import com.nrgserver.ergovision.notifications.domain.model.commands.SendNotificationCommand;
import com.nrgserver.ergovision.notifications.domain.model.commands.CreateNotificationCommand;

import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

public class SendNotificationCommandFromEntityAssembler {

    public static SendNotificationCommand toCommand(CreateNotificationCommand req) {
        if (req == null) {
            throw new IllegalArgumentException("CreateNotificationCommand cannot be null");
        }

        // Valores obtenidos mediante reflexión
        String typeStr = safeToString(get(req, Object.class, "type"));
        String channelStr = safeToString(get(req, Object.class, "channel"));
        List<String> preferred = safeList(get(req, java.util.List.class, "preferredChannels", "preferredChannel"));
        Long userId = get(req, Long.class, "userId", "user");
        String title = safeToString(get(req, String.class, "title"));
        String message = safeToString(get(req, String.class, "message"));

        // ⚙️ Boolean protegido contra null
        Boolean doNotDisturb = get(req, Boolean.class, "doNotDisturb", "doNotDisturbFlag", "dnd");
        if (doNotDisturb == null) doNotDisturb = Boolean.FALSE;

        // Construcción final del comando
        return new SendNotificationCommand(
                null,           // id generado en capa de aplicación
                userId != null ? userId : 0L, // default seguro
                title != null ? title : "(sin título)",
                message != null ? message : "",
                typeStr != null ? typeStr : "GENERAL",
                channelStr != null ? channelStr : "DEFAULT",
                preferred != null ? preferred : Collections.emptyList(),
                doNotDisturb
        );
    }

    /**
     * Reflection helper: intenta los nombres dados como método directo, getX o isX.
     */
    private static <T> T get(CreateNotificationCommand src, Class<T> type, String... names) {
        if (src == null) return null;
        for (String name : names) {
            if (name == null || name.isEmpty()) continue;
            try {
                java.lang.reflect.Method m = src.getClass().getMethod(name);
                Object v = m.invoke(src);
                if (v != null) return type.cast(v);
            } catch (NoSuchMethodException | IllegalAccessException | java.lang.reflect.InvocationTargetException ignored) {
            }
            String cap = Character.toUpperCase(name.charAt(0)) + name.substring(1);
            String[] prefixes = {"get", "is"};
            for (String p : prefixes) {
                try {
                    java.lang.reflect.Method m = src.getClass().getMethod(p + cap);
                    Object v = m.invoke(src);
                    if (v != null) return type.cast(v);
                } catch (NoSuchMethodException | IllegalAccessException | java.lang.reflect.InvocationTargetException ignored) {
                }
            }
        }
        return null;
    }

    // Conversores seguros
    private static String safeToString(Object o) {
        return o == null ? null : o.toString();
    }

    @SuppressWarnings("unchecked")
    private static List<String> safeList(Object o) {
        if (o == null) return Collections.emptyList();
        try {
            return (List<String>) o;
        } catch (ClassCastException ex) {
            try {
                List<?> raw = (List<?>) o;
                return raw.stream().map(Object::toString).collect(Collectors.toList());
            } catch (Exception e) {
                return Collections.emptyList();
            }
        }
    }
}
