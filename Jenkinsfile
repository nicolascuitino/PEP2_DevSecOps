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
                    sh './mvnw clean org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar'
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
