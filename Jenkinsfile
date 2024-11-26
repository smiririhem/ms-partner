pipeline {
  agent any
  stages {
    stage('checkout code') {
      steps {
        git(url: 'src/main/java/com/example/mspartner', branch: 'master')
      }
    }

    stage('Build') {
      steps {
        sh 'sh \'./mvnw clean compile\''
      }
    }

  }
}