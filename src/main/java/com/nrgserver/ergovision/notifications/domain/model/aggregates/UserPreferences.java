package com.nrgserver.ergovision.notifications.domain.model.aggregates;

import com.nrgserver.ergovision.notifications.domain.model.valueobjects.DeliveryChannel;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.FrequencyType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UserPreferences {
    // Getters
    @Getter
    private Long userId;
    private List<DeliveryChannel> preferredChannels = new ArrayList<>();
    @Getter
    private FrequencyType frequency = FrequencyType.IMMEDIATE;
    @Getter
    private boolean doNotDisturb = false;

    public UserPreferences(Long userId, List<DeliveryChannel> preferredChannels, FrequencyType frequency, boolean doNotDisturb) {
        this.userId = userId;
        if (preferredChannels != null) this.preferredChannels = new ArrayList<>(preferredChannels);
        this.frequency = frequency == null ? FrequencyType.IMMEDIATE : frequency;
        this.doNotDisturb = doNotDisturb;
    }

    public UserPreferences getPreferences() {
        return this;
    }

    public void updatePreferences(UserPreferences other) {
        if (other == null) return;
        if (Objects.nonNull(other.preferredChannels)) {
            this.preferredChannels = new ArrayList<>(other.preferredChannels);
        }
        this.frequency = other.frequency;
        this.doNotDisturb = other.doNotDisturb;
    }

    public boolean isChannelAllowed(DeliveryChannel channel) {
        if (doNotDisturb) return false;
        return preferredChannels.contains(channel);
    }

    public List<DeliveryChannel> getPreferredChannels() { return Collections.unmodifiableList(preferredChannels); }

}