pipeline {
  agent any
  stages {
    stage('checkout code') {
      parallel {
        stage('checkout code') {
          steps {
            git(url: 'src/main/java/com/example/mspartner', branch: 'master')
          }
        }

        stage('') {
          steps {
            sh 'ls -la'
          }
        }

      }
    }

  }
}