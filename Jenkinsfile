pipeline {
    agent any

    tools {
        // Tells Jenkins to use Maven (We will name it 'Maven3' in Jenkins next)
        maven 'Maven3'
    }

    stages {
        stage('Checkout Code') {
            steps {
                // Jenkins automatically checks out code from your SCM settings
                checkout scm
            }
        }

        stage('Execute Automation Tests') {
            steps {
                // This runs your Maven test suite
                bat 'mvn clean test' 
            }
        }
    }
    
    post {
        always {
            // This captures your test results and creates charts in Jenkins
            junit '**/target/surefire-reports/*.xml'
        }
    }
}