package org.springframework.jenkins.cloud.common

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType

import org.springframework.jenkins.common.job.JdkConfig

/**
 * TBD
 *
 * @author Spencer Gibb
 */
@CompileStatic
@EqualsAndHashCode
class ReleaseTrain implements JdkConfig {

	// list of Projects
	// mapping of Project to branch
	Map<Project, String> projectsWithBranch = new LinkedHashMap<>()

	// version (calver 2022.0)
	// not just a string?
	String version

	// codename
	String codename

	// baseline boot generation 3.0, 2.6
	String bootVersion

	// compatibility boot generation
	List<String> bootCompatibility = []

	// jdk compatibility, first is baseline
	List<String> jdks = []

	void project(Project project, String branch) {
		projectsWithBranch.put(project, branch)
	}

	@Override
	String toString() {
		return """\
ReleaseTrain{
    projectBranch=$projectsWithBranch, 
    version='$version', 
    codename='$codename', 
    bootVersion='$bootVersion', 
    bootCompatibility=$bootCompatibility
    jdks=$jdks
}"""
	}

	static ReleaseTrain from(
			@DelegatesTo(value = ReleaseTrain, strategy = Closure.DELEGATE_FIRST)
			@ClosureParams(value = SimpleType.class,
					options = 'org.springframework.jenkins.cloud.common.ReleaseTrain')
			Closure closure) {
		def train = new ReleaseTrain();
		closure.delegate = train;
		closure()
		return train
	}

}