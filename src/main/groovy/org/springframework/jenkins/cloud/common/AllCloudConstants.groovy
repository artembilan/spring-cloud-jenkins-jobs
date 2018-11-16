package org.springframework.jenkins.cloud.common

import groovy.transform.CompileStatic

/**
 * Constants used by all cloud jobs (sometimes traits can't easily pass const values)
 *
 * @author Marcin Grzejszczak
 */
@CompileStatic
class AllCloudConstants {
	/**
	 * The minor against which the compatibility builds should be checked
	 */
	public static final String BOOT_MINOR_FOR_API_COMPATIBILITY = '2.1'

	/**
	 * Latest version of Boot to be checked. Used in some E2E test (e.g. Camden vs latest Boot)
	 * and in compatibility builds
	 */
	public static final String LATEST_BOOT_VERSION = '2.0.3.BUILD-SNAPSHOT'

	/**
	 * Latest version of Boot to be checked. Used in some E2E test (e.g. Camden vs latest Boot)
	 * and in compatibility builds
	 */
	public static final String LATEST_SPRING_VERSION = '5.0.0.BUILD-SNAPSHOT'

	/**
	 * List of skipped projects for Spring Cloud meta release
	 */
	public static final String DEFAULT_RELEASER_SKIPPED_PROJECTS = "spring-boot,spring-cloud-stream,spring-cloud-task"

	/**
	 * Latest version of Boot to be checked. Used in some E2E test (e.g. Camden vs latest Boot)
	 * and in compatibility builds
	 */
	public static final String DEFAULT_STREAM_RELEASER_SKIPPED_PROJECTS = "spring-boot,spring-cloud-build"

	/**
	 * Default contents of the config/releaser.properties used for meta-release.
	 * Remember that order of properties matters! We will clone and run the release
	 * process for every single project in that order.
	 */
	public static final String DEFAULT_RELEASER_PROPERTIES_FILE_CONTENT = """\
releaser.fixed-versions[spring-boot]=2.0.3.RELEASE
releaser.fixed-versions[spring-cloud-build]=2.0.3.BUILD-SNAPSHOT
releaser.fixed-versions[spring-cloud-commons]=2.0.1.BUILD-SNAPSHOT
releaser.fixed-versions[spring-cloud-stream]=Elmhurst.RELEASE
releaser.fixed-versions[spring-cloud-task]=2.0.0.BUILD-SNAPSHOT
releaser.fixed-versions[spring-cloud-function]=1.0.0.BUILD-SNAPSHOT
releaser.fixed-versions[spring-cloud-aws]=2.0.1.BUILD-SNAPSHOT
releaser.fixed-versions[spring-cloud-bus]=2.0.1.BUILD-SNAPSHOT
releaser.fixed-versions[spring-cloud-config]=2.0.1.BUILD-SNAPSHOT
releaser.fixed-versions[spring-cloud-netflix]=2.0.1.BUILD-SNAPSHOT
releaser.fixed-versions[spring-cloud-cloudfoundry]=2.0.1.BUILD-SNAPSHOT
releaser.fixed-versions[spring-cloud-openfeign]=2.0.1.BUILD-SNAPSHOT
releaser.fixed-versions[spring-cloud-gateway]=2.0.1.BUILD-SNAPSHOT
releaser.fixed-versions[spring-cloud-security]=2.0.1.BUILD-SNAPSHOT
releaser.fixed-versions[spring-cloud-consul]=2.0.1.BUILD-SNAPSHOT
releaser.fixed-versions[spring-cloud-zookeeper]=2.0.1.BUILD-SNAPSHOT
releaser.fixed-versions[spring-cloud-sleuth]=2.0.1.BUILD-SNAPSHOT
releaser.fixed-versions[spring-cloud-contract]=2.0.1.BUILD-SNAPSHOT
releaser.fixed-versions[spring-cloud-vault]=2.0.1.BUILD-SNAPSHOT
releaser.fixed-versions[spring-cloud-release]=Finchley.BUILD-SNAPSHOT
"""

	/**
	 * Default contents of the config/releaser.properties used for meta-release of stream.
	 * Remember that order of properties matters! We will clone and run the release
	 * process for every single project in that order.
	 */
	public static final String DEFAULT_STREAM_RELEASER_PROPERTIES_FILE_CONTENT = """\
releaser.fixed-versions[spring-boot]=2.1.0.RELEASE
releaser.fixed-versions[spring-cloud-build]=2.1.0.RC2
releaser.fixed-versions[spring-cloud-stream]=2.1.0.BUILD-SNAPSHOT
releaser.fixed-versions[spring-cloud-stream-binder-rabbit]=2.1.0.BUILD-SNAPSHOT
releaser.fixed-versions[spring-cloud-stream-binder-kafka]=2.1.0.BUILD-SNAPSHOT
releaser.fixed-versions[spring-cloud-stream-starters]=Fishtown.BUILD-SNAPSHOT
"""
}
