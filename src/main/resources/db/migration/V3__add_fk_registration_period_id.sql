ALTER TABLE course_registrations
    ADD registration_period_id INT NULL;

ALTER TABLE course_registrations
    MODIFY registration_period_id INT NOT NULL;

ALTER TABLE course_registrations
    ADD CONSTRAINT FK_COURSE_REGISTRATIONS_ON_REGISTRATION_PERIOD FOREIGN KEY (registration_period_id) REFERENCES registration_periods (id);