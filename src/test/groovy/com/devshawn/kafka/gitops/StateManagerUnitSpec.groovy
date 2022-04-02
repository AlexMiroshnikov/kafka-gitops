package com.devshawn.kafka.gitops

import com.devshawn.kafka.gitops.config.ManagerConfig
import com.devshawn.kafka.gitops.domain.state.CustomAclDetails
import com.devshawn.kafka.gitops.domain.state.DesiredStateFile
import com.devshawn.kafka.gitops.domain.state.ServiceDetails
import com.devshawn.kafka.gitops.domain.state.TopicDetails
import com.devshawn.kafka.gitops.domain.state.UserDetails
import com.devshawn.kafka.gitops.domain.state.settings.Settings
import com.devshawn.kafka.gitops.service.ParserService
import spock.lang.Specification
import com.devshawn.kafka.gitops.exception.ValidationException

class StateManagerUnitSpec extends Specification {

    void 'StateManager.getAndValidateStateFile throws an exception when amount of partitions set by default is less than minimum'() {
        setup:
        ManagerConfig managerConfig = Stub(ManagerConfig.class)
        ParserService parserService = Stub(ParserService.class)
        DesiredStateFile.Builder builder = new DesiredStateFile.Builder()
        DesiredStateFile desiredStateFile = builder.build()
        parserService.parseStateFile() >> desiredStateFile
        StateManager stateManager = new StateManager(managerConfig, parserService)

        when:
        stateManager.getAndValidateStateFile()

        then:
        ValidationException ex = thrown(ValidationException)
        ex.message == "The default partitions amount must be a positive integer."
    }
}
