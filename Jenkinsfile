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
            // This captures your test results and creates trend charts on your Jenkins dashboard
            junit '**/target/surefire-reports/*.xml'
        }
    }
}