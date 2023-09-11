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

-- Create temporary table to store usersettings_id

CREATE TABLE fortylove.temp (
    user_id UUID NOT NULL,
    usersettings_id UUID NOT NULL
);

-- Create temporary record for every user in users table with new usersettings_id

INSERT INTO fortylove.temp (user_id, usersettings_id)
SELECT
    id AS user_id,
    gen_random_uuid() AS usersettings_id
FROM
    fortylove.users;

-- Create usersettings records with previously generated usersettings_id

INSERT INTO fortylove.usersettings (version, id, theme)
SELECT
	0 AS version,
    usersettings_id AS id,
    'light' AS theme
FROM
    fortylove.temp;

-- Create relation between usersettings table and users table by adding previously generated usersettings_id as foreign key to users table

UPDATE fortylove.users
	SET usersettings_id = temp.usersettings_id
FROM fortylove.temp
	WHERE fortylove.users.id = fortylove.temp.user_id;

-- Set usersettings_id field on users table to NOT NULL

ALTER TABLE fortylove.users
	ALTER COLUMN usersettings_id SET NOT NULL;

-- Drop temporary table

DROP TABLE fortylove.temp;