package org.springframework.jenkins.cloud.release

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.jobs.FreeStyleJob

/**
 * @author Marcin Grzejszczak
 */
class SpringCloudReleaseMainMaker extends SpringCloudReleaseMaker {

	SpringCloudReleaseMainMaker(DslFactory dsl) {
		super(dsl)
	}

	SpringCloudReleaseMainMaker(DslFactory dsl, String organization) {
		super(dsl, organization)
	}

	@Override
	protected String projectName(String project) {
		return "${project}-${mainBranch()}-releaser"
	}

	@Override
	void release(String project, ReleaserOptions options) {
		options.updateSagan = false
		super.release(project, jdk17(), options)
	}

	@Override
	protected String branchToCheck() {
		return mainBranch()
	}

	@Override
	protected void additionalConfiguration(FreeStyleJob job) {
		job.triggers {
			cron oncePerDay()
		}
	}

	@Override
	protected String scriptPreconditions() {
		return '''\
echo "Running version check"
VERSION=$( sed '\\!<parent!,\\!</parent!d' `pwd`/pom.xml | grep '<version' | head -1 | sed -e 's/.*<version>//' -e 's!</version>.*$!!' )
echo "The found version is [${VERSION}]"

if ! echo $VERSION | egrep -q 'SNAPSHOT'; then
    echo "Version is NOT SNAPSHOT, will not do anything. Something is wrong!"
    exit 1
else
	echo "Version is a SNAPSHOT one, will continue with the build"
fi
'''
	}

	@Override
	protected void configureLabels(FreeStyleJob job) {

	}
}
