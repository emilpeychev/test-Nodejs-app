def gv

pipeline {
    options {
        ansiColor('xterm')
    }
    agent {
        kubernetes {
            label 'kubeagents'
            // Define the pod YAML only if additional customizations are needed
            // Otherwise, let Jenkins UI handle it
        }
    }

    tools {
        maven 'maven-3-9-6'
    }

    stages {
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    gv.buildJar()
                }
            }
        }

        stage("build image with Kaniko") {
            steps {
                container('kaniko') { // Use the Kaniko container
                    script {
                        def imageTag = gv.buildImage()
                        env.IMAGE_TAG = imageTag // Store image tag in environment variable
                    }
                }
            }
        }

        stage("deploy") {
            steps {
                script {
                    gv.deployApp(env.IMAGE_TAG) // Pass the image tag to deploy
                }
            }
        }
    }
}
