package com.nrgserver.ergovision.statistics.aplication.internal.outboundservices.acl;

public record MonthlyProgressDTO(
        String month,            // Formato: "2025-12-01"
        Double averageScore
) {
}