def gv

pipeline {
    options {
        ansiColor('xterm')
    }
    agent {
        kubernetes {
            label 'kubeagents'
            yaml '''
            apiVersion: v1
            kind: Pod
            metadata:
            name: jenkins-agent
            spec:
            containers:
            - name: jnlp
                image: jenkins/inbound-agent:latest
                args: ['$(JENKINS_SECRET)', '$(JENKINS_NAME)']
                volumeMounts:
                - name: shared-data
                mountPath: /shared
            - name: kaniko
                image: gcr.io/kaniko-project/executor:latest
                command:
                - cat
                tty: true
                volumeMounts:
                - name: shared-data
                mountPath: /shared
            volumes:
            - name: shared-data
                emptyDir: {}

            '''
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

