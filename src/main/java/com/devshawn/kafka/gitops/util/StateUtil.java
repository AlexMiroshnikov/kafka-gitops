package com.devshawn.kafka.gitops.util;

import com.devshawn.kafka.gitops.domain.state.DesiredStateFile;
import com.devshawn.kafka.gitops.domain.state.settings.SettingsTopicsDefaults;

import java.util.Map;
import java.util.Optional;

public class StateUtil {

    public static Optional<Integer> fetchReplication(DesiredStateFile desiredStateFile) {
        SettingsTopicsDefaults defaults = getSettingsTopicsDefaults(desiredStateFile);

        if (defaults != null) {
            return defaults.getReplication();
        }

        return Optional.empty();
    }

    public static Optional<Integer> fetchPartitions(DesiredStateFile desiredStateFile) {
        SettingsTopicsDefaults defaults = getSettingsTopicsDefaults(desiredStateFile);

        if (defaults != null) {
            return defaults.getPartitions();
        }

        return Optional.empty();
    }

    public static Optional<Map<String, String>> fetchConfigs(DesiredStateFile desiredStateFile) {
        SettingsTopicsDefaults defaults = getSettingsTopicsDefaults(desiredStateFile);

        if (defaults != null) {
            return defaults.getConfigs();
        }

        return Optional.empty();
    }

    public static boolean areSettingsTopicsDefaultsPresent(DesiredStateFile desiredStateFile) {
        return (
            desiredStateFile.getSettings().isPresent() &&
            desiredStateFile.getSettings().get().getTopics().isPresent() &&
            desiredStateFile.getSettings().get().getTopics().get().getDefaults().isPresent()
        );
    }

    private static SettingsTopicsDefaults getSettingsTopicsDefaults(DesiredStateFile desiredStateFile) {
        if (areSettingsTopicsDefaultsPresent(desiredStateFile)) {
            return desiredStateFile.getSettings().get().getTopics().get().getDefaults().get();
        }
        return null;
    }

    public static boolean isDescribeTopicAclEnabled(DesiredStateFile desiredStateFile) {
        return desiredStateFile.getSettings().isPresent() && desiredStateFile.getSettings().get().getServices().isPresent()
                && desiredStateFile.getSettings().get().getServices().get().getAcls().isPresent()
                && desiredStateFile.getSettings().get().getServices().get().getAcls().get().getDescribeTopicEnabled().isPresent()
                && desiredStateFile.getSettings().get().getServices().get().getAcls().get().getDescribeTopicEnabled().get();
    }
}
