pipeline{
    agent any
    tools {
        maven "M3"
    }     

      stage("Sonarqube scan"){
            steps{
                withSonarQubeEnv(installationName: 'sq1'){
                    sh 'mvn clean package sonar:sonar'
                }
            }
        }
        
      stage("SQ Quality gate"){
            steps{
                timeout(time:2, unit: 'MINUTES'){
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    
        
        stage("Build JAR FILE"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/nicolascuitino/PEP2_DevSecOps.git']])
                sh "mvn clean install"
                
            }
        }
    }
}
