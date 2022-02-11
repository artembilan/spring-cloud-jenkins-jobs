package org.springframework.jenkins.cloud.common

import spock.lang.Specification

import org.springframework.jenkins.common.job.JdkConfig

import static org.assertj.core.api.Assertions.assertThat

class ReleaseTrainsSpec extends Specification implements JdkConfig {

	def 'define all release trains'() {
		expect:
			ReleaseTrains.ALL_BY_CODENAME.keySet().containsAll(["Kilburn", "Jubilee", "Ilford", "Hoxton"])
	}

	def 'define a release train'() {
		expect:
			ReleaseTrains.JUBILEE.version == "2021.0"
			ReleaseTrains.JUBILEE.codename == "Jubilee"
			ReleaseTrains.JUBILEE.bootVersion == "2.6.x"
			ReleaseTrains.JUBILEE.version == "2021.0"
			ReleaseTrains.JUBILEE.jdks.containsAll([jdk8(), jdk11(), jdk17()])
			ReleaseTrains.JUBILEE.projectsWithBranch.size() == 2
	}

}
