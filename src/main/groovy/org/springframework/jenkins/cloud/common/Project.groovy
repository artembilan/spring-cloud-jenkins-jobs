package org.springframework.jenkins.cloud.common

import groovy.transform.CompileStatic
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType

@CompileStatic
class Project {

	enum BuildSystem { MAVEN, GRADLE, BOTH }

	// name spring-cloud-build
	String name;

	// org default to spring-cloud, allow spring-projects-experimental
	String org = "spring-cloud"

	// boolean has tests?
	boolean hasTests = true

	// enum build system, maven, gradle, both
	BuildSystem buildSystem = BuildSystem.MAVEN

	// included in boot compatibility?
	boolean checkBootCompatibility = true

	// custom build stuff (branch param, jdk param)
		// see CustomJob

	// behaviors
		// 2 projects vaultjava8, vault extends vaultjava8 and overrides


	@Override
	String toString() {
		return """\
Project{
    name='$name', 
    org='$org', 
    hasTests=$hasTests, 
    buildSystem=$buildSystem, 
    checkBootCompatibility=$checkBootCompatibility
}"""
	}

	boolean equals(o) {
		if (this.is(o)) return true
		if (getClass() != o.class) return false

		Project project = (Project) o

		if (checkBootCompatibility != project.checkBootCompatibility) return false
		if (hasTests != project.hasTests) return false
		if (buildSystem != project.buildSystem) return false
		if (name != project.name) return false
		if (org != project.org) return false

		return true
	}

	int hashCode() {
		int result
		result = (name != null ? name.hashCode() : 0)
		result = 31 * result + (org != null ? org.hashCode() : 0)
		result = 31 * result + (hasTests ? 1 : 0)
		result = 31 * result + (buildSystem != null ? buildSystem.hashCode() : 0)
		result = 31 * result + (checkBootCompatibility ? 1 : 0)
		return result
	}

	static Project from(
			@DelegatesTo(value = Project, strategy = Closure.DELEGATE_FIRST)
			@ClosureParams(value = SimpleType.class,
					options = 'org.springframework.jenkins.cloud.common.Project')
					Closure closure) {
		def project = new Project();
		closure.delegate = project;
		closure()
		return project
	}
}