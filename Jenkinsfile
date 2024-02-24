def gv

pipeline {
    agent {
        kubernetes {
            // Define the pod template with Maven installed
            label 'k8s-agent'
            defaultContainer 'maven'
            containers {
                containerTemplate(name: 'maven', image: 'maven:3.6.3-jdk-11', command: 'cat', ttyEnabled: true)
            }
        }
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
