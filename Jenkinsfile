pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Execute Automation Tests') {
            steps {
                bat 'mvn clean test' 
            }
        }
    }
    
    post {
        always {
junit '**/target/surefire-reports/*.xml'
            
            script {
                // This is a direct, robust way to send a message without plugin UI issues
                sh """
                curl -H 'Content-Type: application/json' \
                -X POST -d '{"text": "Build ${env.JOB_NAME} #${env.BUILD_NUMBER} finished. Status: ${currentBuild.currentResult}. URL: ${env.BUILD_URL}"}' \
                'https://chat.google.com/app/chat/AAQARjeJUiQ'
                """
                )
            }
        }
    }
}