package org.springframework.jenkins.cloud.common


import groovy.transform.CompileStatic

import static org.springframework.jenkins.cloud.common.Projects.BUILD
import static org.springframework.jenkins.cloud.common.Projects.BUS
import static org.springframework.jenkins.cloud.common.Projects.CIRCUITBREAKER
import static org.springframework.jenkins.cloud.common.Projects.CLI
import static org.springframework.jenkins.cloud.common.Projects.CLOUDFOUNDRY
import static org.springframework.jenkins.cloud.common.Projects.COMMONS
import static org.springframework.jenkins.cloud.common.Projects.CONFIG
import static org.springframework.jenkins.cloud.common.Projects.CONSUL
import static org.springframework.jenkins.cloud.common.Projects.CONTRACT
import static org.springframework.jenkins.cloud.common.Projects.CORE_TESTS
import static org.springframework.jenkins.cloud.common.Projects.GATEWAY
import static org.springframework.jenkins.cloud.common.Projects.KUBERNETES
import static org.springframework.jenkins.cloud.common.Projects.NETFLIX
import static org.springframework.jenkins.cloud.common.Projects.OPENFEIGN
import static org.springframework.jenkins.cloud.common.Projects.RELEASE
import static org.springframework.jenkins.cloud.common.Projects.SLEUTH
import static org.springframework.jenkins.cloud.common.Projects.SLEUTH_OTEL
import static org.springframework.jenkins.cloud.common.Projects.SQUARE
import static org.springframework.jenkins.cloud.common.Projects.TASK
import static org.springframework.jenkins.cloud.common.Projects.VAULT
import static org.springframework.jenkins.cloud.common.Projects.ZOOKEEPER

/**
 * TBD
 *
 * @author Spencer Gibb
 */
@CompileStatic
class ReleaseTrains {

	public static final ReleaseTrain KILBURN = ReleaseTrain.from {
		version = "2022.0"
		codename = "Kilburn"
		jdks << jdk17()
		bootVersion = "3.0.x"
		project BUILD, "main"
		project BUS, "main"
		project CIRCUITBREAKER, "main"
		project COMMONS, "main"
		project CONFIG, "main"
		project CONSUL, "main"
		project CONTRACT, "main"
		project CORE_TESTS, "main"
		project GATEWAY, "main"
		project KUBERNETES, "main"
		project NETFLIX, "main"
		project OPENFEIGN, "main"
		project RELEASE, "main"
		project SLEUTH, "main"
		project TASK, "main"
		project VAULT, "main"
		project ZOOKEEPER, "main"
	}
	public static final ReleaseTrain JUBILEE = ReleaseTrain.from {
		version = "2021.0"
		codename = "Jubilee"
		bootVersion = "2.6.x"
		jdks.addAll jdk8(), jdk11(), jdk17()
		project BUILD, "3.1.x"
		project BUS, "3.1.x"
		project CIRCUITBREAKER, "2.1.x"
		project CLI, "3.1.x"
		project CLOUDFOUNDRY, "3.1.x"
		project COMMONS, "3.1.x"
		project CONFIG, "3.1.x"
		project CONSUL, "3.1.x"
		project CONTRACT, "3.1.x"
		project CORE_TESTS, "3.1.x"
		project GATEWAY, "3.1.x"
		project KUBERNETES, "2.1.x"
		project NETFLIX, "3.1.x"
		project OPENFEIGN, "3.1.x"
		project RELEASE, "2021.0.x"
		project SLEUTH, "3.1.x"
		project TASK, "2.3.x"
		project VAULT, "3.1.x"
		project ZOOKEEPER, "3.1.x"
	}
	public static final ReleaseTrain ILFORD = ReleaseTrain.from {
		version = "2020.0"
		codename = "Ilford"
		bootVersion = "2.4.x"
		bootCompatibility << "2.5.x"
		jdks.addAll jdk8(), jdk11(), jdk17()
		project BUILD, "3.0.x"
		project BUS, "3.0.x"
		project CIRCUITBREAKER, "2.0.x"
		project CLI, "3.0.x"
		project CLOUDFOUNDRY, "3.0.x"
		project COMMONS, "3.0.x"
		project CONFIG, "3.0.x"
		project CONSUL, "3.0.x"
		project CONTRACT, "3.0.x"
		project CORE_TESTS, "3.0.x"
		project GATEWAY, "3.0.x"
		project KUBERNETES, "2.0.x"
		project NETFLIX, "3.0.x"
		project OPENFEIGN, "3.0.x"
		project RELEASE, "2020.0.x"
		project SLEUTH, "3.0.x"
		project TASK, "2.3.x"
		project VAULT, "3.0.x"
		project ZOOKEEPER, "3.0.x"
	}
	public static final ReleaseTrain HOXTON = ReleaseTrain.from {
		version = "Hoxton"
		codename = version
		active = false
		jdks << jdk8()
		bootVersion = "2.3.x"
		project BUILD, "2.3.x"
		project BUS, "2.2.x"
		project CIRCUITBREAKER, "1.0.x"
		project CLI, "2.2.x"
		project CLOUDFOUNDRY, "2.2.x"
		project COMMONS, "2.2.x"
		project CONFIG, "2.2.x"
		project CONSUL, "2.2.x"
		project CONTRACT, "2.2.x"
		project CORE_TESTS, "2.2.x"
		project GATEWAY, "2.2.x"
		project KUBERNETES, "1.1.x"
		project NETFLIX, "2.2.x"
		project OPENFEIGN, "2.2.x"
		project RELEASE, "Hoxton.x"
		project SLEUTH, "2.2.x"
		project VAULT, "2.2.x"
		project ZOOKEEPER, "2.2.x"
	}
	public static final ReleaseTrain EXPERIMENTAL = ReleaseTrain.from {
		version = "Experimental"
		codename = version
		jdks << jdk8()
		bootVersion = "2.6.x"
		project SQUARE, "main"
		project SLEUTH_OTEL, "main"
	}

	public static final List<ReleaseTrain> ALL = [EXPERIMENTAL, HOXTON, ILFORD, JUBILEE, KILBURN]

	public static final Map<String, ReleaseTrain> ALL_BY_CODENAME = ALL.collectEntries { [it.codename, it]}

}
