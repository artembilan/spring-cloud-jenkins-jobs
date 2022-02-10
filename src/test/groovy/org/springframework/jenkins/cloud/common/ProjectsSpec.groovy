package org.springframework.jenkins.cloud.common

import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

class ProjectsSpec extends Specification {

	def 'define all projects'() {
		expect:
			assertThat(Projects.ALL_BY_NAME).containsKeys("spring-cloud-build", "spring-cloud-commons")
	}

	def 'define a project'() {
		expect:
			assertThat(Projects.BUILD.name).isEqualTo("spring-cloud-build")
			assertThat(Projects.BUILD.org).isEqualTo("spring-cloud")
			assertThat(Projects.BUILD.buildSystem).isEqualTo(Project.BuildSystem.MAVEN)
			assertThat(Projects.BUILD.hasTests).isEqualTo(false)
			assertThat(Projects.BUILD.checkBootCompatibility).isEqualTo(true)
	}

}
