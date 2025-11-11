package com.nrgserver.ergovision.orchestrator.application.internal.commandservices;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.PostureSetting;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.CreatePostureSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.DeletePostureSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.UpdatePostureSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.services.PostureSettingCommandService;
import com.nrgserver.ergovision.orchestrator.infrastructure.persistence.jpa.repositories.PostureSettingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostureSettingCommandServiceImpl implements PostureSettingCommandService {
    
    private final PostureSettingRepository postureSettingRepository;
    
    public PostureSettingCommandServiceImpl(PostureSettingRepository postureSettingRepository) {
        this.postureSettingRepository = postureSettingRepository;
    }
    
    @Override
    @Transactional
    public Long handle(CreatePostureSettingCommand command) {
        if (postureSettingRepository.existsByUserId(command.userId())) {
            throw new IllegalArgumentException("Posture settings already exist for user: " + command.userId());
        }
        
        PostureSetting postureSetting = new PostureSetting(
                command.userId(),
                command.postureSensitivity(),
                command.shoulderAngleThreshold(),
                command.headAngleThreshold(),
                command.samplingFrequency(),
                command.showSkeleton(),
                command.postureThresholds()
        );
        
        PostureSetting savedSetting = postureSettingRepository.save(postureSetting);
        return savedSetting.getId();
    }
    
    @Override
    @Transactional
    public Long handle(UpdatePostureSettingCommand command) {
        PostureSetting postureSetting = postureSettingRepository.findById(command.settingId())
                .orElseThrow(() -> new IllegalArgumentException("Posture setting not found with id: " + command.settingId()));
        
        postureSetting.updateSettings(
                command.postureSensitivity(),
                command.shoulderAngleThreshold(),
                command.headAngleThreshold(),
                command.samplingFrequency(),
                command.showSkeleton(),
                command.postureThresholds()
        );
        
        PostureSetting updatedSetting = postureSettingRepository.save(postureSetting);
        return updatedSetting.getId();
    }
    
    @Override
    @Transactional
    public void handle(DeletePostureSettingCommand command) {
        if (!postureSettingRepository.existsById(command.settingId())) {
            throw new IllegalArgumentException("Posture setting not found with id: " + command.settingId());
        }
        
        postureSettingRepository.deleteById(command.settingId());
    }
}
