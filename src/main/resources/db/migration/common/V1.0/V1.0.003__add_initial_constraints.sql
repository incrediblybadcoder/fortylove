ALTER TABLE fortylove.bookingsettings
    ADD CONSTRAINT bookingsettings_pkey PRIMARY KEY (id);

ALTER TABLE fortylove.timeslots
    ADD CONSTRAINT timeslots_pkey PRIMARY KEY (id);

ALTER TABLE fortylove.timeslots
    ADD CONSTRAINT timeslots_bookingsettings_fkey FOREIGN KEY (bookingsettings_id) REFERENCES fortylove.bookingsettings(id);

ALTER TABLE fortylove.playerstatus
    ADD CONSTRAINT playerstatus_pkey PRIMARY KEY (id);

ALTER TABLE fortylove.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);

ALTER TABLE fortylove.privileges
    ADD CONSTRAINT privileges_pkey PRIMARY KEY (id);

ALTER TABLE fortylove.roles_privileges
    ADD CONSTRAINT roles_privileges_pkey PRIMARY KEY (privilege_id, role_id);

ALTER TABLE fortylove.roles_privileges
    ADD CONSTRAINT roles_privileges_privileges_fkey FOREIGN KEY (privilege_id) REFERENCES fortylove.privileges(id);

ALTER TABLE fortylove.roles_privileges
    ADD CONSTRAINT roles_privileges_roles_fkey FOREIGN KEY (role_id) REFERENCES fortylove.roles(id);

ALTER TABLE fortylove.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

ALTER TABLE fortylove.users
    ADD CONSTRAINT users_email_key UNIQUE (email);

ALTER TABLE fortylove.users
    ADD CONSTRAINT users_playerstatus_fkey FOREIGN KEY (playerstatus_id) REFERENCES fortylove.playerstatus(id);

ALTER TABLE fortylove.users_roles
    ADD CONSTRAINT users_roles_pkey PRIMARY KEY (role_id, user_id);

ALTER TABLE fortylove.users_roles
    ADD CONSTRAINT users_roles_users_fkey FOREIGN KEY (user_id) REFERENCES fortylove.users(id);

ALTER TABLE fortylove.users_roles
    ADD CONSTRAINT users_roles_roles_fkey FOREIGN KEY (role_id) REFERENCES fortylove.roles(id);

ALTER TABLE fortylove.courts
    ADD CONSTRAINT courts_pkey PRIMARY KEY (id);

ALTER TABLE fortylove.bookings
    ADD CONSTRAINT bookings_pkey PRIMARY KEY (id);

ALTER TABLE fortylove.bookings
    ADD CONSTRAINT bookings_timeslots_fkey FOREIGN KEY (timeslot_id) REFERENCES fortylove.timeslots(id);

ALTER TABLE fortylove.bookings
    ADD CONSTRAINT bookings_users_owner_fkey FOREIGN KEY (user_owner_id) REFERENCES fortylove.users(id);

ALTER TABLE fortylove.bookings
    ADD CONSTRAINT bookings_courts_fkey FOREIGN KEY (court_id) REFERENCES fortylove.courts(id);

ALTER TABLE fortylove.bookings_opponents
    ADD CONSTRAINT bookings_opponents_pkey PRIMARY KEY (booking_id, user_opponent_id);

ALTER TABLE fortylove.bookings_opponents
    ADD CONSTRAINT bookings_opponents_bookings_fkey FOREIGN KEY (booking_id) REFERENCES fortylove.bookings(id);

ALTER TABLE fortylove.bookings_opponents
    ADD CONSTRAINT bookings_opponents_users_opponent_fkey FOREIGN KEY (user_opponent_id) REFERENCES fortylove.users(id);