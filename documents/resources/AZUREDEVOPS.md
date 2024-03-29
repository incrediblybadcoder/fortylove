# AZURE

## Azure App Service
In den WebApps des Azure App Services müssen folgende Environment Variablen für die DB Verbindung gesetzt werden (definiert im [application-properties](../../src/main/resources/application.properties)):
* SPRING_PROFILE
* SPRING_DATASOURCE_URL
* SPRING_DATASOURCE_USERNAME
* SPRING_DATASOURCE_PASSWORD

Um E-Mail Versand zu ermöglichen, müssen folgende Environment Variablen gesetzt werden:
* MAIL_USERNAME
* MAIL_PASSWORD
* BASE_URL

## Azure SQL
* [Use Java and JDBC with Azure SQL Database](https://learn.microsoft.com/en-us/azure/azure-sql/database/connect-query-java?view=azuresql)

### SQL Server Connection
#### Local
* Name: jdbc:postgresql://localhost:5432/fortylovedb
* User: locally defined user's username
* Password: locally defined user's password

#### Azure postgresql main
* Name: jdbc:postgresql://fortylove.postgres.database.azure.com:5432/fortylovedb?sslmode=require
* User: 
* Password: 

#### Azure h2 dev
* Name: jdbc:h2:mem:fortylove;DB_CLOSE_DELAY=-1
* User: sa
* Password:

## Azure Devops
### Pipeline
* [Azure Pipeline Documentation](https://learn.microsoft.com/en-us/azure/devops/pipelines/?view=azure-devops)

#### Pipeline-Runs
Die Pipeline wird in folgenden Situationen ausgeführt:
* Pull-Request auf *main*-Branch
* Pull-Request auf *development*-Branch
* Code-Push auf Branches im Ordner *feature/**

#### Environment Variables
Spring-Boot-Data-JPA hat im [application-properties](../../src/main/resources/application.properties) diverse Variablen definiert. Diese sind während dem Gradle-Build
nötig, um die Verbindung zur Datenbank zu testen. Diese Werte sind mit Environment Variables umgesetzt, welche an folgenden Orten eingefügt werden müssen:
* Intellij Run-Configuration
* Gradle-Build-Task in der Pipeline
* Azure App Service Apps (in den Konfigurationen)

Es gibt die Möglichkeit bei Code-Pushes auf die *feature*-Branches den Pipeline-Run zu unterbinden. Dies kann hilfreich sein, wenn viele kleine Änderungen separat
gepushed werden sollen. Durch das einfügen von `[skip ci]` in der Commit-Message beim Push auf einen *feature*-Branch, wird die Pipeline nicht ausgeführt.

