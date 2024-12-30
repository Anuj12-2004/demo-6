 //Mention the tools which have been configured
   
   def mavenhome= tool name:"*****"
   
   // Mention how to trigger the Pipeline and how many Builds must be there and so on 
   
   properties([buildDiscarder(logRotator(artifactDaysToKeepStr: 
   '', artifactNumToKeepStr: '5', daysToKeepStr: '
   ', numToKeepStr: '5')), pipelineTriggers([pollSCM('* * * * *')])])
   
   // Getting the code from the GitHub
   
   stage('checkout code'){
       git branch: 'development', credentialsId: '*******', url: '********'
   }

   //Building the code in to packages by using maven 
    
   stage('build'){ 
       sh "${mavenhome}/bin/mvn clean package"
       
   //Executing the code quality report by using SonarQube
      
   }
   stage('execute sonarqube package'){
        sh "${mavenhome}/bin/mvn clean sonar:sonar"

    //Uploading the package into nexus
    
   }
   stage('upload buildartifact'){
       sh "${mavenhome}/bin/mvn clean deploy"
    
    //Deploying th application into Tomcat
       
   }
   stage('tomcat'){
       sshagent(['**********']) {
       sh "scp -o  StrictHostKeyChecking=no target
       /maven-web-application.war ec2-user@*******:/opt/apache-tomcat-9.0.64/webapps/"
}
   }
