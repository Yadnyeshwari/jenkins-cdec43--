// pipeline {
//     agent  {label '  worker-node'}
//     stages {
//         stage('git_checkout') {
//             steps {
//                 echo "pull-sucess"
//             }
//         }
//         stage('build-stage') {
//             steps {
               
//                 echo "build-sucess" 
//             }
//         }
//         stage('test-stage') {
//             steps {
               
//                 echo "test-sucess" 
//             }
//         }
//         stage('Deploy') {
//             steps {
//                 echo "deploy-sucess"
//             }
//         }
//     }
// }


// ---


pipeline {
    agent {label 'worker-node'}
    stages {
        stage('git_checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Anilbamnote/student-ui-app.git'
            }
        }
        stage('build-stage') {
            steps {
               sh '/opt/maven/bin/mvn clean package'
             
            }
        }
        stage('test-stage') {
            steps {
               withSonarQubeEnv(installationName: 'sonar', credentialsId: 'sonar-cred') {
                 // sh '/opt/maven/bin/mvn sonar:sonar'
              }
               //withCredentials([string(credentialsId: 'sonar-cred', variable: 'sonar')]) 
 // sh '''/opt/maven/bin/mvn sonar:sonar  -Dsonar.projectKey=student_app -Dsonar.host.url=http://172.31.6.67:9000 -Dsonar.login=565af3a2273c8eb65381e3762951d69511f57bf5'''
            }
        }
      stage('Quality-gate') {
        steps {
             timeout(10) {
    // some block
            }
          waitForQualityGate true
    }
  }
        stage('Deploy') {
            steps {
                echo "deploy-sucess"
            }
        }
    }
}
