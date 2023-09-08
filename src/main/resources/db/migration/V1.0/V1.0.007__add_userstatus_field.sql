-- Add the new column "userstatus" to the table "users"
ALTER TABLE fortylove.users
ADD userstatus character varying(255) NOT NULL;