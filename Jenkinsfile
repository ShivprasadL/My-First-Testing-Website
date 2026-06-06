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
            // Capture TestNG/JUnit results
            junit '**/target/surefire-reports/*.xml'
            
            // Send Notification
            script {
                // Use the ID you created in Jenkins Credentials
                withCredentials([string(credentialsId: 'google-chat-token', variable: 'CHAT_TOKEN')]) {
                    googlechatnotification(
                        // This handles the authentication using the token
                        url: "https://chat.googleapis.com/v1/spaces/YOUR_SPACE_ID/messages?key=${CHAT_TOKEN}",
                        message: "Build ${env.JOB_NAME} #${env.BUILD_NUMBER} finished. Status: ${currentBuild.currentResult}. URL: ${env.BUILD_URL}",
                        spaceId: 'YOUR_SPACE_ID' // Replace with your actual Space ID
                    )
                }
            }
        }
    }
}