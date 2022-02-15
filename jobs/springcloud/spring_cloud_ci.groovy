package springcloud

import javaposse.jobdsl.dsl.DslFactory

import org.springframework.jenkins.cloud.ci.CustomJobFactory
import org.springframework.jenkins.cloud.ci.SpringCloudDeployBuildMaker
import org.springframework.jenkins.cloud.ci.SpringCloudDeployBuildMakerBuilder
import org.springframework.jenkins.cloud.ci.SpringCloudKubernetesDeployBuildMaker
import org.springframework.jenkins.cloud.ci.SpringCloudReleaseToolsBuildMaker
import org.springframework.jenkins.cloud.ci.SpringCloudReleaseTrainDocsMaker
import org.springframework.jenkins.cloud.ci.VaultSpringCloudDeployBuildMaker
import org.springframework.jenkins.cloud.common.AllCloudJobs
import org.springframework.jenkins.cloud.common.CloudJdkConfig
import org.springframework.jenkins.cloud.common.ReleaseTrains
import org.springframework.jenkins.cloud.compatibility.BootCompatibilityBuildMaker

import static org.springframework.jenkins.cloud.common.AllCloudJobs.CUSTOM_BUILD_JOBS
import static org.springframework.jenkins.cloud.common.AllCloudJobs.INCUBATOR_JOBS
import static org.springframework.jenkins.cloud.common.AllCloudJobs.JOBS_WITH_BRANCHES

DslFactory dsl = this

// CI BUILDS
// Branch build maker that allows you to build and deploy a branch - this will be done on demand
new SpringCloudDeployBuildMaker(dsl).with { SpringCloudDeployBuildMaker maker ->
	ReleaseTrains.ALL.findAll { it.active }.each { train ->
		// each jdk in the train
		boolean doUpload = true
		train.jdks.each { jdk ->
			// each project and branch
			train.projectsWithBranch.each { project, branch ->
				new SpringCloudDeployBuildMakerBuilder(dsl)
						.organization(project.org)
						.jdkVersion(jdk)
						.upload(doUpload)
						.cron(oncePerDay())
						.prefix("${train.codename}-${project.name}-${jdk}-${branch}-ci")
						.build().deploy(project.name, branch, project.hasTests)
			}
			doUpload = false // only upload baseline jdk
		}
	}
}

// Custom jobs builder
CUSTOM_BUILD_JOBS.each { String projectName ->
	new CloudJdkConfig().with {
		new CustomJobFactory(dsl).deploy(projectName)
	}
	List<String> branches = JOBS_WITH_BRANCHES[projectName]
	if (branches) {
		branches.each { branch ->
			new CustomJobFactory(dsl).with {
				it.deployWithJdk(projectName, jdk8(), branch)
			}
			// TODO: branch jdk compatibility
//			new CustomJobFactory(dsl).jdkVersion(projectName, jdk11())
//			new CustomJobFactory(dsl).jdkVersion(projectName, jdk17())
		}
	}
}

// Sleuth
JOBS_WITH_BRANCHES['spring-cloud-sleuth'].each {String branch ->
	new SpringCloudDeployBuildMakerBuilder(dsl).with {
		jdkVersion(jdk8())
	}.build().deploy('spring-cloud-sleuth', branch)
}

new SpringCloudReleaseToolsBuildMaker(dsl).with {
	deploy()
	deploy("1.0.x")
}

new SpringCloudReleaseTrainDocsMaker(dsl).with {
	deploy(mainBranch())
	deploy("2021.0.x")
	deploy("2020.0.x")
	deploy("Hoxton")
}


// TODO: Main is being built against 2.6.0-SNAPSHOTs - we don't need to check the compatibility against Boot 2.5
//ALL_DEFAULT_JOBS.each {String project ->
//	boolean checkTests = !JOBS_WITHOUT_TESTS.contains(project)
//	// We're using the latest Boot version at this point
//	new BootCompatibilityBuildMaker(dsl).with {
//		it.buildWithTests(project, project, "main", "", checkTests)
//	}
//}
// TODO: compatibility builds for custom job projects
//new BootCompatibilityBuildMaker(dsl).with {
//	it.buildWithTests("spring-cloud-netflix", "spring-cloud-netflix", "main", oncePerDay(), true)
//}
//new BootCompatibilityBuildMaker(dsl).with {
//	it.buildWithTests("spring-cloud-contract", "spring-cloud-contract", "main", oncePerDay(), true)
//}
//new VaultCompatibilityBuildMaker(dsl).with {
//	it.buildWithTests("spring-cloud-vault", "spring-cloud-vault", "main", oncePerDay(), true)
//}

// Release branches for Spring Cloud Release
new SpringCloudDeployBuildMaker(dsl)
		.deploy('spring-cloud-release', 'Hoxton', false)
new SpringCloudDeployBuildMaker(dsl)
		.deploy('spring-cloud-release', '2020.0.x', false)

new SpringCloudKubernetesDeployBuildMaker(dsl).deploy()
new SpringCloudKubernetesDeployBuildMaker(dsl).deploy('2.0.x')
new VaultSpringCloudDeployBuildMaker(dsl).with {
	deploy(mainBranch())
}


// CI BUILDS FOR INCUBATOR
INCUBATOR_JOBS.each { String projectName ->
	def org = "spring-projects-experimental"
	new SpringCloudDeployBuildMaker(dsl, org).with {
		jdkVersion = jdk8()
		deploy(projectName)

		def jdk11Maker = new SpringCloudDeployBuildMakerBuilder(dsl)
				.organization(org)
				.prefix("spring-cloud-${jdk11()}").jdkVersion(jdk11())
				.cron(oncePerDay())
				.upload(false).build()
		jdk11Maker.deploy(projectName)

		def jdk17Maker = new SpringCloudDeployBuildMakerBuilder(dsl)
				.organization(org)
				.prefix("spring-cloud-${jdk17()}").jdkVersion(jdk17())
				.cron(oncePerDay())
				.upload(false).build()
		jdk17Maker.deploy(projectName)

		List<String> branches = AllCloudJobs.INCUBATOR_JOBS_WITH_BRANCHES[projectName]
		if (branches) {
			branches.each {
				deploy(projectName, it)
				jdk11Maker.deploy(projectName, it)
				jdk17Maker.deploy(projectName, it)
			}
		}
	}

}

