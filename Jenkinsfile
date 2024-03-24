def gv

pipeline {
    agent {
        kubernetes {
            label 'k8s-agent'
            defaultContainer 'docker'
            yaml """
            apiVersion: v1
            kind: Pod
            spec:
              containers:
              - name: docker
                image: docker:19.03-dind
                command:
                - cat
                tty: true
              volumes:
              - name: docker-socket
                hostPath:
                  path: /var/run/docker.sock
            """
        }
    }

    tools {
        maven 'maven-v3.9.6'
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

        stage("build image") {
            steps {
                script {
                    gv.buildImage()
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
