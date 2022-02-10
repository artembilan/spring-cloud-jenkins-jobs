package org.springframework.jenkins.cloud.common

import groovy.transform.CompileStatic
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType

import org.springframework.jenkins.common.job.JdkConfig

/**
 * TBD
 *
 * @author Spencer Gibb
 */
@CompileStatic
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

	def project(Project project, String branch) {
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

	boolean equals(o) {
		if (this.is(o)) return true
		if (getClass() != o.class) return false

		ReleaseTrain that = (ReleaseTrain) o

		if (bootCompatibility != that.bootCompatibility) return false
		if (bootVersion != that.bootVersion) return false
		if (codename != that.codename) return false
		if (jdks != that.jdks) return false
		if (projectsWithBranch != that.projectsWithBranch) return false
		if (version != that.version) return false

		return true
	}

	int hashCode() {
		int result
		result = (projectsWithBranch != null ? projectsWithBranch.hashCode() : 0)
		result = 31 * result + (jdks != null ? jdks.hashCode() : 0)
		result = 31 * result + (version != null ? version.hashCode() : 0)
		result = 31 * result + (codename != null ? codename.hashCode() : 0)
		result = 31 * result + (bootVersion != null ? bootVersion.hashCode() : 0)
		result = 31 * result + (bootCompatibility != null ? bootCompatibility.hashCode() : 0)
		return result
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