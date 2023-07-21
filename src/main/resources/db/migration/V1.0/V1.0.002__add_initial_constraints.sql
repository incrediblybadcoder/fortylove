ALTER TABLE ONLY fortylove.bookingsettings
    ADD CONSTRAINT bookingsettings_pkey PRIMARY KEY (id);

ALTER TABLE ONLY fortylove.timeslots
    ADD CONSTRAINT timeslots_pkey PRIMARY KEY (id);

ALTER TABLE ONLY fortylove.timeslots
    ADD CONSTRAINT bookingsettings_fkey FOREIGN KEY (bookingsettings_id) REFERENCES fortylove.bookingsettings(id);

ALTER TABLE ONLY fortylove.playerstatus
    ADD CONSTRAINT player_status_pkey PRIMARY KEY (id);

ALTER TABLE ONLY fortylove.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);

ALTER TABLE ONLY fortylove.privileges
    ADD CONSTRAINT privileges_pkey PRIMARY KEY (id);

ALTER TABLE ONLY fortylove.roles_privileges
    ADD CONSTRAINT roles_privileges_pkey PRIMARY KEY (privilege_id, role_id);

ALTER TABLE ONLY fortylove.roles_privileges
    ADD CONSTRAINT privileges_fkey FOREIGN KEY (privilege_id) REFERENCES fortylove.privileges(id);

ALTER TABLE ONLY fortylove.roles_privileges
    ADD CONSTRAINT roles_fkey FOREIGN KEY (role_id) REFERENCES fortylove.roles(id);

ALTER TABLE ONLY fortylove.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

ALTER TABLE ONLY fortylove.users
    ADD CONSTRAINT users_email_key UNIQUE (email);

ALTER TABLE ONLY fortylove.users
    ADD CONSTRAINT playerstatus_fkey FOREIGN KEY (playerstatus_id) REFERENCES fortylove.playerstatus(id);

ALTER TABLE ONLY fortylove.users_roles
    ADD CONSTRAINT users_roles_pkey PRIMARY KEY (role_id, user_id);

ALTER TABLE ONLY fortylove.users_roles
    ADD CONSTRAINT users_fkey FOREIGN KEY (user_id) REFERENCES fortylove.users(id);

ALTER TABLE ONLY fortylove.users_roles
    ADD CONSTRAINT roles_fkey FOREIGN KEY (role_id) REFERENCES fortylove.roles(id);

ALTER TABLE ONLY fortylove.courts
    ADD CONSTRAINT courts_pkey PRIMARY KEY (id);

ALTER TABLE ONLY fortylove.bookings
    ADD CONSTRAINT bookings_pkey PRIMARY KEY (id);

ALTER TABLE ONLY fortylove.bookings
    ADD CONSTRAINT timeslots_fkey FOREIGN KEY (timeslot_id) REFERENCES fortylove.timeslots(id);

ALTER TABLE ONLY fortylove.bookings
    ADD CONSTRAINT users_owner_fkey FOREIGN KEY (user_owner_id) REFERENCES fortylove.users(id);

ALTER TABLE ONLY fortylove.bookings
    ADD CONSTRAINT courts_fkey FOREIGN KEY (court_id) REFERENCES fortylove.courts(id);

ALTER TABLE ONLY fortylove.bookings_opponents
    ADD CONSTRAINT bookings_opponents_pkey PRIMARY KEY (booking_id, user_opponent_id);

ALTER TABLE ONLY fortylove.bookings_opponents
    ADD CONSTRAINT bookings_fkey FOREIGN KEY (booking_id) REFERENCES fortylove.bookings(id);

ALTER TABLE ONLY fortylove.bookings_opponents
    ADD CONSTRAINT users_opponent_fkey FOREIGN KEY (user_opponent_id) REFERENCES fortylove.users(id);