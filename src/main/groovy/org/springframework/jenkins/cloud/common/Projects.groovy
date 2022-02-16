package org.springframework.jenkins.cloud.common


import groovy.transform.CompileStatic

/**
 * TBD
 *
 * @author Spencer Gibb
 */
@CompileStatic
class Projects {

	public static final Project BUILD = new Project(
		name: "spring-cloud-build",
		hasTests: false
	)

	public static final Project BUS = new Project(
		name: "spring-cloud-bus"
	)

	public static final Project CIRCUITBREAKER = new Project(
		name: "spring-cloud-circuitbreaker"
	)

	public static final Project CLI = new Project(
		name: "spring-cloud-cli"
	)

	public static final Project CLOUDFOUNDRY = new Project(
		name: "spring-cloud-cloudfoundry"
	)

	public static final Project COMMONS = new Project(
		name: "spring-cloud-commons"
	)

	public static final Project CONFIG = new Project(
		name: "spring-cloud-config"
	)

	public static final Project CONSUL = new Project(
		name: "spring-cloud-consul"
	)

	public static final Project CONTRACT = new Project(
		name: "spring-cloud-contract"
	)

	public static final Project CORE_TESTS = new Project(
		name: "spring-cloud-core-tests"
	)

	public static final Project GATEWAY = new Project(
		name: "spring-cloud-gateway"
	)

	public static final Project KUBERNETES = new Project(
		name: "spring-cloud-kubernetes"
	)

	public static final Project NETFLIX = new Project(
		name: "spring-cloud-netflix"
	)

	public static final Project OPENFEIGN = new Project(
		name: "spring-cloud-openfeign"
	)

	public static final Project RELEASE = new Project(
		name: "spring-cloud-release",
		hasTests: false
	)

	public static final Project SLEUTH = new Project(
		name: "spring-cloud-sleuth"
	)

	public static final Project TASK = new Project(
		name: "spring-cloud-task"
	)

	public static final VaultProject VAULT = new VaultProject(
		name: "spring-cloud-vault",
		hasTests: false
	)

	public static final Project ZOOKEEPER = new Project(
		name: "spring-cloud-zookeeper"
	)

	public static final Project SQUARE = new Project(
		name: "spring-cloud-square",
		org: "spring-projects-experimental"
	)

	public static final Project SLEUTH_OTEL = new Project(
		name: "spring-cloud-sleuth-otel",
		org: "spring-projects-experimental"
	)

	public static final List<Project> ALL = [BUILD, BUS, CIRCUITBREAKER, CLI, CLOUDFOUNDRY, COMMONS, CONFIG,
											 CONSUL, CONTRACT, CORE_TESTS, GATEWAY, KUBERNETES, NETFLIX, OPENFEIGN,
											 RELEASE, SLEUTH, TASK, VAULT, ZOOKEEPER, SQUARE, SLEUTH_OTEL]

	public static final Map<String, Project> ALL_BY_NAME = ALL.collectEntries { [it.name, it]}

}
