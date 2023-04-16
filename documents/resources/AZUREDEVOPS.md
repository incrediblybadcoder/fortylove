# AZURE
## Azure SQL
* [Use Java and JDBC with Azure SQL Database](https://learn.microsoft.com/en-us/azure/azure-sql/database/connect-query-java?view=azuresql)

## Azure Devops
### Pipeline
* [Azure Pipeline Documentation](https://learn.microsoft.com/en-us/azure/devops/pipelines/?view=azure-devops)

### Heroku Deployment
Das Deployment auf Heroku funktioniert automatisch durch die Build-Pipeline nach einem Build auf dem Azure-Repo-Branch *main*.

#### [Heroku](https://dashboard.heroku.com/apps)
* Heroku App erstellen
* Buildpack *heroku/gradle* in App-Einstellungen/Settings hinzufügen
* Dem App unter *Resources* das Addon [New Relic APM](https://elements.heroku.com/addons/newrelic) hinzufügen. Heroku im eco-Dyno Modus legt Apps nach 30 min Inaktivität schlafen.
* New Relic ist ein Performance-Tool, welches die Seite regelmässig anpingt, um Performance-Daten zu generieren. Dies verhindert das Schalfen der Seite.

#### [Azure-Pipeline](../../azure-pipelines.yml)
* [PushToHeroku plugin](https://marketplace.visualstudio.com/items?itemName=ckrnstck.devops-heroku-tools) vom Azure Marketplace installieren
* [azure-pipeline.yaml](../../azure-pipelines.yml) muss als vmImage *windows-latest* konfiguriert sein (Plugin ist für Windoes konzipiert)
* [Heroku API-Key](https://dashboard.heroku.com/account/applications) erstellen
* In Pipeline über Azure-Seite folgende Variablen hinzufügen:
  * *HEROKU_APP_NAME* mit Name der App auf Heroku
  * *HEROKU_API_KEY* mit [Heroku API-Key](https://dashboard.heroku.com/account/applications)
* Task *PushToHeroku* über Tasks in Pipeline einfügen mit folgenden Werten:
  * *ApiKey* $(HEROKU_API_KEY)
  * *AppName* $(HEROKU_APP_NAME)
  * *PushRoot* $(System.DefaultWorkingDirectory)
* Condition an *PushToHeroku* Task anbringen, damit Task nur bei Azure-Repo Branch *main* läuft durch *condition: eq(variables['Build.SourceBranch'], 'refs/heads/main')*

#### [Procfile](../../Procfile)
Das [Procfile](../../Procfile) definiert, wie das gebuildete App auf Heroku gestartet wird. Es muss an Azure-Repo-Root erstellt werden.
Die Environment-Variablen JAVA_OPTS und JAR_OPTS können optional über die Heroku seite definiert werden und bieten die Möglichkeit,
Argumente an die JRT (JAVA_OPTS) und die App selber (JAR_OPTS) zu übergeben.

Wichtig ist hier, das .jar zu definieren, welches ausgeführt werden soll. Ohne spezifische Angabe, nimmt Heroku das erste, welches gefunden wird.
Dies ist unter Umständen das falsche (beim Build werden 2 .jar-Dateien gebuildet. Das mit -plain ist das Falsche.)

#### [system.properties](../../system.properties)
Das [system.properties](../../system.properties) File in Azure-Repo-Root definiert weitere Einstellungen, wie Heroku die App buildet.
Darin wird beispielsweise definiert, dass Heroku das JDK 17 benutzen soll. Standardmässig wird JDK 8 benutzt.

#### [application-properties](../../src/main/resources/application.properties)
In den [application-properties](../../src/main/resources/application.properties) muss der Server-Port mit *server.port=${PORT:8080}* spezifiziert werden.
Dies bewirkt, dass die App mit Port 8080 als Default-Port gestartet wird. Ist dieser besetzt, wird der Port in der Environmental Variable *PORT* herangezogen.
Dadurch kann Heroku die App mit einem zufälligen anderen Port starten, wenn der Port 8080 bereits belegt ist. Lokal wird hingegen der Port 8080 verwendet.