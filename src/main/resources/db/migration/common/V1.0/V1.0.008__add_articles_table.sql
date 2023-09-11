-- Add the new column "userstatus" to the table "users"
CREATE TABLE fortylove.articles (
    version integer,
    date_time timestamp(6) without time zone NOT NULL,
    id uuid NOT NULL,
    text character varying(255) NOT NULL,
    title character varying(255) NOT NULL
);

ALTER TABLE fortylove.articles
    ADD CONSTRAINT articles_pkey PRIMARY KEY (id);