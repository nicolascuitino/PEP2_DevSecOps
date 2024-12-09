pipeline{
    agent any
    environment {
        ZAP_PATH = '/zap/zap.sh'
        TARGET_URL = 'http://localhost:8082'
        ZAP_PORT = '8081'
        REPORT_PATH = 'zap-report.html'
    }
    tools {
        maven "M3"
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
                docker run -dt --name owasp \ -p 8081:8081 \
                         zaproxy/zap-stable \
                         /bin/bash
                '''
                

                // Perform the scan
                sh '''
                docker exec owasp \ zap-baseline.py \ -t $TARGET_URL \ -x report.xml \ -I
                '''

                // Generate a report
                sh '''
                docker exec zap zap-cli report -o /zap/wrk/$REPORT_PATH -f html
                '''
            }
        }
        
        stage('Prepare wrk directory') {
             when {
                         environment name : 'GENERATE_REPORT', value: 'true'
             }
             steps {
                 script {
                         sh """
                             docker exec owasp \
                             mkdir /zap/wrk
                         """
                     }
                 }
         }

        stage('Scanning target on owasp container') {
             steps {
                        sh """
                             docker exec owasp \
                             zap-baseline.py \
                             -t $target \
                             -x report.xml \
                             -I
                         """
                     }
                    
         }
        
        stage('Copy Report to Workspace'){
             steps {
                 script {
                     sh '''
                         docker cp owasp:/zap/wrk/report.xml ${WORKSPACE}/report.xml
                     '''
                 }
             }
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
