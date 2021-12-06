pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Get some code from a GitHub repository
                git url: 'https://github.com/YigalNissan/Java-Optimizer.git',
                branch: 'master'
            }
        }
        stage('Build') {
            steps {
                //sh 'chmod +x init-image-optimizer.sh'
                sh "./java-image-optimizer/init-image-optimizer.sh"
                //sh 'mvn clean compile'
                //sh "run -t ."
                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
    }
}
