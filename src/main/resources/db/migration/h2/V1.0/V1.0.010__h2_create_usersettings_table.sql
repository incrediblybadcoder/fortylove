-- h2 specific script:
    -- no update of existing records needed
    -- gen_random_uuid() not available on h2

-- Add usersettings table

CREATE TABLE fortylove.usersettings (
    version integer,
    id uuid NOT NULL,
    theme character varying(255) NOT NULL
);

ALTER TABLE fortylove.usersettings
    ADD CONSTRAINT usersettings_pkey PRIMARY KEY (id);

-- Add usersettings_id to users table

ALTER TABLE fortylove.users
    ADD usersettings_id uuid;

ALTER TABLE fortylove.users
    ADD CONSTRAINT users_usersettings_id_key UNIQUE (usersettings_id);

ALTER TABLE fortylove.users
    ADD CONSTRAINT users_usersettings_fkey FOREIGN KEY (usersettings_id) REFERENCES fortylove.usersettings(id);