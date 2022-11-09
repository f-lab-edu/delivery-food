pipeline {
    agent an
    environment {
   	    PATH = "/opt/gradle/gradle-7.5/bin:$PATH"
   	    SLACK_CHANNEL = '#jenkins'
    	TEAM_DOMAIN = 'delivery-foodhq'
    	TOKEN_CREDENTIAL_ID = 'haenlee-slack'

        stages {

            stage('Git Checkout') {
                steps {
                    checkout scm
                    echo 'git checkout success'
                }
            }

            stage('Build') {
                steps {
                    sh 'gradle clean build --exclude-task test'
                    echo 'build success'
                }
            }

            stage('Test') {
                steps {
                    sh 'gradle test'
                    echo 'test success'
                }
            }

            stage('Deploy') {
                steps([$class: 'BapSshPromotionPublisherPlugin']) {
                    sshPublisher(
                        continueOnError: false, failOnError: true,
                        publishers: [
                            sshPublisherDesc(
                            configName: "cd-server",
                                verbose: true,
                                transfers: [
                                    sshTransfer(
                                        sourceFiles: "build/libs/*jar",
                                        removePrefix: "build/libs",
                                        remoteDirectory: "/deploy",
                                        execCommand: "sh /root/deploy/run.sh"
                                    )
                                ]
                            )
                        ]
                    )
                }
            }
        }
    }

    post {
        success {
            slackSend (channel: SLACK_CHANNEL, color: '#00FF00', message: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})", teamDomain: TEAM_DOMAIN, tokenCredentialId: TOKEN_CREDENTIAL_ID )
        }

        failure {
            slackSend (channel: SLACK_CHANNEL, color: '#F01717', message: "FAILURE: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})", teamDomain: TEAM_DOMAIN, tokenCredentialId: TOKEN_CREDENTIAL_ID )
        }
    }
}
