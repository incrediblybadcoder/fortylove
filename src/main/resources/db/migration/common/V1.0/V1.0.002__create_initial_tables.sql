CREATE TABLE fortylove.bookingsettings (
    version integer,
    id uuid NOT NULL
);

CREATE TABLE fortylove.timeslots (
    bookable boolean,
    index integer,
    version integer,
    bookingsettings_id uuid,
    id uuid NOT NULL
);

CREATE TABLE fortylove.playerstatus (
    bookable_days_in_advance integer,
    bookings_per_day integer,
    version integer,
    id uuid NOT NULL,
    name character varying(255) NOT NULL
);

CREATE TABLE fortylove.roles (
    version integer,
    id uuid NOT NULL,
    name character varying(255) NOT NULL
);

CREATE TABLE fortylove.privileges (
    version integer,
    id uuid NOT NULL,
    name character varying(255) NOT NULL
);

CREATE TABLE fortylove.roles_privileges (
    privilege_id uuid NOT NULL,
    role_id uuid NOT NULL
);

CREATE TABLE fortylove.users (
    enabled boolean,
    version integer,
    id uuid NOT NULL,
    playerstatus_id uuid NOT NULL,
    encrypted_password character varying(60) NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL
);

CREATE TABLE fortylove.users_roles (
    role_id uuid NOT NULL,
    user_id uuid NOT NULL
);

CREATE TABLE fortylove.bookings (
    booking_date date NOT NULL,
    version integer,
    court_id uuid NOT NULL,
    id uuid NOT NULL,
    timeslot_id uuid NOT NULL,
    user_owner_id uuid NOT NULL
);

CREATE TABLE fortylove.bookings_opponents (
    booking_id uuid NOT NULL,
    user_opponent_id uuid NOT NULL
);

CREATE TABLE fortylove.courts (
    number integer,
    version integer,
    id uuid NOT NULL,
    court_type character varying(255) NOT NULL,
    name character varying(255)
);