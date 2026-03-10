-- ============================================================
-- V1__init_schema.sql
-- EduMS - Initial Database Schema
-- ============================================================

-- ----------------------------
-- roles
-- ----------------------------
CREATE TABLE IF NOT EXISTS roles (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    role_name   VARCHAR(255) NOT NULL,
    status      INT          NOT NULL DEFAULT 1,
    deleted     BOOLEAN      NOT NULL DEFAULT FALSE
);

-- ----------------------------
-- accounts
-- ----------------------------
CREATE TABLE IF NOT EXISTS accounts (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    role_id     INT,
    username    VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    status      VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    avatar_url  TEXT,
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted     BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_accounts_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- ----------------------------
-- role_permissions
-- ----------------------------
CREATE TABLE IF NOT EXISTS role_permissions (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    role_id         INT          NOT NULL,
    function_code   VARCHAR(255) NOT NULL,
    deleted         BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_role_permissions_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- ----------------------------
-- guardians
-- ----------------------------
CREATE TABLE IF NOT EXISTS guardians (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    account_id      INT UNIQUE,
    full_name       VARCHAR(50)  NOT NULL,
    phone           VARCHAR(10),
    relationship    VARCHAR(50),
    deleted         BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_guardians_account FOREIGN KEY (account_id) REFERENCES accounts (id)
);

-- ----------------------------
-- faculties
-- ----------------------------
CREATE TABLE IF NOT EXISTS faculties (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    faculty_name    VARCHAR(255) NOT NULL,
    faculty_code    VARCHAR(50)  NOT NULL,
    deleted         BOOLEAN      NOT NULL DEFAULT FALSE
);

-- ----------------------------
-- majors
-- ----------------------------
CREATE TABLE IF NOT EXISTS majors (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    faculty_id  INT,
    major_name  VARCHAR(255) NOT NULL,
    major_code  VARCHAR(50)  NOT NULL,
    deleted     BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_majors_faculty FOREIGN KEY (faculty_id) REFERENCES faculties (id)
);

-- ----------------------------
-- specializations
-- ----------------------------
CREATE TABLE IF NOT EXISTS specializations (
    id                      INT AUTO_INCREMENT PRIMARY KEY,
    major_id                INT,
    specialization_name     VARCHAR(255) NOT NULL,
    deleted                 BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_specializations_major FOREIGN KEY (major_id) REFERENCES majors (id)
);

-- ----------------------------
-- cohorts
-- ----------------------------
CREATE TABLE IF NOT EXISTS cohorts (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    cohort_name     VARCHAR(255) NOT NULL,
    start_year      INT,
    end_year        INT,
    status          VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    deleted         BOOLEAN      NOT NULL DEFAULT FALSE
);

-- ----------------------------
-- lecturers
-- ----------------------------
CREATE TABLE IF NOT EXISTS lecturers (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    account_id          INT UNIQUE,
    full_name           VARCHAR(50),
    academic_degree     VARCHAR(50),
    email               VARCHAR(50),
    phone               VARCHAR(10),
    deleted             BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_lecturers_account FOREIGN KEY (account_id) REFERENCES accounts (id)
);

-- ----------------------------
-- administrative_classes
-- ----------------------------
CREATE TABLE IF NOT EXISTS administrative_classes (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    class_name          VARCHAR(255) NOT NULL,
    head_lecturer_id    INT,
    cohort_id           INT,
    major_id            INT,
    max_capacity        INT,
    deleted             BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_admin_classes_lecturer    FOREIGN KEY (head_lecturer_id) REFERENCES lecturers (id),
    CONSTRAINT fk_admin_classes_cohort      FOREIGN KEY (cohort_id)        REFERENCES cohorts (id),
    CONSTRAINT fk_admin_classes_major       FOREIGN KEY (major_id)         REFERENCES majors (id)
);

-- ----------------------------
-- students
-- ----------------------------
CREATE TABLE IF NOT EXISTS students (
    id                      INT AUTO_INCREMENT PRIMARY KEY,
    account_id              INT UNIQUE,
    class_id                INT,
    specialization_id       INT,
    guardian_id             INT,
    student_code            VARCHAR(10)  UNIQUE,
    full_name               VARCHAR(50),
    email                   VARCHAR(50)  UNIQUE,
    address                 VARCHAR(250),
    date_of_birth           DATE,
    gender                  BOOLEAN,
    phone                   VARCHAR(10),
    national_id             VARCHAR(12)  UNIQUE,
    ethnicity               VARCHAR(20),
    religion                VARCHAR(50),
    place_of_birth          VARCHAR(50),
    nationality             VARCHAR(50),
    status                  VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    created_at              DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted                 BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_students_account          FOREIGN KEY (account_id)        REFERENCES accounts (id),
    CONSTRAINT fk_students_admin_class      FOREIGN KEY (class_id)          REFERENCES administrative_classes (id),
    CONSTRAINT fk_students_specialization   FOREIGN KEY (specialization_id) REFERENCES specializations (id),
    CONSTRAINT fk_students_guardian         FOREIGN KEY (guardian_id)       REFERENCES guardians (id)
);

-- ----------------------------
-- admission_applications
-- ----------------------------
CREATE TABLE IF NOT EXISTS admission_applications (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    full_name       VARCHAR(255),
    date_of_birth   DATE,
    email           VARCHAR(255),
    phone           VARCHAR(20),
    national_id     VARCHAR(12),
    address         VARCHAR(255),
    major_id        INT,
    total_score     FLOAT,
    exam_group      VARCHAR(5),
    status          VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
    approval_date   DATETIME,
    deleted         BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_admission_major FOREIGN KEY (major_id) REFERENCES majors (id)
);

-- ----------------------------
-- benchmark_scores
-- ----------------------------
CREATE TABLE IF NOT EXISTS benchmark_scores (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    major_id        INT,
    admission_year  INT,
    score           FLOAT,
    CONSTRAINT fk_benchmark_major FOREIGN KEY (major_id) REFERENCES majors (id)
);

-- ----------------------------
-- classrooms
-- ----------------------------
CREATE TABLE IF NOT EXISTS classrooms (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    room_name   VARCHAR(255) NOT NULL,
    capacity    INT,
    room_type   VARCHAR(255),
    deleted     BOOLEAN      NOT NULL DEFAULT FALSE
);

-- ----------------------------
-- courses
-- ----------------------------
CREATE TABLE IF NOT EXISTS courses (
    id                      INT AUTO_INCREMENT PRIMARY KEY,
    course_code             VARCHAR(20)  NOT NULL,
    course_name             VARCHAR(255) NOT NULL,
    credits                 INT,
    faculty_id              INT,
    prerequisite_course_id  INT,
    status                  VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    deleted                 BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_courses_faculty           FOREIGN KEY (faculty_id)             REFERENCES faculties (id),
    CONSTRAINT fk_courses_prerequisite      FOREIGN KEY (prerequisite_course_id) REFERENCES courses (id)
);

-- ----------------------------
-- semesters
-- ----------------------------
CREATE TABLE IF NOT EXISTS semesters (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    semester_number     INT,
    academic_year       VARCHAR(50),
    deleted             BOOLEAN NOT NULL DEFAULT FALSE
);

-- ----------------------------
-- course_sections
-- ----------------------------
CREATE TABLE IF NOT EXISTS course_sections (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    section_code    VARCHAR(50)  NOT NULL UNIQUE,
    course_id       INT,
    lecturer_id     INT,
    semester_id     INT,
    max_capacity    INT,
    status          VARCHAR(20)  NOT NULL DEFAULT 'OPEN',
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted         BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_sections_course      FOREIGN KEY (course_id)   REFERENCES courses (id),
    CONSTRAINT fk_sections_lecturer    FOREIGN KEY (lecturer_id) REFERENCES lecturers (id),
    CONSTRAINT fk_sections_semester    FOREIGN KEY (semester_id) REFERENCES semesters (id)
);

-- ----------------------------
-- recurring_schedules
-- ----------------------------
CREATE TABLE IF NOT EXISTS recurring_schedules (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    section_id      INT,
    room_id         INT,
    day_of_week     INT,
    start_period    INT,
    end_period      INT,
    CONSTRAINT fk_recurring_section    FOREIGN KEY (section_id) REFERENCES course_sections (id),
    CONSTRAINT fk_recurring_room       FOREIGN KEY (room_id)    REFERENCES classrooms (id),
    INDEX idx_recurring_room_day_period (room_id, day_of_week, start_period)
);

-- ----------------------------
-- class_sessions
-- ----------------------------
CREATE TABLE IF NOT EXISTS class_sessions (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    section_id      INT,
    room_id         INT,
    session_date    DATE,
    start_period    INT,
    end_period      INT,
    lesson_content  TEXT,
    status          VARCHAR(20)  NOT NULL DEFAULT 'NORMAL',
    CONSTRAINT fk_sessions_section    FOREIGN KEY (section_id) REFERENCES course_sections (id),
    CONSTRAINT fk_sessions_room       FOREIGN KEY (room_id)    REFERENCES classrooms (id)
);

-- ----------------------------
-- course_registrations
-- ----------------------------
CREATE TABLE IF NOT EXISTS course_registrations (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    student_id          INT,
    section_id          INT,
    registration_time   DATETIME,
    status              VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
    deleted             BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_registrations_student    FOREIGN KEY (student_id) REFERENCES students (id),
    CONSTRAINT fk_registrations_section    FOREIGN KEY (section_id) REFERENCES course_sections (id),
    CONSTRAINT uk_student_section          UNIQUE (student_id, section_id)
);

-- ----------------------------
-- attendance
-- ----------------------------
CREATE TABLE IF NOT EXISTS attendance (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    session_id          INT,
    registration_id     INT,
    attendance_status   VARCHAR(20),
    note                VARCHAR(255),
    deleted             BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_attendance_session        FOREIGN KEY (session_id)      REFERENCES class_sessions (id),
    CONSTRAINT fk_attendance_registration   FOREIGN KEY (registration_id) REFERENCES course_registrations (id),
    CONSTRAINT uk_session_registration      UNIQUE (session_id, registration_id)
);

-- ----------------------------
-- grade_components
-- ----------------------------
CREATE TABLE IF NOT EXISTS grade_components (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    component_name      VARCHAR(50),
    weight_percentage   FLOAT,
    course_id           INT,
    deleted             BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_grade_components_course FOREIGN KEY (course_id) REFERENCES courses (id),
    INDEX idx_grade_components_course_name (course_id, component_name)
);

-- ----------------------------
-- grade_reports
-- ----------------------------
CREATE TABLE IF NOT EXISTS grade_reports (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    registration_id INT UNIQUE,
    final_score     FLOAT,
    letter_grade    VARCHAR(5),
    status          VARCHAR(20)  NOT NULL DEFAULT 'DRAFT',
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted         BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_grade_reports_registration FOREIGN KEY (registration_id) REFERENCES course_registrations (id)
);

-- ----------------------------
-- grade_details
-- ----------------------------
CREATE TABLE IF NOT EXISTS grade_details (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    report_id       INT,
    component_id    INT,
    score           FLOAT,
    deleted         BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_grade_details_report      FOREIGN KEY (report_id)    REFERENCES grade_reports (id),
    CONSTRAINT fk_grade_details_component   FOREIGN KEY (component_id) REFERENCES grade_components (id),
    CONSTRAINT uk_report_component          UNIQUE (report_id, component_id)
);

