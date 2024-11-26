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
        sh 'mvn clean install'
      }
    }

    stage('Test') {
      steps {
        sh 'mvn test'
      }
    }

    stage('Package') {
      steps {
        sh ' sh \'mvn package\''
      }
    }

  }
}