pipeline {
    agent any
    
    tools {
        jdk 'Java 11'  // Jenkins에서 설정 필요
    }
    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                    credentialsId: 'github-token',
                    url: 'https://github.com/ricky9397/spring_boot_react_project.git'
            }
        }
        
        stage('Build Spring Boot JAR') {
            steps {
                sh 'echo "Building Spring Boot application..."'
                sh 'chmod +x ./gradlew'
                sh 'JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64 ./gradlew clean build -x test'
            }
        }
        
        stage('Build Docker Images') {
            steps {
                sh 'echo "Building Docker images..."'
                sh 'docker-compose build'
            }
        }
        
        stage('Deploy') {
            steps {
                sh 'echo "Deploying containers..."'
                sh 'docker-compose down'
                sh 'docker-compose up -d'
            }
        }
        
        stage('Cleanup') {
            steps {
                sh 'echo "Cleaning up unused images..."'
                sh 'docker image prune -f'
            }
        }
    }
    
    post {
        success {
            echo 'Deployment successful!'
        }
        failure {
            echo 'Deployment failed!'
        }
    }
}
