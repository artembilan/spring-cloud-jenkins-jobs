package org.springframework.jenkins.cloud.common

import groovy.transform.CompileStatic

/**
 * @author Marcin Grzejszczak
 */
@CompileStatic
trait HashicorpTrait {

	String preConsulShell() {
		return ''' #!/bin/bash
					echo "Clearing consul data"
					rm -rf /tmp/consul
					rm -rf /tmp/consul-config

					echo "Install consul"
					./src/main/bash/ci_install_consul.sh

					echo "Run consul"
					./src/test/bash/ci_run_consul.sh
				'''
	}

	String postConsulShell() {
		return '''echo 'Kill consul' && kill -9 $(ps aux | grep '[c]onsul' | awk '{print $2}') && echo 'Killed consul' || echo 'Cannot find consul in running processes';'''
	}

	String preVaultShell() {
		return ''' #!/bin/bash
					echo "Kill Vault"
					pkill vault && echo "Vault killed" || echo "No Vault process was running"

					echo "Install Vault"
					./src/test/bash/create_certificates.sh
					./src/test/bash/install_vault.sh

					echo "Run Vault"
					./src/test/bash/local_run_vault.sh &
				'''
	}

	String postVaultShell() {
		return """pkill vault && echo 'Vault killed' || echo 'No Vault process was running';"""
	}
}