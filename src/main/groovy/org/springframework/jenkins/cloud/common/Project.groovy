package org.springframework.jenkins.cloud.common

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@CompileStatic
@EqualsAndHashCode
@ToString
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
	String customBuildCommand(String branch, String jdkVersion, boolean upload) {
		return null;
	}
	String labelExpression(String jdkVersion) {
		return null;
	}

	// behaviors
		// 2 projects vaultjava8, vault extends vaultjava8 and overrides

}