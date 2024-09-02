def gv

pipeline {
agent {
    kubernetes {
        yaml """
        apiVersion: v1
        kind: Pod
        spec:
          containers:
          - name: kaniko
            image: gcr.io/kaniko-project/executor:latest
            args: ["-v", "/kaniko/.docker", "-v", "/workspace"]
        """
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
