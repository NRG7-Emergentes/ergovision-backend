package com.nrgserver.ergovision.monitoring.aplication.internal.commandservices;

import com.nrgserver.ergovision.monitoring.domain.model.aggregates.MonitoringSession;
import com.nrgserver.ergovision.monitoring.domain.model.commands.CreateMonitoringSessionCommand;
import com.nrgserver.ergovision.monitoring.domain.model.commands.DeleteMonitoringSessionCommand;
import com.nrgserver.ergovision.monitoring.domain.model.commands.UpdateMonitoringSessionCommand;
import com.nrgserver.ergovision.monitoring.domain.services.MonitoringSessionCommandService;
import com.nrgserver.ergovision.monitoring.infrastructure.persistence.jpa.repositories.MonitoringSessionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MonitoringSessionCommandImpl implements MonitoringSessionCommandService {

    private final MonitoringSessionRepository monitoringSessionRepository;
    public MonitoringSessionCommandImpl(MonitoringSessionRepository monitoringSessionRepository) {
        this.monitoringSessionRepository = monitoringSessionRepository;
    }

    @Override
    public Long handle(CreateMonitoringSessionCommand command) {
        var monitoringSession = new MonitoringSession(command);
        try {
            this.monitoringSessionRepository.save(monitoringSession);
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR WHILE SAVING MONITORING SESSION: " + e.getMessage());
        }
        return monitoringSession.getId();
    }

    @Override
    public Optional<MonitoringSession> handle(UpdateMonitoringSessionCommand command) {
        var monitoringSessionId = command.monitoringSessionId();
        if(!this.monitoringSessionRepository.existsById(monitoringSessionId)) {
            throw new IllegalArgumentException("Monitoring Session with ID " + monitoringSessionId + " does not exist.");
        }
        var monitoringSessionToUpdate = this.monitoringSessionRepository.findById(monitoringSessionId).get();
        monitoringSessionToUpdate.updateMonitoringSessionInformation(
                command.userId(),
                command.startDate(),
                command.endDate(),
                command.score(),
                command.goodScore(),
                command.badScore(),
                command.goodPostureTime(),
                command.badPostureTime(),
                command.duration(),
                command.numberOfPauses(),
                command.averagePauseDuration()
        );
        try{
            var updatedMonitoringSession = this.monitoringSessionRepository.save(monitoringSessionToUpdate);
            return Optional.of(updatedMonitoringSession);
        } catch (Exception e){
            throw new IllegalArgumentException("ERROR WHILE Saving MONITORING SESSION: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteMonitoringSessionCommand command) {
        if (!this.monitoringSessionRepository.existsById(command.monitoringSessionId())) {
            throw new IllegalArgumentException("Monitoring Session with ID " + command.monitoringSessionId() + " does not exist.");
        }
        try {
            this.monitoringSessionRepository.deleteById(command.monitoringSessionId());
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR WHILE DELETING MONITORING SESSION: " + e.getMessage());
        }
    }
}
