def buildJar() {
    echo 'Building the application...'
    // Build the JAR file using Maven
    sh 'mvn clean package -DskipTests'
}

def buildImage() {
    echo "Building the Docker image with Kaniko..."

    // Create a unique tag for the image using the timestamp
    def imageTag = "softunium/demo-app:${new Date().format('yyyyMMddHHmmss')}"

    // Kaniko build command
    sh """
    /kaniko/executor \
      --dockerfile=Dockerfile \
      --context=/workspace \
      --destination=docker.io/softunium/demo-app:${imageTag} \
      --insecure --skip-tls-verify
    """

    echo "Docker image built and pushed: ${imageTag}"

    // Return the image tag for further use (e.g., in deployment)
    return imageTag
}



def deployApp(imageTag) {
    echo "Deploying the application with image: ${imageTag}"

    // Kubernetes deployment using kubectl
    sh """
    kubectl set image deployment/your-deployment-name demo-app=${imageTag}
    kubectl rollout status deployment/demo-app
    """

    echo 'Application deployed successfully.'
}


return this

