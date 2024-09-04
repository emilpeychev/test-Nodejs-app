def gv

pipeline {
    options {
        ansiColor('xterm')
    }
    agent {
        kubernetes {
            label 'kubeagents'
            inheritFrom 'default-pod-template'  // Adjust or remove based on your setup
            containerTemplate {
                name 'jnlp'
                image 'jenkins/inbound-agent:latest'
                args '${computer.jnlpmac} ${computer.name}'
                ttyEnabled true
                alwaysPullImage true
            }
            containerTemplate {
                name 'kaniko'
                image 'gcr.io/kaniko-project/executor:latest'
                command 'cat'
                ttyEnabled true
                volumeMounts {
                    mountPath '/kaniko/.docker'
                    name 'kaniko-secret'
                }
                // Add other configurations if necessary
            }
            volume {
                name 'kaniko-secret'
                secret {
                    secretName 'docker-config-secret'
                }
            }
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
                container('kaniko') {
                    script {
                        gv.buildImage()
                    }
                }
            }
        }

        stage("deploy") {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }
}

