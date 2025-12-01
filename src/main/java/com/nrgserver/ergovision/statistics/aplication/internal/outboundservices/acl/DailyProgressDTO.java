package com.nrgserver.ergovision.statistics.aplication.internal.outboundservices.acl;

public record DailyProgressDTO(
        String date,            // Formato: "2025-12-01"
        Double averageScore
) {
}

