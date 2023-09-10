-- Historisch: Die Spalte `encrypted_password` wurde aus der Tabelle `users` ausgelagert.
ALTER TABLE fortylove.authenticationdetails
    DROP COLUMN activation_code;

CREATE TABLE fortylove.unvalidatedusers
(
    version integer,
    id      uuid NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    encrypted_password character varying(60) NOT NULL,
    activation_code    character varying(255) NOT NULL
);