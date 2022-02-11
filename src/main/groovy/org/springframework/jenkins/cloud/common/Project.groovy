package org.springframework.jenkins.cloud.common

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType

@CompileStatic
@EqualsAndHashCode
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