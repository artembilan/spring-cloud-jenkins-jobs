package org.springframework.jenkins.cloud.ci

import groovy.transform.PackageScope
import javaposse.jobdsl.dsl.DslFactory

import org.springframework.jenkins.cloud.common.CloudCron
import org.springframework.jenkins.cloud.common.CustomJob
import org.springframework.jenkins.cloud.common.HashicorpTrait
import org.springframework.jenkins.cloud.common.SpringCloudJobs
import org.springframework.jenkins.cloud.common.SpringCloudNotification
import org.springframework.jenkins.common.job.JdkConfig
import org.springframework.jenkins.common.job.Maven
import org.springframework.jenkins.common.job.TestPublisher

/**
 * @author Marcin Grzejszczak
 */
@PackageScope
abstract class AbstractHashicorpDeployBuildMaker implements JdkConfig, TestPublisher, HashicorpTrait,
		CloudCron, SpringCloudJobs, Maven, CustomJob {
	protected final DslFactory dsl
	protected final String organization
	protected final String project
	protected String jdkVersion = jdk17()
	protected boolean upload = true

	AbstractHashicorpDeployBuildMaker(DslFactory dsl, String organization, String project) {
		this.dsl = dsl
		this.organization = organization
		this.project = project
	}

	@Override
	void deploy(String branchName = "main") {
		dsl.job("$project-$branchName-ci") {
			triggers {
				cron cronValue
				if (onGithubPush) {
					githubPush()
				}
			}
			jdk(jdkVersion(branchName))
			label(findLabel(branchName))
			scm {
				git {
					remote {
						url "https://github.com/${organization}/${project}"
						branch branchName
					}
					extensions {
						wipeOutWorkspace()
						localBranch("**")
					}
				}
			}
			wrappers {
				timestamps()
				colorizeOutput()
				maskPasswords()
				credentialsBinding {
					usernamePassword(dockerhubUserNameEnvVar(),
							dockerhubPasswordEnvVar(),
							dockerhubCredentialId())
					usernamePassword(githubRepoUserNameEnvVar(),
							githubRepoPasswordEnvVar(),
							githubUserCredentialId())
					string(githubToken(), githubTokenCredId())
				}
				environmentVariables {
					env('BRANCH', branchName)
				}
				timeout {
					noActivity(600)
					failBuild()
					writeDescription('Build failed due to timeout after {0} minutes of inactivity')
				}
			}
			steps {
				shell(loginToDocker())
				shell(removeMavenInstallation())
				shell(stopRunningDocker())
				maven {
					mavenInstallation(maven33())
					goals('--version')
				}
				shell("""\
						${antiPermgenAndJava7TlsHack(branchName)}
						${preStep()}
						trap "{ ${postStep()} }" EXIT
						${upload ? cleanDeployWithDocs() : cleanInstallWithoutDocs()}
					""")
			}
			configure {
				SpringCloudNotification.cloudSlack(it as Node)
			}
			publishers {
				archiveJunit mavenJUnitResults()
			}
		}
	}

	@Override
	void jdkBuild(String jdkVersion) {
		this.jdkVersion = jdkVersion
		this.upload = false
		deploy(mainBranch())
	}

	protected String antiPermgenAndJava7TlsHack(String branchName) {
		if (branchName == "main") {
			return '#!/bin/bash -x\nexport MAVEN_OPTS="-Xms256M -Xmx1024M -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000 -Dhttps.protocols=TLSv1.2"'
		}
		return '#!/bin/bash -x\nexport MAVEN_OPTS="-Xms256M -Xmx1024M -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000 -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=4096M -Dhttps.protocols=TLSv1.2"'
	}

	protected String findLabel(String branchName) {
		if (branchName.equals("main")) {
			return "linux&&jdk17"
		}
		return "linux&&jdk8"
	}

	protected String jdkVersion(String branchName) {
        //TODO: better matching. All 1.*.x branches are jdk7
		return branchName.startsWith('1.') && branchName.endsWith('.x') ? jdk7() : this.jdkVersion
	}

	protected abstract String preStep()
	protected abstract String postStep()
}
