def gv

pipeline {
    options {
        ansiColor('xterm')
    }
    agent {
        kubernetes {
            label 'kubeagents'
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

