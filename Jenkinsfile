pipeline{
    agent any
    environment {
        ZAP_PATH = '/zap/zap.sh'
        TARGET_URL = 'http://localhost:8080'
        ZAP_PORT = '8081'
        REPORT_PATH = 'zap-report.html'
    }
    tools {
        maven "M3"
        'org.jenkinsci.plugins.docker.commons.tools.DockerTool' 'docker'
    }
    stages{
        
        
        stage("Build JAR FILE"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/nicolascuitino/PEP2_DevSecOps.git']])
                sh "mvn clean install"
                
            }
        }
        stage('Start Spring Boot App') {
            steps {
                sh 'java -jar /var/jenkins_home/workspace/devsecops/target/app-0.0.1-SNAPSHOT.jar &'
            }
        }

        stage('Run OWASP ZAP') {
            steps {
                // Launch OWASP ZAP Docker container
                sh '''
                docker run --rm \
                    -v $(pwd):/zap/wrk \
                    -p 8081:8081 \
                    owasp/zap2docker-stable zap.sh -daemon -port $ZAP_PORT
                '''

                // Perform the scan
                sh '''
                docker exec zap \
                    zap-cli quick-scan --self-contained --start-options="-config api.disablekey=true" $TARGET_URL
                '''

                // Generate a report
                sh '''
                docker exec zap zap-cli report -o /zap/wrk/$REPORT_PATH -f html
                '''
            }
        }
        stage('Publish Report') {
            steps {
                publishHTML(target: [
                    allowMissing: false,
                    keepAll: true,
                    reportDir: '.',
                    reportFiles: 'zap-report.html',
                    reportName: 'ZAP Security Report'
                ])
            }
        }
    }
    post {
        always {
            echo 'Cleaning up resources...'
            sh 'pkill -f app-0.0.1-SNAPSHOT.jar || true'
        }
    }
}
