pipeline {
    agent any
    tools {
        jdk 'JDK'
    }
    stages {
        stage('test') {
            steps {
                bat """
                cd store
                mvn clean test"""
            }
        }
       stage('package') {
            steps {
                bat """
                cd store
                mvn package -DskipTests"""
            }
       }
       stage('install frontend') {
           steps {
               bat """
               cd store-front
               npm install"""
           }
      }
      stage('test frontend') {
             steps {
                 bat """
                 cd store-front
                 npm test"""
             }
      }
      stage('build frontend') {
           steps {
               bat """
               cd store-front
               ng build --prod"""
           }
      }
    }
}