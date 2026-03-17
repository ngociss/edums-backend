CREATE TABLE accounts
(
    id         INT AUTO_INCREMENT NOT NULL,
    role_id    INT NULL,
    username   VARCHAR(255) NULL,
    password   VARCHAR(255) NULL,
    status     VARCHAR(20) NULL,
    avatar_url VARCHAR(255) NULL,
    created_at datetime NULL,
    deleted    BIT(1) NOT NULL,
    CONSTRAINT pk_accounts PRIMARY KEY (id)
);

CREATE TABLE administrative_classes
(
    id               INT AUTO_INCREMENT NOT NULL,
    class_name       VARCHAR(255) NULL,
    head_lecturer_id INT NULL,
    cohort_id        INT NULL,
    major_id         INT NULL,
    max_capacity     INT NULL,
    deleted          BIT(1) NOT NULL,
    CONSTRAINT pk_administrative_classes PRIMARY KEY (id)
);

CREATE TABLE admission_applications
(
    id            INT AUTO_INCREMENT NOT NULL,
    period_id     INT    NOT NULL,
    full_name     VARCHAR(255) NULL,
    date_of_birth date NULL,
    email         VARCHAR(255) NULL,
    phone         VARCHAR(20) NULL,
    national_id   VARCHAR(12) NULL,
    address       VARCHAR(255) NULL,
    major_id      INT NULL,
    total_score   FLOAT NULL,
    block_id      INT NULL,
    status        VARCHAR(20) NULL,
    approval_date datetime NULL,
    deleted       BIT(1) NOT NULL,
    CONSTRAINT pk_admission_applications PRIMARY KEY (id)
);

CREATE TABLE admission_blocks
(
    id            INT AUTO_INCREMENT NOT NULL,
    block_name    VARCHAR(5)    NOT NULL,
    `description` VARCHAR(50) NULL,
    deleted       INT DEFAULT 0 NOT NULL,
    CONSTRAINT pk_admission_blocks PRIMARY KEY (id)
);

CREATE TABLE admission_periods
(
    id          INT AUTO_INCREMENT NOT NULL,
    period_name VARCHAR(255) NOT NULL,
    start_time  datetime     NOT NULL,
    end_time    datetime     NOT NULL,
    status      SMALLINT     NOT NULL,
    deleted     BIT(1)       NOT NULL,
    CONSTRAINT pk_admission_periods PRIMARY KEY (id)
);

CREATE TABLE attendance
(
    id                INT AUTO_INCREMENT NOT NULL,
    session_id        INT NULL,
    registration_id   INT NULL,
    attendance_status VARCHAR(20) NULL,
    note              VARCHAR(255) NULL,
    deleted           BIT(1) NOT NULL,
    CONSTRAINT pk_attendance PRIMARY KEY (id)
);

CREATE TABLE benchmark_scores
(
    id        INT AUTO_INCREMENT NOT NULL,
    major_id  INT NULL,
    block_id  INT NULL,
    period_id INT    NOT NULL,
    score     FLOAT NULL,
    deleted   BIT(1) NOT NULL,
    CONSTRAINT pk_benchmark_scores PRIMARY KEY (id)
);

CREATE TABLE class_sessions
(
    id                    INT AUTO_INCREMENT NOT NULL,
    section_id            INT NULL,
    room_id               INT NULL,
    recurring_schedule_id INT NULL,
    session_date          date NULL,
    start_period          INT NULL,
    end_period            INT NULL,
    lesson_content        TEXT NULL,
    status                VARCHAR(20) NULL,
    deleted               BIT(1) NOT NULL,
    CONSTRAINT pk_class_sessions PRIMARY KEY (id)
);

CREATE TABLE classrooms
(
    id        INT AUTO_INCREMENT NOT NULL,
    room_name VARCHAR(255) NULL,
    capacity  INT NULL,
    room_type VARCHAR(255) NULL,
    deleted   BIT(1) NOT NULL,
    CONSTRAINT pk_classrooms PRIMARY KEY (id)
);

CREATE TABLE cohorts
(
    id          INT AUTO_INCREMENT NOT NULL,
    cohort_name VARCHAR(255) NULL,
    start_year  INT NULL,
    end_year    INT NULL,
    status      VARCHAR(20) NULL,
    deleted     BIT(1) NOT NULL,
    CONSTRAINT pk_cohorts PRIMARY KEY (id)
);

CREATE TABLE course_registrations
(
    id                INT AUTO_INCREMENT NOT NULL,
    student_id        INT NULL,
    section_id        INT NULL,
    registration_time datetime NULL,
    status            VARCHAR(20) NULL,
    deleted           BIT(1) NOT NULL,
    CONSTRAINT pk_course_registrations PRIMARY KEY (id)
);

CREATE TABLE course_sections
(
    id           INT AUTO_INCREMENT NOT NULL,
    section_code VARCHAR(50) NOT NULL,
    display_name VARCHAR(255) NULL,
    course_id    INT         NOT NULL,
    lecturer_id  INT NULL,
    semester_id  INT         NOT NULL,
    max_capacity INT NULL,
    status       VARCHAR(20) NULL,
    created_at   datetime NULL,
    deleted      BIT(1)      NOT NULL,
    CONSTRAINT pk_course_sections PRIMARY KEY (id)
);

CREATE TABLE courses
(
    id                     INT AUTO_INCREMENT NOT NULL,
    course_code            VARCHAR(20) NULL,
    course_name            VARCHAR(255) NULL,
    credits                INT NULL,
    faculty_id             INT NULL,
    prerequisite_course_id INT NULL,
    status                 VARCHAR(20) NULL,
    deleted                BIT(1) NOT NULL,
    CONSTRAINT pk_courses PRIMARY KEY (id)
);

CREATE TABLE faculties
(
    id           INT AUTO_INCREMENT NOT NULL,
    faculty_name VARCHAR(255) NULL,
    faculty_code VARCHAR(50) NULL,
    deleted      BIT(1) NOT NULL,
    CONSTRAINT pk_faculties PRIMARY KEY (id)
);

CREATE TABLE grade_components
(
    id                INT AUTO_INCREMENT NOT NULL,
    component_name    VARCHAR(50) NULL,
    weight_percentage FLOAT NULL,
    course_id         INT NULL,
    deleted           BIT(1) NOT NULL,
    CONSTRAINT pk_grade_components PRIMARY KEY (id)
);

CREATE TABLE grade_details
(
    id           INT AUTO_INCREMENT NOT NULL,
    report_id    INT NULL,
    component_id INT NULL,
    score        FLOAT NULL,
    deleted      BIT(1) NOT NULL,
    CONSTRAINT pk_grade_details PRIMARY KEY (id)
);

CREATE TABLE grade_reports
(
    id              INT AUTO_INCREMENT NOT NULL,
    registration_id INT NULL,
    final_score     FLOAT NULL,
    letter_grade    VARCHAR(5) NULL,
    status          VARCHAR(20) NULL,
    created_at      datetime NULL,
    deleted         BIT(1) NOT NULL,
    CONSTRAINT pk_grade_reports PRIMARY KEY (id)
);

CREATE TABLE guardians
(
    id           INT AUTO_INCREMENT NOT NULL,
    account_id   INT NULL,
    full_name    VARCHAR(50) NULL,
    phone        VARCHAR(10) NULL,
    relationship VARCHAR(50) NULL,
    deleted      BIT(1) NOT NULL,
    CONSTRAINT pk_guardians PRIMARY KEY (id)
);

CREATE TABLE lecturers
(
    id              INT AUTO_INCREMENT NOT NULL,
    account_id      INT NULL,
    full_name       VARCHAR(50) NULL,
    academic_degree VARCHAR(50) NULL,
    email           VARCHAR(50) NULL,
    phone           VARCHAR(10) NULL,
    deleted         BIT(1) NOT NULL,
    CONSTRAINT pk_lecturers PRIMARY KEY (id)
);

CREATE TABLE majors
(
    id         INT AUTO_INCREMENT NOT NULL,
    faculty_id INT NULL,
    major_name VARCHAR(255) NULL,
    major_code VARCHAR(50) NULL,
    deleted    BIT(1) NOT NULL,
    CONSTRAINT pk_majors PRIMARY KEY (id)
);

CREATE TABLE recurring_schedules
(
    id           INT AUTO_INCREMENT NOT NULL,
    section_id   INT NULL,
    room_id      INT NULL,
    day_of_week  INT NULL,
    start_period INT NULL,
    end_period   INT NULL,
    start_week   INT NULL,
    end_week     INT NULL,
    deleted      BIT(1) NOT NULL,
    created_at   datetime NULL,
    CONSTRAINT pk_recurring_schedules PRIMARY KEY (id)
);

CREATE TABLE role_permissions
(
    id            INT AUTO_INCREMENT NOT NULL,
    role_id       INT NULL,
    function_code VARCHAR(255) NULL,
    deleted       BIT(1) NOT NULL,
    CONSTRAINT pk_role_permissions PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id        INT AUTO_INCREMENT NOT NULL,
    role_name VARCHAR(255) NULL,
    deleted   BIT(1) NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE semesters
(
    id              INT AUTO_INCREMENT NOT NULL,
    semester_number INT NULL,
    academic_year   VARCHAR(50) NULL,
    start_date      date NULL,
    end_date        date NULL,
    total_weeks     INT NULL,
    deleted         BIT(1) NOT NULL,
    CONSTRAINT pk_semesters PRIMARY KEY (id)
);

CREATE TABLE specializations
(
    id                  INT AUTO_INCREMENT NOT NULL,
    major_id            INT NULL,
    specialization_name VARCHAR(255) NULL,
    deleted             BIT(1) NOT NULL,
    CONSTRAINT pk_specializations PRIMARY KEY (id)
);

CREATE TABLE students
(
    id                INT AUTO_INCREMENT NOT NULL,
    account_id        INT NULL,
    class_id          INT NULL,
    major_id          INT NULL,
    specialization_id INT NULL,
    guardian_id       INT NULL,
    student_code      VARCHAR(10) NULL,
    full_name         VARCHAR(50) NULL,
    email             VARCHAR(50) NULL,
    address           VARCHAR(250) NULL,
    date_of_birth     date NULL,
    gender            BIT(1) NULL,
    phone             VARCHAR(10) NULL,
    national_id       VARCHAR(12) NULL,
    ethnicity         VARCHAR(20) NULL,
    religion          VARCHAR(50) NULL,
    place_of_birth    VARCHAR(50) NULL,
    nationality       VARCHAR(50) NULL,
    status            VARCHAR(20) NULL,
    created_at        datetime NULL,
    deleted           BIT(1) NOT NULL,
    CONSTRAINT pk_students PRIMARY KEY (id)
);

ALTER TABLE accounts
    ADD CONSTRAINT uc_accounts_username UNIQUE (username);

ALTER TABLE admission_blocks
    ADD CONSTRAINT uc_admission_blocks_block_name UNIQUE (block_name);

ALTER TABLE grade_reports
    ADD CONSTRAINT uc_grade_reports_registration UNIQUE (registration_id);

ALTER TABLE guardians
    ADD CONSTRAINT uc_guardians_account UNIQUE (account_id);

ALTER TABLE lecturers
    ADD CONSTRAINT uc_lecturers_account UNIQUE (account_id);

ALTER TABLE students
    ADD CONSTRAINT uc_students_account UNIQUE (account_id);

ALTER TABLE students
    ADD CONSTRAINT uc_students_email UNIQUE (email);

ALTER TABLE students
    ADD CONSTRAINT uc_students_national UNIQUE (national_id);

ALTER TABLE students
    ADD CONSTRAINT uc_students_student_code UNIQUE (student_code);

ALTER TABLE grade_details
    ADD CONSTRAINT uk_report_component UNIQUE (report_id, component_id);

ALTER TABLE course_sections
    ADD CONSTRAINT uk_section_code_semester UNIQUE (section_code, semester_id);

ALTER TABLE attendance
    ADD CONSTRAINT uk_session_registration UNIQUE (session_id, registration_id);

ALTER TABLE course_registrations
    ADD CONSTRAINT uk_student_section UNIQUE (student_id, section_id);

CREATE INDEX idx_grade_components_course_name ON grade_components (course_id, component_name);

CREATE INDEX idx_recurring_room_day_period ON recurring_schedules (room_id, day_of_week, start_period);

ALTER TABLE accounts
    ADD CONSTRAINT FK_ACCOUNTS_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE administrative_classes
    ADD CONSTRAINT FK_ADMINISTRATIVE_CLASSES_ON_COHORT FOREIGN KEY (cohort_id) REFERENCES cohorts (id);

ALTER TABLE administrative_classes
    ADD CONSTRAINT FK_ADMINISTRATIVE_CLASSES_ON_HEAD_LECTURER FOREIGN KEY (head_lecturer_id) REFERENCES lecturers (id);

ALTER TABLE administrative_classes
    ADD CONSTRAINT FK_ADMINISTRATIVE_CLASSES_ON_MAJOR FOREIGN KEY (major_id) REFERENCES majors (id);

ALTER TABLE admission_applications
    ADD CONSTRAINT FK_ADMISSION_APPLICATIONS_ON_BLOCK FOREIGN KEY (block_id) REFERENCES admission_blocks (id);

ALTER TABLE admission_applications
    ADD CONSTRAINT FK_ADMISSION_APPLICATIONS_ON_MAJOR FOREIGN KEY (major_id) REFERENCES majors (id);

ALTER TABLE admission_applications
    ADD CONSTRAINT FK_ADMISSION_APPLICATIONS_ON_PERIOD FOREIGN KEY (period_id) REFERENCES admission_periods (id);

ALTER TABLE attendance
    ADD CONSTRAINT FK_ATTENDANCE_ON_REGISTRATION FOREIGN KEY (registration_id) REFERENCES course_registrations (id);

ALTER TABLE attendance
    ADD CONSTRAINT FK_ATTENDANCE_ON_SESSION FOREIGN KEY (session_id) REFERENCES class_sessions (id);

ALTER TABLE benchmark_scores
    ADD CONSTRAINT FK_BENCHMARK_SCORES_ON_BLOCK FOREIGN KEY (block_id) REFERENCES admission_blocks (id);

ALTER TABLE benchmark_scores
    ADD CONSTRAINT FK_BENCHMARK_SCORES_ON_MAJOR FOREIGN KEY (major_id) REFERENCES majors (id);

ALTER TABLE benchmark_scores
    ADD CONSTRAINT FK_BENCHMARK_SCORES_ON_PERIOD FOREIGN KEY (period_id) REFERENCES admission_periods (id);

ALTER TABLE class_sessions
    ADD CONSTRAINT FK_CLASS_SESSIONS_ON_RECURRING_SCHEDULE FOREIGN KEY (recurring_schedule_id) REFERENCES recurring_schedules (id);

ALTER TABLE class_sessions
    ADD CONSTRAINT FK_CLASS_SESSIONS_ON_ROOM FOREIGN KEY (room_id) REFERENCES classrooms (id);

ALTER TABLE class_sessions
    ADD CONSTRAINT FK_CLASS_SESSIONS_ON_SECTION FOREIGN KEY (section_id) REFERENCES course_sections (id);

ALTER TABLE courses
    ADD CONSTRAINT FK_COURSES_ON_FACULTY FOREIGN KEY (faculty_id) REFERENCES faculties (id);

ALTER TABLE courses
    ADD CONSTRAINT FK_COURSES_ON_PREREQUISITE_COURSE FOREIGN KEY (prerequisite_course_id) REFERENCES courses (id);

ALTER TABLE course_registrations
    ADD CONSTRAINT FK_COURSE_REGISTRATIONS_ON_SECTION FOREIGN KEY (section_id) REFERENCES course_sections (id);

ALTER TABLE course_registrations
    ADD CONSTRAINT FK_COURSE_REGISTRATIONS_ON_STUDENT FOREIGN KEY (student_id) REFERENCES students (id);

ALTER TABLE course_sections
    ADD CONSTRAINT FK_COURSE_SECTIONS_ON_COURSE FOREIGN KEY (course_id) REFERENCES courses (id);

ALTER TABLE course_sections
    ADD CONSTRAINT FK_COURSE_SECTIONS_ON_LECTURER FOREIGN KEY (lecturer_id) REFERENCES lecturers (id);

ALTER TABLE course_sections
    ADD CONSTRAINT FK_COURSE_SECTIONS_ON_SEMESTER FOREIGN KEY (semester_id) REFERENCES semesters (id);

ALTER TABLE grade_components
    ADD CONSTRAINT FK_GRADE_COMPONENTS_ON_COURSE FOREIGN KEY (course_id) REFERENCES courses (id);

ALTER TABLE grade_details
    ADD CONSTRAINT FK_GRADE_DETAILS_ON_COMPONENT FOREIGN KEY (component_id) REFERENCES grade_components (id);

ALTER TABLE grade_details
    ADD CONSTRAINT FK_GRADE_DETAILS_ON_REPORT FOREIGN KEY (report_id) REFERENCES grade_reports (id);

ALTER TABLE grade_reports
    ADD CONSTRAINT FK_GRADE_REPORTS_ON_REGISTRATION FOREIGN KEY (registration_id) REFERENCES course_registrations (id);

ALTER TABLE guardians
    ADD CONSTRAINT FK_GUARDIANS_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES accounts (id);

ALTER TABLE lecturers
    ADD CONSTRAINT FK_LECTURERS_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES accounts (id);

ALTER TABLE majors
    ADD CONSTRAINT FK_MAJORS_ON_FACULTY FOREIGN KEY (faculty_id) REFERENCES faculties (id);

ALTER TABLE recurring_schedules
    ADD CONSTRAINT FK_RECURRING_SCHEDULES_ON_ROOM FOREIGN KEY (room_id) REFERENCES classrooms (id);

ALTER TABLE recurring_schedules
    ADD CONSTRAINT FK_RECURRING_SCHEDULES_ON_SECTION FOREIGN KEY (section_id) REFERENCES course_sections (id);

ALTER TABLE role_permissions
    ADD CONSTRAINT FK_ROLE_PERMISSIONS_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE specializations
    ADD CONSTRAINT FK_SPECIALIZATIONS_ON_MAJOR FOREIGN KEY (major_id) REFERENCES majors (id);

ALTER TABLE students
    ADD CONSTRAINT FK_STUDENTS_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES accounts (id);

ALTER TABLE students
    ADD CONSTRAINT FK_STUDENTS_ON_CLASS FOREIGN KEY (class_id) REFERENCES administrative_classes (id);

ALTER TABLE students
    ADD CONSTRAINT FK_STUDENTS_ON_GUARDIAN FOREIGN KEY (guardian_id) REFERENCES guardians (id);

ALTER TABLE students
    ADD CONSTRAINT FK_STUDENTS_ON_MAJOR FOREIGN KEY (major_id) REFERENCES majors (id);

ALTER TABLE students
    ADD CONSTRAINT FK_STUDENTS_ON_SPECIALIZATION FOREIGN KEY (specialization_id) REFERENCES specializations (id);