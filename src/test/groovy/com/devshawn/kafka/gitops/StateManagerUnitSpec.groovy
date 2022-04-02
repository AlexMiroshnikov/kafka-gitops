package com.devshawn.kafka.gitops

import com.devshawn.kafka.gitops.config.ManagerConfig
import com.devshawn.kafka.gitops.domain.state.DesiredStateFile
import com.devshawn.kafka.gitops.domain.state.settings.Settings
import com.devshawn.kafka.gitops.domain.state.settings.SettingsTopics
import com.devshawn.kafka.gitops.domain.state.settings.SettingsTopicsDefaults
import com.devshawn.kafka.gitops.service.ParserService
import spock.lang.Specification
import com.devshawn.kafka.gitops.exception.ValidationException

class StateManagerUnitSpec extends Specification {

    void 'StateManager.getAndValidateStateFile throws an exception when amount of partitions set by default is less than minimum'() {
        setup:
        ManagerConfig managerConfig = Stub(ManagerConfig.class)
        ParserService parserService = Stub(ParserService.class)
        SettingsTopicsDefaults settingsTopicsDefaults = (new SettingsTopicsDefaults.Builder()).setPartitions(0).build()
        SettingsTopics settingsTopic = (new SettingsTopics.Builder()).setDefaults(settingsTopicsDefaults).build()
        Settings settings = (new Settings.Builder()).setTopics(settingsTopic).build()
        DesiredStateFile desiredStateFile = (new DesiredStateFile.Builder()).setSettings(settings).build()
        parserService.parseStateFile() >> desiredStateFile
        StateManager stateManager = new StateManager(managerConfig, parserService)

        when:
        stateManager.getAndValidateStateFile()

        then:
        ValidationException ex = thrown(ValidationException)
        ex.message == "The default partitions amount must be a positive integer."
    }
}
