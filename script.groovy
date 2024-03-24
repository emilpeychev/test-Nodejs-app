def buildJar() {
    echo 'building the application...'
    sh 'mvn package'
}

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'f26bf48c-31dc-4204-95b6-de762e5ed119', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t test/demo-app:jma-2.0 .'
        sh 'echo $PASS | docker login -u $USER --password-stdin'
        sh 'docker push softunium/demo-app:$(date +"%Y%m%d%H%M%S")'
    }
}

def deployApp() {
    echo 'deploying the application...'
}

return this
