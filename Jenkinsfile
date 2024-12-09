pipeline{
    agent any
    environment {
        ZAP_PATH = '/zap/zap.sh'
        TARGET_URL = 'http://app:8082'
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
        stage("Build Docker Image"){
            steps{
                
                    sh "docker build -t nicolascuitino4/devsecops ."
                
            }
        }
        stage("Push Docker Image"){
            steps{
                
                    sh "docker login -u nicolascuitino4 -p X9xw3YiZE"
                    
                    sh "docker push nicolascuitino4/devsecops"
                
            }
        }   

        stage('Run app') {
            steps {
                // Launch OWASP ZAP Docker container
                sh '''
                docker run --name app -p 8082:8082 \
                         nicolascuitino4/devsecops
                '''
            }
        }


        stage('Run OWASP ZAP') {
            steps {
                // Launch OWASP ZAP Docker container
                sh '''
                docker run -dt -p 8081:8081 --name owasp \
                         zaproxy/zap-stable \
                         /bin/bash
                '''
            }
        }
        
        stage('Prepare wrk directory') {
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
                             -t $TARGET_URL \
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
    post {
        always {
            echo 'Cleaning up resources...'
            sh 'pkill -f app-0.0.1-SNAPSHOT.jar || true'
            sh '''
                     docker stop owasp
                     docker rm owasp
                 '''
            sh '''
                     docker stop app
                     docker rm app
                 '''
        }
    }
}
