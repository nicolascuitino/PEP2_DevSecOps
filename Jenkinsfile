pipeline{
    agent any
    tools {
        maven "M3"
    }
    stages{
        stage("Build JAR FILE"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/nicolascuitino/PEP2_DevSecOps.git']])
                dir("eval1"){
                    sh "mvn clean install"
                }
            }
        }
    }
}
