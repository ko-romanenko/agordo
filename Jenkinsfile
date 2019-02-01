pipeline {
    agent any
    parameters {
        booleanParam(name: 'MAKE_RELEASE', defaultValue: false, description: 'Do you want to perform release?')
        string(name: 'RELEASE_VERSION', defaultValue: "1.0", description: 'Version that you want to release. This option make sense only if MAKE_RELEASE is checked')
        string(name: 'NEXT_VERSION', defaultValue: "2-SNAPSHOT", description: 'Next version of application after release. This option make sense only if MAKE_RELEASE is checked')
    }

    environment {
        MAIN_BRANCH = 'master'
    }

    stages {
        stage("Gather Project Info") {
            steps {
                //checkout scm

                script {
                    // will be availabe on global scope
                    env.SERVICE_VERSION = readFile 'VERSION'
                    def scmUrl = scm.getUserRemoteConfigs()[0].getUrl()
                    env.SERVICE_NAME = scmUrl.tokenize('/')[3].split("\\.")[0]
                }
                echo "Service name: ${env.SERVICE_NAME}"
                echo "Service version: ${env.SERVICE_VERSION}"
                echo "Release?: ${params.MAKE_RELEASE}"
            }
        }
        stage('Build') {
            when { expression { return params.MAKE_RELEASE || env.GIT_COMMIT != env.GIT_PREVIOUS_SUCCESSFUL_COMMIT } }
            steps {
                sh "./gradlew clean build"
            }
            post {
                always {
                    script {
                        if (currentBuild.currentResult == "SUCCESS") {
                            bitbucketStatusNotify(buildState: 'SUCCESSFUL', repoSlug: env.SERVICE_NAME)
                        } else {
                            bitbucketStatusNotify(buildState: 'FAILED', repoSlug: env.SERVICE_NAME)
                        }
                    }
                    archiveArtifacts artifacts: 'build/libs/**/*.jar'
                }
            }
        }
    }
}
