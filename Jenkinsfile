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
                // Using 'bat' for Windows compatibility
                // Note: Windows 'curl' requires double quotes for JSON data
                bat """
                curl -H "Content-Type: application/json" -X POST -d "{\\"text\\": \\"Build ${env.JOB_NAME} #${env.BUILD_NUMBER} finished. Status: ${currentBuild.currentResult}. URL: ${env.BUILD_URL}\\"}" https://chat.google.com/app/chat/AAQARjeJUiQ
                """
            }
            // This captures your test results and creates charts in Jenkins
	            junit '**/target/surefire-reports/*.xml'
//removed script for google chat
        }
    }
}