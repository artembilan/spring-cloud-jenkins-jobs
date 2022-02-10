package org.springframework.jenkins.cloud.common


import groovy.transform.CompileStatic

/**
 * TBD
 *
 * @author Spencer Gibb
 */
@CompileStatic
class Projects {

	public static final Project BUILD = Project.from {
		name = "spring-cloud-build"
		hasTests = false
	}

	public static final Project COMMONS = Project.from {
		name = "spring-cloud-commons"
	}

	public static final List<Project> ALL = [BUILD, COMMONS]

	public static final Map<String, Project> ALL_BY_NAME = ALL.collectEntries { [it.name, it]}

}
