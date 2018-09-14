pipeline {
    agent any

    stages {

        stage ('Compile Stage') {
            steps {
                sh 'mvn clean compile -DskipTests=true'
            }
        }

 /*       stage ('Testing Stage') {
            steps {
                sh 'mvn test'
            }
        }
 */

        stage ('Packaging Stage') {
            steps {
//                sh 'mvn clean package'
                  sh 'mvn -DskipTests=true package'
            }
        }

        stage ('Deployment Stage') {
            steps {
                 sh '/var/lib/jenkins/scripts/04_deploy_user_micro.sh'
            }
        }

    }
}
