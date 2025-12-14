pipeline {
    agent any

    tools {
        jdk 'Java 11'
        maven 'Maven 3.9.2'
    }

    environment {
        SONARQUBE = 'SonarQubeServer'
        SONAR_PROJECT_KEY = 'banking-app'
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_TOKEN = credentials('SONAR_TOKEN')
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/MohanaGowda-Code/banking-app.git', credentialsId: 'github-credentials'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                        -Dsonar.host.url=${SONAR_HOST_URL} \
                        -Dsonar.login=${SONAR_TOKEN}
                    """
                }
            }
        }
    }

    post {
        success {
            echo 'Build, Tests, and SonarQube Analysis Completed Successfully!'
        }
        failure {
            echo 'Build Failed. Check Jenkins logs for details.'
        }
    }
}

