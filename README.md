# fortylove

## Environment Variables
Folgende Environment Variables werden in den [application-properties](src/main/resources/application.properties) referenziert und müssen zum Ausführen der App gesetzt werden:
*SPRING_DATASOURCE_URL*
SQL Datenbank URL

*SPRING_DATASOURCE_USERNAME*
SQL Datenbank Username

*SPRING_DATASOURCE_PASSWORD*
SQL Datenbank Passwort

Folgende Environment Variables werden für den E-Mail Versand benötigt:

*MAIL_USERNAME*
Verwendeter E-Mail Username von z.B. Mailtrap oder SendGrid

*MAIL_PASSWORD*
Verwendetes E-Mail Passwort von z.B. Mailtrap oder SendGrid

*BASE_URL*
Basis URL der App - welche für den Aktivierungslink verwendet wird (z.B. http://localhost:8080/ oder https://fortylove.azurewebsites.net/ oder https://fortylove.ch/)
