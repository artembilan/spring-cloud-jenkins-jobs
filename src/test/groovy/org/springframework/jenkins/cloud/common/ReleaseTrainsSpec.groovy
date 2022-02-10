package org.springframework.jenkins.cloud.common

import spock.lang.Specification

import org.springframework.jenkins.common.job.JdkConfig

import static org.assertj.core.api.Assertions.assertThat

class ReleaseTrainsSpec extends Specification implements JdkConfig {

	def 'define all release trains'() {
		expect:
			assertThat(ReleaseTrains.ALL_BY_CODENAME).containsKeys("Kilburn", "Jubilee", "Ilford", "Hoxton")
	}

	def 'define a release train'() {
		expect:
			assertThat(ReleaseTrains.JUBILEE.version).isEqualTo("2021.0")
			assertThat(ReleaseTrains.JUBILEE.codename).isEqualTo("Jubilee")
			assertThat(ReleaseTrains.JUBILEE.bootVersion).isEqualTo("2.6.x")
			assertThat(ReleaseTrains.JUBILEE.jdks).containsExactly(jdk8(), jdk11(), jdk17())
			assertThat(ReleaseTrains.JUBILEE.projectsWithBranch).hasSize(2)
	}

}
