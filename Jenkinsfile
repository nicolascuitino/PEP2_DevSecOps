pipeline{
    agent any
    tools {
        maven "M3"
    }
    stages{

        stage("Dependency check"){
            steps{
              dependencyCheck additionalArguments: '--format HTML', odcInstallation: 'owasp-dc'
            }
        }

        stage("Sonarqube scan"){
            steps{
                withSonarQubeEnv(installationName: 'sq1'){
                    sh 'mvn clean verify sonar:sonar  -Dsonar.projectKey=pep2_DevSecOps  -Dsonar.projectName='pep2_DevSecOps''
                }
              dependencyCheck additionalArguments: '--format HTML', odcInstallation: 'owasp-dc'
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
