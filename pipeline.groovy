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
            //    withSonarQubeEnv(installationName: 'sonar', credentialsId: 'sonar-cred') {
            //         sh '/opt/maven/bin/mvn sonar:sonar' 
            // }
sh '''/opt/maven/bin/mvn sonar:sonar  -Dsonar.projectKey=student_app -Dsonar.host.url=http://172.31.6.67:9000 -Dsonar.login=c2ddbbd412be0b40314cd5fb17f7c8fd44c9dbb7'''            }
        }
      stage('Quality-gate') {
        steps {
        timeout(10) {
    }
        waitForQualityGate--true
      }
    }
        stage('Deploy') {
            steps {
                echo "deploy-sucess"
            }
        }
    }
}
