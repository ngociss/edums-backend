CREATE TABLE registration_periods
(
    id          INT AUTO_INCREMENT NOT NULL,
    semester_id INT          NOT NULL,
    name        VARCHAR(100) NOT NULL,
    start_time  datetime     NOT NULL,
    end_time    datetime     NOT NULL,
    status      VARCHAR(20)  NOT NULL,
    created_at  datetime     NOT NULL,
    deleted     BIT(1)       NOT NULL,
    CONSTRAINT pk_registration_periods PRIMARY KEY (id)
);

ALTER TABLE registration_periods
    ADD CONSTRAINT FK_REGISTRATION_PERIODS_ON_SEMESTER FOREIGN KEY (semester_id) REFERENCES semesters (id);

ALTER TABLE students
    MODIFY class_id INT NOT NULL;

ALTER TABLE lecturers
    MODIFY email VARCHAR (50) NOT NULL;

ALTER TABLE students
    MODIFY email VARCHAR (50) NOT NULL;

ALTER TABLE guardians
    MODIFY full_name VARCHAR (50) NOT NULL;

ALTER TABLE lecturers
    MODIFY full_name VARCHAR (50) NOT NULL;

ALTER TABLE students
    MODIFY full_name VARCHAR (50) NOT NULL;

ALTER TABLE students
    MODIFY major_id INT NOT NULL;

ALTER TABLE students
    MODIFY national_id VARCHAR (12) NOT NULL;

ALTER TABLE guardians
    MODIFY phone VARCHAR (10) NOT NULL;

ALTER TABLE students
    MODIFY status VARCHAR (20) NOT NULL;

ALTER TABLE students
    MODIFY student_code VARCHAR (10) NOT NULL;