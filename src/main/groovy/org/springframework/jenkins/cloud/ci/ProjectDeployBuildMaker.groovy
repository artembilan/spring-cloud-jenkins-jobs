package org.springframework.jenkins.cloud.ci

import javaposse.jobdsl.dsl.DslFactory
import org.springframework.jenkins.cloud.common.AllCloudJobs
import org.springframework.jenkins.cloud.common.CloudCron
import org.springframework.jenkins.cloud.common.Project
import org.springframework.jenkins.cloud.common.ReleaseTrain
import org.springframework.jenkins.cloud.common.SpringCloudJobs
import org.springframework.jenkins.cloud.common.SpringCloudNotification
import org.springframework.jenkins.common.job.JdkConfig
import org.springframework.jenkins.common.job.Maven
import org.springframework.jenkins.common.job.TestPublisher

/**
 * @author Marcin Grzejszczak
 */
class ProjectDeployBuildMaker implements JdkConfig, TestPublisher, CloudCron,
		SpringCloudJobs, Maven {
	private final DslFactory dsl
	private final ReleaseTrain train
	private final Project project
	boolean upload = true
	String branchToBuild
	String jdkVersion
	Closure<Node> slack = { Node node -> SpringCloudNotification.cloudSlack(node) }


    ProjectDeployBuildMaker(DslFactory dsl, ReleaseTrain train, Project project) {
		this.dsl = dsl
		this.train = train
		this.project = project
	}

	void deploy() {
		// TODO: assertions
		String jobName = "${project.name}-${train.codename}-${branchToBuild}-${jdkVersion}-ci"

		dsl.job(jobName) {
			triggers {
				cron cronValue
				if (onGithubPush) {
					githubPush()
				}
			}
			parameters {
				stringParam(branchVarName(), branchToBuild ?: mainBranch(), 'Which branch should be built')
			}
			jdk branchToBuild != mainBranch() ? jdk8() : jdkVersion
			label(project.labelExpression(jdkVersion))
			scm {
				git {
					remote {
						url "https://github.com/${project.org}/${project.name}"
					}
					branch "\$${branchVarName()}"
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
					env('BRANCH', branchToBuild)
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
				shell(buildCommand())
			}
			configure {
				slack.call(it as Node)
			}
			if (project.hasTests) {
				publishers {
					archiveJunit mavenJUnitResults()
				}
			}
			List<String> emails = AllCloudJobs.EMAIL_NOTIFICATIONS.get(project)
			if (emails) {
				publishers {
					mailer(emails.join(","), true, true)
				}
			}
		}
	}

	String buildCommand() {
		String customBuildCommand = project.customBuildCommand(branchToBuild, jdkVersion, upload)
		if (customBuildCommand) {
			return customBuildCommand
		}
		return this.upload ? cleanDeployWithDocs() : cleanInstallWithoutDocs()
	}

}
