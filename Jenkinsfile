pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                    credentialsId: 'github-token',
                    url: 'https://github.com/ricky9397/spring_boot_react_project.git'
            }
        }
        
        stage('Build') {
            steps {
                sh 'echo "Building Docker images..."'
                sh 'docker compose build'
            }
        }
        
        stage('Deploy') {
            steps {
                sh 'echo "Deploying containers..."'
                sh 'docker compose down'
                sh 'docker compose up -d'
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
