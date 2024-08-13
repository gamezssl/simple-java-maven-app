job('/Learning-jobs/Udemy-Jenkins-Cero-Experto-2024/6-Jobs-DSL-Maven/1-DSL-Job-Maven') {
    description('Java Maven App con DSL para el curso de Jenkins')
    scm {
        git('https://github.com/gamezssl/simple-java-maven-app.git', 'master') { node ->
            node / gitConfigName('gamezssl')
            node / gitConfigEmail('macloujulian@gmail.com')
        }
    }
    steps {
        maven {
          mavenInstallation('maven-jenkins')
          goals('-B -DskipTests clean package')
        }
        maven {
          mavenInstallation('maven-jenkins')
          goals('test')
        }
        shell('''
          echo "Entrega: Desplegando la aplicación" 
          java -jar "/home/jenkins/agent/workspace/Learning-jobs/Udemy-Jenkins-Cero-Experto-2024/6-Jobs-DSL-Maven/1-DSL-Job-Maven/target/my-app-1.0-SNAPSHOT.jar"
        ''')  
    }
    publishers {
        archiveArtifacts('target/*.jar')
        archiveJunit('target/surefire-reports/*.xml')
	slackNotifier {
            notifyAborted(true)
            notifyEveryFailure(true)
            notifyNotBuilt(false)
            notifyUnstable(false)
            notifyBackToNormal(true)
            notifySuccess(true)
            notifyRepeatedFailure(false)
            startNotification(false)
            includeTestSummary(false)
            includeCustomMessage(false)
            customMessage(null)
            sendAs(null)
            commitInfoChoice('NONE')
            teamDomain(null)
            authToken(null)
       }
    }
}