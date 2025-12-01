package com.nrgserver.ergovision.statistics.aplication.internal.outboundservices.acl;

import com.nrgserver.ergovision.monitoring.interfaces.acl.MonitoringSessionContextFacade;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ExternalMonitoringSessionService {
    private final MonitoringSessionContextFacade monitoringSessionContextFacade;

    public ExternalMonitoringSessionService(MonitoringSessionContextFacade monitoringSessionContextFacade) {
        this.monitoringSessionContextFacade = monitoringSessionContextFacade;
    }

    public List<MonthlyProgressDTO> fetchMonthlyProgressByUserId(Long userId) {
        var sessions = monitoringSessionContextFacade.getMonitoringSessionsByUserId(userId);
        if (sessions.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, Double> monthlyData = sessions.stream()
                .collect(Collectors.groupingBy(
                        session -> {
                            Instant instant = session.getStartDate().toInstant();
                            YearMonth yearMonth = YearMonth.from(
                                    instant.atZone(ZoneId.systemDefault())
                            );
                            return yearMonth.toString(); // Formato: "2025-12"
                        },
                        Collectors.averagingDouble(com.nrgserver.ergovision.monitoring.domain.model.aggregates.MonitoringSession::getScore)
                ));

        return monthlyData.entrySet().stream()
                .map(entry -> new MonthlyProgressDTO(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(MonthlyProgressDTO::month))
                .toList();
    }

    public List<DailyProgressDTO> fetchDailyProgressByUserId(Long userId) {
        var sessions = monitoringSessionContextFacade.getMonitoringSessionsByUserId(userId);
        if (sessions.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, Double> dailyData = sessions.stream()
                .collect(Collectors.groupingBy(
                        session -> {
                            Instant instant = session.getStartDate().toInstant();
                            LocalDate date = instant.atZone(ZoneId.systemDefault())
                                    .toLocalDate();
                            return date.toString(); // Formato: "2025-12-01"
                        },
                        Collectors.averagingDouble(com.nrgserver.ergovision.monitoring.domain.model.aggregates.MonitoringSession::getScore)
                ));

        return dailyData.entrySet().stream()
                .map(entry -> new DailyProgressDTO(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(DailyProgressDTO::date))
                .toList();
    }

    public Optional<Double> fetchGlobalAverageScoreByUserId(Long userId) {
        var sessions = monitoringSessionContextFacade.getMonitoringSessionsByUserId(userId);
        if (sessions.isEmpty()) {
            return Optional.empty();
        }
        double totalScore = sessions.stream()
                .mapToDouble(session -> session.getScore())
                .sum();
        double averageScore = totalScore / sessions.size();
        return Optional.of(averageScore);
    }

    public Optional<Double> fetchAverageSessionTimeMinutesByUserId(Long userId) {
         var sessions = monitoringSessionContextFacade.getMonitoringSessionsByUserId(userId);
         if (sessions.isEmpty()) {
              return Optional.empty();
         }
         double totalMinutes = sessions.stream()
                .mapToDouble(session -> session.getDuration())
                .sum();
         double averageMinutes = totalMinutes / sessions.size();
         return Optional.of(averageMinutes);
   }

    public Optional<Double> fetchAveragePausesPerSessionByUserId(Long userId) {
        var sessions = monitoringSessionContextFacade.getMonitoringSessionsByUserId(userId);
        if (sessions.isEmpty()) {
            return Optional.empty();
        }
        double totalPauses = sessions.stream()
                .mapToDouble(session -> session.getNumberOfPauses())
                .sum();
        double averagePauses = totalPauses / sessions.size();
        return Optional.of(averagePauses);
    }

    public Optional<Double> fetchTotalMonitoredHoursByUserId(Long userId) {
        var sessions = monitoringSessionContextFacade.getMonitoringSessionsByUserId(userId);
        if (sessions.isEmpty()) {
            return Optional.empty();
        }
        double totalMinutes = sessions.stream()
                .mapToDouble(session -> session.getDuration())
                .sum();
        double totalHours = totalMinutes / 60.0;
        return Optional.of(totalHours);
    }

}
