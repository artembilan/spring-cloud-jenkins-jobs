package org.springframework.jenkins.cloud.common


import groovy.transform.CompileStatic

import static org.springframework.jenkins.cloud.common.Projects.BUILD
import static org.springframework.jenkins.cloud.common.Projects.COMMONS

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
		project COMMONS, "main"
	}
	public static final ReleaseTrain JUBILEE = ReleaseTrain.from {
		version = "2021.0"
		codename = "Jubilee"
		bootVersion = "2.6.x"
		jdks.addAll jdk8(), jdk11(), jdk17()
		project BUILD, "3.1.x"
		project COMMONS, "3.1.x"
	}
	public static final ReleaseTrain ILFORD = ReleaseTrain.from {
		version = "2020.0"
		codename = "Ilford"
		bootVersion = "2.4.x"
		bootCompatibility << "2.5.x"
		jdks.addAll jdk8(), jdk11(), jdk17()
		project BUILD, "3.0.x"
		project COMMONS, "3.0.x"
	}
	public static final ReleaseTrain HOXTON = ReleaseTrain.from {
		version = "Hoxton"
		codename = version
		jdks << jdk8()
		bootVersion = "2.3.x"
		project BUILD, "2.3.x"
		project COMMONS, "2.2.x"
	}

	public static final List<ReleaseTrain> ALL = [HOXTON, ILFORD, JUBILEE, KILBURN]

	public static final Map<String, ReleaseTrain> ALL_BY_CODENAME = ALL.collectEntries { [it.codename, it]}

}
