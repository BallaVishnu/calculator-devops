pipeline{
    agent any

    environment {
        DOCKER_IMAGE = 'vishnu933/calculator-devops'
    }

    stages{
        stage('Checkout'){
            steps{
                checkout scm
            }
        }
        stage('Build'){
            steps{
                sh './mvnw clean package -DskipTests'
            }
        }
        stage('Test'){
            steps{
                sh './mvnw test'
            }

            post {
                always{
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Docker Build'){
            steps{
                sh '''docker build -t ${DOCKER_IMAGE}:${BUILD_NUMBER} -t ${DOCKER_IMAGE}:latest . '''
            }
        }

        stage('Docker Push'){
            steps{
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-credentials', username
                        userNameVariable: 'DOCKER_USERNAME', 
                        passwordVariable: 'DOCKER_PASSWORD'
                    )
                ]) {
                    sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
                    
                        docker push ${DOCKER_IMAGE}:${BUILD_NUMBER}
                        docker push ${DOCKER_IMAGE}:latest
                        
                        docker logout
                    '''
                }
            }
        }
    }
}


