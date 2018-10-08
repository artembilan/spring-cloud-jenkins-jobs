package org.springframework.jenkins.cloud.e2e

import javaposse.jobdsl.dsl.DslFactory

import org.springframework.jenkins.cloud.common.SpringCloudJobs
import org.springframework.jenkins.common.job.Cron
import org.springframework.jenkins.common.job.JdkConfig
import org.springframework.jenkins.common.job.Label
import org.springframework.jenkins.common.job.TestPublisher

class SpringCloudSamplesEndToEndBuilder implements TestPublisher,
		JdkConfig, BreweryDefaults, Label, Cron, SpringCloudJobs {
	String projectName
	String organization = "spring-cloud-samples"
	String repoName
	String scriptName = "scripts/runAcceptanceTests.sh"
	String cronExpr
	String branchName = masterBranch()
	String label = ""
	String postBuildScripts = ""
	String jdk = jdk8()
	boolean mavenTests = false
	boolean gradleTests = false
	boolean withNodeJs = false
	Map<String, String> envs = [:]

	SpringCloudSamplesEndToEndBuilder withProjectName(String projectName) {
		this.projectName = projectName
		return this
	}

	SpringCloudSamplesEndToEndBuilder withProjectAndRepoName(String projectName) {
		this.projectName = projectName
		this.repoName = projectName
		return this
	}

	SpringCloudSamplesEndToEndBuilder withJdk(String jdk) {
		this.jdk = jdk
		return this
	}

	SpringCloudSamplesEndToEndBuilder withRepoName(String repoName) {
		this.repoName = repoName
		return this
	}

	SpringCloudSamplesEndToEndBuilder withScriptName(String scriptName) {
		this.scriptName = scriptName
		return this
	}

	SpringCloudSamplesEndToEndBuilder withCronExpr(String cronExpr) {
		this.cronExpr = cronExpr
		return this
	}

	SpringCloudSamplesEndToEndBuilder withBranchName(String branchName) {
		this.branchName = branchName
		return this
	}

	SpringCloudSamplesEndToEndBuilder withEnvs(Map<String, String> envs) {
		this.envs = envs
		return this
	}

	SpringCloudSamplesEndToEndBuilder withPostBuildScripts(String postBuildScripts) {
		this.postBuildScripts = postBuildScripts
		return this
	}

	SpringCloudSamplesEndToEndBuilder withMavenTests(boolean mavenTests) {
		this.mavenTests = mavenTests
		return this
	}

	SpringCloudSamplesEndToEndBuilder withGradleTests(boolean gradleTests) {
		this.gradleTests = gradleTests
		return this
	}

	SpringCloudSamplesEndToEndBuilder withNodeJs(boolean withNodeJs) {
		this.withNodeJs = withNodeJs
		return this
	}

	SpringCloudSamplesEndToEndBuilder withLabel(String label) {
		this.label = label
		return this
	}

	SpringCloudSamplesEndToEndBuildMaker build(DslFactory dsl) {
		def maker = new SpringCloudSamplesEndToEndBuildMaker(dsl)
		maker.jdkVersion = this.jdk
		maker.additionalEnvs = this.envs
		return maker
				.build(this.projectName, this.repoName, this.scriptName, this.cronExpr,
				this.branchName, this.postBuildScripts, this.mavenTests, this.gradleTests,
				this.label, this.withNodeJs)
	}
}
