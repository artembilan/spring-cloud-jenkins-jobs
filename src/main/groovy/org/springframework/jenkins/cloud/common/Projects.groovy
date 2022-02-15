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

	public static final Project BUS = Project.from {
		name = "spring-cloud-bus"
	}

	public static final Project CIRCUITBREAKER = Project.from {
		name = "spring-cloud-circuitbreaker"
	}

	public static final Project CLI = Project.from {
		name = "spring-cloud-cli"
	}

	public static final Project CLOUDFOUNDRY = Project.from {
		name = "spring-cloud-cloudfoundry"
	}

	public static final Project COMMONS = Project.from {
		name = "spring-cloud-commons"
	}

	public static final Project CONFIG = Project.from {
		name = "spring-cloud-config"
	}

	public static final Project CONSUL = Project.from {
		name = "spring-cloud-consul"
	}

	public static final Project CONTRACT = Project.from {
		name = "spring-cloud-contract"
	}

	public static final Project CORE_TESTS = Project.from {
		name = "spring-cloud-core-tests"
	}

	public static final Project GATEWAY = Project.from {
		name = "spring-cloud-gateway"
	}

	public static final Project KUBERNETES = Project.from {
		name = "spring-cloud-kubernetes"
	}

	public static final Project NETFLIX = Project.from {
		name = "spring-cloud-netflix"
	}

	public static final Project OPENFEIGN = Project.from {
		name = "spring-cloud-openfeign"
	}

	public static final Project RELEASE = Project.from {
		name = "spring-cloud-release"
		hasTests = false
	}

	public static final Project SLEUTH = Project.from {
		name = "spring-cloud-sleuth"
	}

	public static final Project TASK = Project.from {
		name = "spring-cloud-task"
	}

	public static final Project VAULT = Project.from {
		name = "spring-cloud-vault"
	}

	public static final Project ZOOKEEPER = Project.from {
		name = "spring-cloud-zookeeper"
	}

	public static final Project SQUARE = Project.from {
		name = "spring-cloud-square"
		org = "spring-projects-experimental"
	}

	public static final Project SLEUTH_OTEL = Project.from {
		name = "spring-cloud-sleuth-otel"
		org = "spring-projects-experimental"
	}

	public static final List<Project> ALL = [BUILD, BUS, CIRCUITBREAKER, CLI, CLOUDFOUNDRY, COMMONS, CONFIG,
											 CONSUL, CONTRACT, CORE_TESTS, GATEWAY, KUBERNETES, NETFLIX, OPENFEIGN,
											 RELEASE, SLEUTH, TASK, VAULT, ZOOKEEPER, SQUARE, SLEUTH_OTEL]

	public static final Map<String, Project> ALL_BY_NAME = ALL.collectEntries { [it.name, it]}

}
