package com.nrgserver.ergovision.notifications.interfaces.rest.transform;

import com.nrgserver.ergovision.notifications.domain.model.commands.SendNotificationCommand;
import com.nrgserver.ergovision.notifications.domain.model.commands.CreateNotificationCommand;

import java.util.List;
import java.util.Collections;

public class SendNotificationCommandFromEntityAssembler {
    public static SendNotificationCommand toCommand(CreateNotificationCommand req) {
        // id intentionally left null; command service will generate if needed
        String typeStr = safeToString(get(req, Object.class, "type"));
        String channelStr = safeToString(get(req, Object.class, "channel"));
        List<String> preferred = safeList(get(req, java.util.List.class, "preferredChannels", "preferredChannel"));

        return new SendNotificationCommand(
            null,
            get(req, Long.class, "userId", "user"),
            get(req, String.class, "title"),
            get(req, String.class, "message"),
            typeStr,
            channelStr,
            preferred,
            get(req, Boolean.class, "doNotDisturb", "doNotDisturbFlag", "dnd")
        );
    }

    // Reflection helper: try provided candidate names as-is, then try getX/isX variants.
    private static <T> T get(CreateNotificationCommand src, Class<T> type, String... names) {
        if (src == null) return null;
        for (String name : names) {
            if (name == null || name.isEmpty()) continue;
            // try method named exactly
            try {
                java.lang.reflect.Method m = src.getClass().getMethod(name);
                Object v = m.invoke(src);
                if (v != null) return type.cast(v);
            } catch (NoSuchMethodException | IllegalAccessException | java.lang.reflect.InvocationTargetException ignored) {
            }
            // try getX / isX
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

    // safe converters
    private static String safeToString(Object o) {
        return o == null ? null : o.toString();
    }

    @SuppressWarnings("unchecked")
    private static List<String> safeList(Object o) {
        if (o == null) return Collections.emptyList();
        try {
            return (List<String>) o;
        } catch (ClassCastException ex) {
            // try to convert elements to strings
            try {
                List<?> raw = (List<?>) o;
                return raw.stream().map(Object::toString).collect(java.util.stream.Collectors.toList());
            } catch (Exception e) {
                return Collections.emptyList();
            }
        }
    }
}