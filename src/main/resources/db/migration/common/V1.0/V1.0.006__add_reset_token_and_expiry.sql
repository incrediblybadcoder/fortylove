ALTER TABLE fortylove.authenticationdetails
    ADD COLUMN reset_token character varying(255);

ALTER TABLE fortylove.authenticationdetails
    ADD COLUMN token_expire_date timestamp;
