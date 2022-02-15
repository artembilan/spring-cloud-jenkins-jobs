package org.springframework.jenkins.cloud.common

import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

class ProjectsSpec extends Specification {

	def 'define all projects'() {
		expect:
			Projects.ALL_BY_NAME.keySet().containsAll(["spring-cloud-build", "spring-cloud-commons"])
			Projects.ALL_BY_NAME.size() == 21
	}

	def 'define a project'() {
		expect:
			Projects.BUILD.name == "spring-cloud-build"
			Projects.BUILD.org == "spring-cloud"
			Projects.BUILD.buildSystem == Project.BuildSystem.MAVEN
			!Projects.BUILD.hasTests
			Projects.BUILD.checkBootCompatibility
	}

}
