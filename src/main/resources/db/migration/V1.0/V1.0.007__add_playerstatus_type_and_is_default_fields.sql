-- Add the new column "playerstatus_type" to the table "playerstatus"
ALTER TABLE fortylove.playerstatus
ADD playerstatus_type character varying(255) NOT NULL;

-- Add the new column "is_default" to the table "playerstatus"
ALTER TABLE fortylove.playerstatus
ADD is_default boolean;