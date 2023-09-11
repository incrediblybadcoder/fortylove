/*
    Tabelle `user_authentication_details` in Schema `fortylove`.

    Diese Tabelle speichert Authentifizierungsdetails für jeden Benutzer, einschließlich des verschlüsselten Passworts
    und eines Aktivierungscodes. Diese Details wurden ausgelagert aus der `users` Tabelle, um die Verantwortlichkeiten
    klarer zu trennen und das Benutzermodell zu vereinfachen.

    Struktur der Tabelle:

    - `version`: Ein Feld zur Optimistischen Sperrung. Dieses Feld wird automatisch hochgezählt, wenn ein Eintrag
                 aktualisiert wird, und hilft dabei, gleichzeitige Änderungen am gleichen Eintrag zu verhindern.

    - `id`: Die eindeutige UUID des Eintrags. Diese dient als Primärschlüssel der Tabelle.

    - `encrypted_password`: Das verschlüsselte Passwort des Benutzers. Das Passwort wird vor der Speicherung
                            verschlüsselt, um die Sicherheit zu gewährleisten.

    - `activation_code`: Ein Aktivierungscode für den Benutzer. Dieser wird für Funktionen wie
                         die Bestätigung der E-Mail-Adresse oder das Zurücksetzen des Passworts verwendet.

    Beziehungen:

    - Jeder Eintrag in `user_authentication_details` hat genau einen zugehörigen Eintrag in `users`,
      und jeder Benutzer in `users` hat genau einen zugehörigen Eintrag in `user_authentication_details`.
      Dies stellt eine 1:1-Beziehung zwischen den Tabellen `users` und `user_authentication_details` dar.
*/

CREATE TABLE fortylove.authenticationdetails
(
    version            integer,
    id                 uuid                   NOT NULL,
    encrypted_password character varying(60)  NOT NULL,
    activation_code    character varying(255) NOT NULL
);

ALTER TABLE fortylove.authenticationdetails
    ADD CONSTRAINT authenticationdetails_pkey PRIMARY KEY (id);


-- Historisch: Die Spalte `encrypted_password` wurde aus der Tabelle `users` ausgelagert.
ALTER TABLE fortylove.users
    DROP COLUMN encrypted_password;

ALTER TABLE fortylove.users
    ADD authenticationdetails_id uuid NOT NULL;

ALTER TABLE fortylove.users
    ADD CONSTRAINT users_authenticationdetails_key UNIQUE (authenticationdetails_id);

ALTER TABLE fortylove.users
    ADD CONSTRAINT users_authenticationdetails_fkey FOREIGN KEY (authenticationdetails_id) REFERENCES fortylove.authenticationdetails(id);