-- Comprehensive seed data for local development/testing

-- 1) Access control foundation
INSERT INTO roles (role_name, deleted)
SELECT 'ADMIN', b'0'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'ADMIN');

INSERT INTO roles (role_name, deleted)
SELECT 'LECTURER', b'0'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'LECTURER');

INSERT INTO roles (role_name, deleted)
SELECT 'STUDENT', b'0'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'STUDENT');

INSERT INTO roles (role_name, deleted)
SELECT 'GUARDIAN', b'0'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'GUARDIAN');

INSERT INTO role_permissions (role_id, function_code, deleted)
SELECT r.id, 'SYSTEM_ALL', b'0'
FROM roles r
WHERE r.role_name = 'ADMIN'
  AND NOT EXISTS (
      SELECT 1 FROM role_permissions rp
      WHERE rp.role_id = r.id AND rp.function_code = 'SYSTEM_ALL'
  );

INSERT INTO role_permissions (role_id, function_code, deleted)
SELECT r.id, 'COURSE_SECTION_MANAGE', b'0'
FROM roles r
WHERE r.role_name = 'LECTURER'
  AND NOT EXISTS (
      SELECT 1 FROM role_permissions rp
      WHERE rp.role_id = r.id AND rp.function_code = 'COURSE_SECTION_MANAGE'
  );

INSERT INTO role_permissions (role_id, function_code, deleted)
SELECT r.id, 'COURSE_REGISTRATION', b'0'
FROM roles r
WHERE r.role_name = 'STUDENT'
  AND NOT EXISTS (
      SELECT 1 FROM role_permissions rp
      WHERE rp.role_id = r.id AND rp.function_code = 'COURSE_REGISTRATION'
  );

-- 2) Core catalogs
INSERT INTO faculties (faculty_name, faculty_code, deleted)
SELECT 'Faculty of Information Technology', 'FIT', b'0'
WHERE NOT EXISTS (SELECT 1 FROM faculties WHERE faculty_code = 'FIT');

INSERT INTO faculties (faculty_name, faculty_code, deleted)
SELECT 'Faculty of Business Administration', 'FBA', b'0'
WHERE NOT EXISTS (SELECT 1 FROM faculties WHERE faculty_code = 'FBA');

INSERT INTO majors (faculty_id, major_name, major_code, deleted)
SELECT f.id, 'Computer Science', 'CS', b'0'
FROM faculties f
WHERE f.faculty_code = 'FIT'
  AND NOT EXISTS (SELECT 1 FROM majors WHERE major_code = 'CS');

INSERT INTO majors (faculty_id, major_name, major_code, deleted)
SELECT f.id, 'Information Systems', 'IS', b'0'
FROM faculties f
WHERE f.faculty_code = 'FIT'
  AND NOT EXISTS (SELECT 1 FROM majors WHERE major_code = 'IS');

INSERT INTO majors (faculty_id, major_name, major_code, deleted)
SELECT f.id, 'Business Administration', 'BA', b'0'
FROM faculties f
WHERE f.faculty_code = 'FBA'
  AND NOT EXISTS (SELECT 1 FROM majors WHERE major_code = 'BA');

INSERT INTO specializations (major_id, specialization_name, deleted)
SELECT m.id, 'Software Engineering', b'0'
FROM majors m
WHERE m.major_code = 'CS'
  AND NOT EXISTS (
      SELECT 1 FROM specializations s
      WHERE s.major_id = m.id AND s.specialization_name = 'Software Engineering'
  );

INSERT INTO specializations (major_id, specialization_name, deleted)
SELECT m.id, 'Data Science', b'0'
FROM majors m
WHERE m.major_code = 'CS'
  AND NOT EXISTS (
      SELECT 1 FROM specializations s
      WHERE s.major_id = m.id AND s.specialization_name = 'Data Science'
  );

INSERT INTO specializations (major_id, specialization_name, deleted)
SELECT m.id, 'Enterprise Systems', b'0'
FROM majors m
WHERE m.major_code = 'IS'
  AND NOT EXISTS (
      SELECT 1 FROM specializations s
      WHERE s.major_id = m.id AND s.specialization_name = 'Enterprise Systems'
  );

INSERT INTO cohorts (cohort_name, start_year, end_year, status, deleted)
SELECT 'K2023', 2023, 2027, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM cohorts WHERE cohort_name = 'K2023');

INSERT INTO cohorts (cohort_name, start_year, end_year, status, deleted)
SELECT 'K2024', 2024, 2028, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM cohorts WHERE cohort_name = 'K2024');

INSERT INTO semesters (semester_number, academic_year, start_date, end_date, total_weeks, deleted)
SELECT 1, '2025-2026', '2025-09-01', '2025-12-31', 16, b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM semesters
    WHERE semester_number = 1 AND academic_year = '2025-2026'
);

INSERT INTO semesters (semester_number, academic_year, start_date, end_date, total_weeks, deleted)
SELECT 2, '2025-2026', '2026-01-05', '2026-05-20', 16, b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM semesters
    WHERE semester_number = 2 AND academic_year = '2025-2026'
);

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'A101', 60, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms WHERE room_name = 'A101');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'B201', 45, 'LAB', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms WHERE room_name = 'B201');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C301', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms WHERE room_name = 'C301');

INSERT INTO admission_periods (period_name, start_time, end_time, status, deleted)
SELECT 'Admission 2026 - Round 1', '2026-03-01 00:00:00', '2026-06-30 23:59:59', 1, b'0'
WHERE NOT EXISTS (SELECT 1 FROM admission_periods WHERE period_name = 'Admission 2026 - Round 1');

INSERT INTO admission_blocks (block_name, description, deleted)
SELECT 'A00', 'Math - Physics - Chemistry', 0
WHERE NOT EXISTS (SELECT 1 FROM admission_blocks WHERE block_name = 'A00');

INSERT INTO admission_blocks (block_name, description, deleted)
SELECT 'D01', 'Math - Literature - English', 0
WHERE NOT EXISTS (SELECT 1 FROM admission_blocks WHERE block_name = 'D01');

-- 3) Accounts and profiles
INSERT INTO accounts (role_id, username, password, status, avatar_url, created_at, deleted)
SELECT r.id, 'admin', '$2a$10$exampleAdminHash', 'ACTIVE', NULL, NOW(), b'0'
FROM roles r
WHERE r.role_name = 'ADMIN'
  AND NOT EXISTS (SELECT 1 FROM accounts WHERE username = 'admin');

INSERT INTO accounts (role_id, username, password, status, avatar_url, created_at, deleted)
SELECT r.id, 'lecturer01', '$2a$10$exampleLecturerHash01', 'ACTIVE', NULL, NOW(), b'0'
FROM roles r
WHERE r.role_name = 'LECTURER'
  AND NOT EXISTS (SELECT 1 FROM accounts WHERE username = 'lecturer01');

INSERT INTO accounts (role_id, username, password, status, avatar_url, created_at, deleted)
SELECT r.id, 'lecturer02', '$2a$10$exampleLecturerHash02', 'ACTIVE', NULL, NOW(), b'0'
FROM roles r
WHERE r.role_name = 'LECTURER'
  AND NOT EXISTS (SELECT 1 FROM accounts WHERE username = 'lecturer02');

INSERT INTO accounts (role_id, username, password, status, avatar_url, created_at, deleted)
SELECT r.id, 'guardian01', '$2a$10$exampleGuardianHash01', 'ACTIVE', NULL, NOW(), b'0'
FROM roles r
WHERE r.role_name = 'GUARDIAN'
  AND NOT EXISTS (SELECT 1 FROM accounts WHERE username = 'guardian01');

INSERT INTO accounts (role_id, username, password, status, avatar_url, created_at, deleted)
SELECT r.id, 'guardian02', '$2a$10$exampleGuardianHash02', 'ACTIVE', NULL, NOW(), b'0'
FROM roles r
WHERE r.role_name = 'GUARDIAN'
  AND NOT EXISTS (SELECT 1 FROM accounts WHERE username = 'guardian02');

INSERT INTO accounts (role_id, username, password, status, avatar_url, created_at, deleted)
SELECT r.id, 'student01', '$2a$10$exampleStudentHash01', 'ACTIVE', NULL, NOW(), b'0'
FROM roles r
WHERE r.role_name = 'STUDENT'
  AND NOT EXISTS (SELECT 1 FROM accounts WHERE username = 'student01');

INSERT INTO accounts (role_id, username, password, status, avatar_url, created_at, deleted)
SELECT r.id, 'student02', '$2a$10$exampleStudentHash02', 'ACTIVE', NULL, NOW(), b'0'
FROM roles r
WHERE r.role_name = 'STUDENT'
  AND NOT EXISTS (SELECT 1 FROM accounts WHERE username = 'student02');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT a.id, 'Nguyen Van An', 'PhD', 'an.nguyen@edums.edu', '0901000001', b'0'
FROM accounts a
WHERE a.username = 'lecturer01'
  AND NOT EXISTS (SELECT 1 FROM lecturers l WHERE l.account_id = a.id);

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT a.id, 'Tran Thi Binh', 'MSc', 'binh.tran@edums.edu', '0901000002', b'0'
FROM accounts a
WHERE a.username = 'lecturer02'
  AND NOT EXISTS (SELECT 1 FROM lecturers l WHERE l.account_id = a.id);

INSERT INTO guardians (account_id, full_name, phone, relationship, deleted)
SELECT a.id, 'Le Van Parent', '0912000001', 'Father', b'0'
FROM accounts a
WHERE a.username = 'guardian01'
  AND NOT EXISTS (SELECT 1 FROM guardians g WHERE g.account_id = a.id);

INSERT INTO guardians (account_id, full_name, phone, relationship, deleted)
SELECT a.id, 'Pham Thi Parent', '0912000002', 'Mother', b'0'
FROM accounts a
WHERE a.username = 'guardian02'
  AND NOT EXISTS (SELECT 1 FROM guardians g WHERE g.account_id = a.id);

INSERT INTO administrative_classes (class_name, head_lecturer_id, cohort_id, major_id, max_capacity, deleted)
SELECT 'SE1701',
       (SELECT l.id FROM lecturers l WHERE l.email = 'an.nguyen@edums.edu' LIMIT 1),
       (SELECT c.id FROM cohorts c WHERE c.cohort_name = 'K2023' LIMIT 1),
       (SELECT m.id FROM majors m WHERE m.major_code = 'CS' LIMIT 1),
       40,
       b'0'
WHERE NOT EXISTS (SELECT 1 FROM administrative_classes ac WHERE ac.class_name = 'SE1701');

INSERT INTO administrative_classes (class_name, head_lecturer_id, cohort_id, major_id, max_capacity, deleted)
SELECT 'IS1701',
       (SELECT l.id FROM lecturers l WHERE l.email = 'binh.tran@edums.edu' LIMIT 1),
       (SELECT c.id FROM cohorts c WHERE c.cohort_name = 'K2023' LIMIT 1),
       (SELECT m.id FROM majors m WHERE m.major_code = 'IS' LIMIT 1),
       35,
       b'0'
WHERE NOT EXISTS (SELECT 1 FROM administrative_classes ac WHERE ac.class_name = 'IS1701');

INSERT INTO students (account_id, class_id, major_id, specialization_id, guardian_id, student_code, full_name, email, address,
                      date_of_birth, gender, phone, national_id, ethnicity, religion, place_of_birth, nationality,
                      status, created_at, deleted)
SELECT (SELECT a.id FROM accounts a WHERE a.username = 'student01' LIMIT 1),
       (SELECT ac.id FROM administrative_classes ac WHERE ac.class_name = 'SE1701' LIMIT 1),
       (SELECT m.id FROM majors m WHERE m.major_code = 'CS' LIMIT 1),
       (SELECT s.id FROM specializations s WHERE s.specialization_name = 'Software Engineering' LIMIT 1),
       (SELECT g.id FROM guardians g WHERE g.phone = '0912000001' LIMIT 1),
       'SE170001',
       'Do Minh Khoa',
       'khoa.do@sv.edums.edu',
       'Ha Noi',
       '2005-02-14',
       b'1',
       '0933000001',
       '012345678901',
       'Kinh',
       'None',
       'Ha Noi',
       'Viet Nam',
       'ACTIVE',
       NOW(),
       b'0'
WHERE NOT EXISTS (SELECT 1 FROM students st WHERE st.student_code = 'SE170001');

INSERT INTO students (account_id, class_id, major_id, specialization_id, guardian_id, student_code, full_name, email, address,
                      date_of_birth, gender, phone, national_id, ethnicity, religion, place_of_birth, nationality,
                      status, created_at, deleted)
SELECT (SELECT a.id FROM accounts a WHERE a.username = 'student02' LIMIT 1),
       (SELECT ac.id FROM administrative_classes ac WHERE ac.class_name = 'IS1701' LIMIT 1),
       (SELECT m.id FROM majors m WHERE m.major_code = 'IS' LIMIT 1),
       (SELECT s.id FROM specializations s WHERE s.specialization_name = 'Enterprise Systems' LIMIT 1),
       (SELECT g.id FROM guardians g WHERE g.phone = '0912000002' LIMIT 1),
       'IS170001',
       'Pham Gia Huy',
       'huy.pham@sv.edums.edu',
       'Da Nang',
       '2005-06-10',
       b'1',
       '0933000002',
       '012345678902',
       'Kinh',
       'None',
       'Da Nang',
       'Viet Nam',
       'ACTIVE',
       NOW(),
       b'0'
WHERE NOT EXISTS (SELECT 1 FROM students st WHERE st.student_code = 'IS170001');

-- 4) Teaching and registration data
INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT 'CS101', 'Introduction to Programming', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL,
       'ACTIVE',
       b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = 'CS101');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT 'CS102', 'Object Oriented Programming', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = 'CS101' LIMIT 1),
       'ACTIVE',
       b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = 'CS102');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT 'IS201', 'Database Systems', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL,
       'ACTIVE',
       b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = 'IS201');

INSERT INTO grade_components (component_name, weight_percentage, course_id, deleted)
SELECT 'Attendance', 10,
       (SELECT c.id FROM courses c WHERE c.course_code = 'CS101' LIMIT 1),
       b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM grade_components gc
    WHERE gc.component_name = 'Attendance' AND gc.course_id = (SELECT c.id FROM courses c WHERE c.course_code = 'CS101' LIMIT 1)
);

INSERT INTO grade_components (component_name, weight_percentage, course_id, deleted)
SELECT 'Midterm', 30,
       (SELECT c.id FROM courses c WHERE c.course_code = 'CS101' LIMIT 1),
       b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM grade_components gc
    WHERE gc.component_name = 'Midterm' AND gc.course_id = (SELECT c.id FROM courses c WHERE c.course_code = 'CS101' LIMIT 1)
);

INSERT INTO grade_components (component_name, weight_percentage, course_id, deleted)
SELECT 'Final', 60,
       (SELECT c.id FROM courses c WHERE c.course_code = 'CS101' LIMIT 1),
       b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM grade_components gc
    WHERE gc.component_name = 'Final' AND gc.course_id = (SELECT c.id FROM courses c WHERE c.course_code = 'CS101' LIMIT 1)
);

INSERT INTO grade_components (component_name, weight_percentage, course_id, deleted)
SELECT 'Final', 100,
       (SELECT c.id FROM courses c WHERE c.course_code = 'IS201' LIMIT 1),
       b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM grade_components gc
    WHERE gc.component_name = 'Final' AND gc.course_id = (SELECT c.id FROM courses c WHERE c.course_code = 'IS201' LIMIT 1)
);

INSERT INTO registration_periods (semester_id, name, start_time, end_time, status, created_at, deleted)
SELECT s.id,
       'Main Registration Window - Semester 1',
       '2025-08-15 00:00:00',
       '2025-09-15 23:59:59',
       'OPEN',
       NOW(),
       b'0'
FROM semesters s
WHERE s.semester_number = 1
  AND s.academic_year = '2025-2026'
  AND NOT EXISTS (
      SELECT 1 FROM registration_periods rp
      WHERE rp.semester_id = s.id AND rp.name = 'Main Registration Window - Semester 1'
  );

INSERT INTO registration_periods (semester_id, name, start_time, end_time, status, created_at, deleted)
SELECT s.id,
       'Late Registration - Semester 1',
       '2025-09-16 00:00:00',
       '2025-09-25 23:59:59',
       'CLOSED',
       NOW(),
       b'0'
FROM semesters s
WHERE s.semester_number = 1
  AND s.academic_year = '2025-2026'
  AND NOT EXISTS (
      SELECT 1 FROM registration_periods rp
      WHERE rp.semester_id = s.id AND rp.name = 'Late Registration - Semester 1'
  );

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT 'CS101-01', 'CS101 - Morning Class',
       (SELECT c.id FROM courses c WHERE c.course_code = 'CS101' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = 'an.nguyen@edums.edu' LIMIT 1),
       (SELECT s.id FROM semesters s WHERE s.semester_number = 1 AND s.academic_year = '2025-2026' LIMIT 1),
       50,
       'OPEN',
       NOW(),
       b'0'
WHERE NOT EXISTS (
    SELECT 1
    FROM course_sections cs
    WHERE cs.section_code = 'CS101-01'
      AND cs.semester_id = (SELECT s.id FROM semesters s WHERE s.semester_number = 1 AND s.academic_year = '2025-2026' LIMIT 1)
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT 'CS102-01', 'CS102 - Afternoon Class',
       (SELECT c.id FROM courses c WHERE c.course_code = 'CS102' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = 'an.nguyen@edums.edu' LIMIT 1),
       (SELECT s.id FROM semesters s WHERE s.semester_number = 1 AND s.academic_year = '2025-2026' LIMIT 1),
       45,
       'OPEN',
       NOW(),
       b'0'
WHERE NOT EXISTS (
    SELECT 1
    FROM course_sections cs
    WHERE cs.section_code = 'CS102-01'
      AND cs.semester_id = (SELECT s.id FROM semesters s WHERE s.semester_number = 1 AND s.academic_year = '2025-2026' LIMIT 1)
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT 'IS201-01', 'IS201 - Evening Class',
       (SELECT c.id FROM courses c WHERE c.course_code = 'IS201' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = 'binh.tran@edums.edu' LIMIT 1),
       (SELECT s.id FROM semesters s WHERE s.semester_number = 1 AND s.academic_year = '2025-2026' LIMIT 1),
       55,
       'OPEN',
       NOW(),
       b'0'
WHERE NOT EXISTS (
    SELECT 1
    FROM course_sections cs
    WHERE cs.section_code = 'IS201-01'
      AND cs.semester_id = (SELECT s.id FROM semesters s WHERE s.semester_number = 1 AND s.academic_year = '2025-2026' LIMIT 1)
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT (SELECT cs.id FROM course_sections cs
        WHERE cs.section_code = 'CS101-01'
          AND cs.semester_id = (SELECT s.id FROM semesters s WHERE s.semester_number = 1 AND s.academic_year = '2025-2026' LIMIT 1)
        LIMIT 1),
       (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'A101' LIMIT 1),
       2,
       1,
       3,
       1,
       15,
       b'0',
       NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs
                           WHERE cs.section_code = 'CS101-01'
                             AND cs.semester_id = (SELECT s.id FROM semesters s WHERE s.semester_number = 1 AND s.academic_year = '2025-2026' LIMIT 1)
                           LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 1
      AND rs.end_period = 3
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT (SELECT cs.id FROM course_sections cs
        WHERE cs.section_code = 'CS102-01'
          AND cs.semester_id = (SELECT s.id FROM semesters s WHERE s.semester_number = 1 AND s.academic_year = '2025-2026' LIMIT 1)
        LIMIT 1),
       (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'B201' LIMIT 1),
       4,
       4,
       6,
       1,
       15,
       b'0',
       NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs
                           WHERE cs.section_code = 'CS102-01'
                             AND cs.semester_id = (SELECT s.id FROM semesters s WHERE s.semester_number = 1 AND s.academic_year = '2025-2026' LIMIT 1)
                           LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 4
      AND rs.end_period = 6
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT (SELECT cs.id FROM course_sections cs
        WHERE cs.section_code = 'IS201-01'
          AND cs.semester_id = (SELECT s.id FROM semesters s WHERE s.semester_number = 1 AND s.academic_year = '2025-2026' LIMIT 1)
        LIMIT 1),
       (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C301' LIMIT 1),
       6,
       7,
       9,
       1,
       15,
       b'0',
       NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs
                           WHERE cs.section_code = 'IS201-01'
                             AND cs.semester_id = (SELECT s.id FROM semesters s WHERE s.semester_number = 1 AND s.academic_year = '2025-2026' LIMIT 1)
                           LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 7
      AND rs.end_period = 9
);

INSERT INTO class_sessions (section_id, room_id, recurring_schedule_id, session_date, start_period, end_period, lesson_content, status, deleted)
SELECT rs.section_id, rs.room_id, rs.id, '2025-09-08', rs.start_period, rs.end_period, 'Variables and data types', 'COMPLETED', b'0'
FROM recurring_schedules rs
JOIN course_sections cs ON cs.id = rs.section_id
JOIN semesters s ON s.id = cs.semester_id
WHERE cs.section_code = 'CS101-01'
  AND s.semester_number = 1
  AND s.academic_year = '2025-2026'
  AND NOT EXISTS (
      SELECT 1 FROM class_sessions ss
      WHERE ss.recurring_schedule_id = rs.id AND ss.session_date = '2025-09-08'
  );

INSERT INTO class_sessions (section_id, room_id, recurring_schedule_id, session_date, start_period, end_period, lesson_content, status, deleted)
SELECT rs.section_id, rs.room_id, rs.id, '2025-09-10', rs.start_period, rs.end_period, 'Class and object basics', 'COMPLETED', b'0'
FROM recurring_schedules rs
JOIN course_sections cs ON cs.id = rs.section_id
JOIN semesters s ON s.id = cs.semester_id
WHERE cs.section_code = 'CS102-01'
  AND s.semester_number = 1
  AND s.academic_year = '2025-2026'
  AND NOT EXISTS (
      SELECT 1 FROM class_sessions ss
      WHERE ss.recurring_schedule_id = rs.id AND ss.session_date = '2025-09-10'
  );

INSERT INTO benchmark_scores (major_id, block_id, period_id, score, deleted)
SELECT (SELECT m.id FROM majors m WHERE m.major_code = 'CS' LIMIT 1),
       (SELECT b.id FROM admission_blocks b WHERE b.block_name = 'A00' LIMIT 1),
       (SELECT p.id FROM admission_periods p WHERE p.period_name = 'Admission 2026 - Round 1' LIMIT 1),
       24.5,
       b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM benchmark_scores bs
    WHERE bs.major_id = (SELECT m.id FROM majors m WHERE m.major_code = 'CS' LIMIT 1)
      AND bs.block_id = (SELECT b.id FROM admission_blocks b WHERE b.block_name = 'A00' LIMIT 1)
      AND bs.period_id = (SELECT p.id FROM admission_periods p WHERE p.period_name = 'Admission 2026 - Round 1' LIMIT 1)
);

INSERT INTO benchmark_scores (major_id, block_id, period_id, score, deleted)
SELECT (SELECT m.id FROM majors m WHERE m.major_code = 'IS' LIMIT 1),
       (SELECT b.id FROM admission_blocks b WHERE b.block_name = 'D01' LIMIT 1),
       (SELECT p.id FROM admission_periods p WHERE p.period_name = 'Admission 2026 - Round 1' LIMIT 1),
       22.0,
       b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM benchmark_scores bs
    WHERE bs.major_id = (SELECT m.id FROM majors m WHERE m.major_code = 'IS' LIMIT 1)
      AND bs.block_id = (SELECT b.id FROM admission_blocks b WHERE b.block_name = 'D01' LIMIT 1)
      AND bs.period_id = (SELECT p.id FROM admission_periods p WHERE p.period_name = 'Admission 2026 - Round 1' LIMIT 1)
);

INSERT INTO admission_applications (period_id, full_name, date_of_birth, email, phone, national_id, address, major_id,
                                    total_score, block_id, status, approval_date, deleted)
SELECT (SELECT p.id FROM admission_periods p WHERE p.period_name = 'Admission 2026 - Round 1' LIMIT 1),
       'Candidate One',
       '2007-03-20',
       'candidate.one@example.com',
       '0988000001',
       '099999999901',
       'Hai Phong',
       (SELECT m.id FROM majors m WHERE m.major_code = 'CS' LIMIT 1),
       25.5,
       (SELECT b.id FROM admission_blocks b WHERE b.block_name = 'A00' LIMIT 1),
       'APPROVED',
       NOW(),
       b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM admission_applications aa WHERE aa.national_id = '099999999901'
);

INSERT INTO admission_applications (period_id, full_name, date_of_birth, email, phone, national_id, address, major_id,
                                    total_score, block_id, status, approval_date, deleted)
SELECT (SELECT p.id FROM admission_periods p WHERE p.period_name = 'Admission 2026 - Round 1' LIMIT 1),
       'Candidate Two',
       '2007-07-15',
       'candidate.two@example.com',
       '0988000002',
       '099999999902',
       'Nghe An',
       (SELECT m.id FROM majors m WHERE m.major_code = 'IS' LIMIT 1),
       23.0,
       (SELECT b.id FROM admission_blocks b WHERE b.block_name = 'D01' LIMIT 1),
       'PENDING',
       NULL,
       b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM admission_applications aa WHERE aa.national_id = '099999999902'
);

INSERT INTO course_registrations (student_id, section_id, registration_time, status, deleted, registration_period_id)
SELECT (SELECT st.id FROM students st WHERE st.student_code = 'SE170001' LIMIT 1),
       (SELECT cs.id FROM course_sections cs
        WHERE cs.section_code = 'CS101-01'
          AND cs.semester_id = (SELECT s.id FROM semesters s WHERE s.semester_number = 1 AND s.academic_year = '2025-2026' LIMIT 1)
        LIMIT 1),
       NOW(),
       'CONFIRMED',
       b'0',
       (SELECT rp.id FROM registration_periods rp WHERE rp.name = 'Main Registration Window - Semester 1' LIMIT 1)
WHERE NOT EXISTS (
    SELECT 1 FROM course_registrations cr
    WHERE cr.student_id = (SELECT st.id FROM students st WHERE st.student_code = 'SE170001' LIMIT 1)
      AND cr.section_id = (SELECT cs.id FROM course_sections cs
                           WHERE cs.section_code = 'CS101-01'
                             AND cs.semester_id = (SELECT s.id FROM semesters s WHERE s.semester_number = 1 AND s.academic_year = '2025-2026' LIMIT 1)
                           LIMIT 1)
);

INSERT INTO course_registrations (student_id, section_id, registration_time, status, deleted, registration_period_id)
SELECT (SELECT st.id FROM students st WHERE st.student_code = 'IS170001' LIMIT 1),
       (SELECT cs.id FROM course_sections cs
        WHERE cs.section_code = 'IS201-01'
          AND cs.semester_id = (SELECT s.id FROM semesters s WHERE s.semester_number = 1 AND s.academic_year = '2025-2026' LIMIT 1)
        LIMIT 1),
       NOW(),
       'CONFIRMED',
       b'0',
       (SELECT rp.id FROM registration_periods rp WHERE rp.name = 'Main Registration Window - Semester 1' LIMIT 1)
WHERE NOT EXISTS (
    SELECT 1 FROM course_registrations cr
    WHERE cr.student_id = (SELECT st.id FROM students st WHERE st.student_code = 'IS170001' LIMIT 1)
      AND cr.section_id = (SELECT cs.id FROM course_sections cs
                           WHERE cs.section_code = 'IS201-01'
                             AND cs.semester_id = (SELECT s.id FROM semesters s WHERE s.semester_number = 1 AND s.academic_year = '2025-2026' LIMIT 1)
                           LIMIT 1)
);

INSERT INTO grade_reports (registration_id, final_score, letter_grade, status, created_at, deleted)
SELECT (SELECT cr.id FROM course_registrations cr
        JOIN students st ON st.id = cr.student_id
        JOIN course_sections cs ON cs.id = cr.section_id
        WHERE st.student_code = 'SE170001' AND cs.section_code = 'CS101-01'
        LIMIT 1),
       8.2,
       'B+',
       'PUBLISHED',
       NOW(),
       b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM grade_reports gr
    WHERE gr.registration_id = (
        SELECT cr.id FROM course_registrations cr
        JOIN students st ON st.id = cr.student_id
        JOIN course_sections cs ON cs.id = cr.section_id
        WHERE st.student_code = 'SE170001' AND cs.section_code = 'CS101-01'
        LIMIT 1
    )
);

INSERT INTO grade_details (report_id, component_id, score, deleted)
SELECT (SELECT gr.id FROM grade_reports gr
        JOIN course_registrations cr ON gr.registration_id = cr.id
        JOIN students st ON st.id = cr.student_id
        JOIN course_sections cs ON cs.id = cr.section_id
        WHERE st.student_code = 'SE170001' AND cs.section_code = 'CS101-01'
        LIMIT 1),
       (SELECT gc.id FROM grade_components gc
        JOIN courses c ON c.id = gc.course_id
        WHERE c.course_code = 'CS101' AND gc.component_name = 'Attendance'
        LIMIT 1),
       9.0,
       b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM grade_details gd
    WHERE gd.report_id = (SELECT gr.id FROM grade_reports gr
                          JOIN course_registrations cr ON gr.registration_id = cr.id
                          JOIN students st ON st.id = cr.student_id
                          JOIN course_sections cs ON cs.id = cr.section_id
                          WHERE st.student_code = 'SE170001' AND cs.section_code = 'CS101-01'
                          LIMIT 1)
      AND gd.component_id = (SELECT gc.id FROM grade_components gc
                             JOIN courses c ON c.id = gc.course_id
                             WHERE c.course_code = 'CS101' AND gc.component_name = 'Attendance'
                             LIMIT 1)
);

INSERT INTO grade_details (report_id, component_id, score, deleted)
SELECT (SELECT gr.id FROM grade_reports gr
        JOIN course_registrations cr ON gr.registration_id = cr.id
        JOIN students st ON st.id = cr.student_id
        JOIN course_sections cs ON cs.id = cr.section_id
        WHERE st.student_code = 'SE170001' AND cs.section_code = 'CS101-01'
        LIMIT 1),
       (SELECT gc.id FROM grade_components gc
        JOIN courses c ON c.id = gc.course_id
        WHERE c.course_code = 'CS101' AND gc.component_name = 'Midterm'
        LIMIT 1),
       8.0,
       b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM grade_details gd
    WHERE gd.report_id = (SELECT gr.id FROM grade_reports gr
                          JOIN course_registrations cr ON gr.registration_id = cr.id
                          JOIN students st ON st.id = cr.student_id
                          JOIN course_sections cs ON cs.id = cr.section_id
                          WHERE st.student_code = 'SE170001' AND cs.section_code = 'CS101-01'
                          LIMIT 1)
      AND gd.component_id = (SELECT gc.id FROM grade_components gc
                             JOIN courses c ON c.id = gc.course_id
                             WHERE c.course_code = 'CS101' AND gc.component_name = 'Midterm'
                             LIMIT 1)
);

INSERT INTO grade_details (report_id, component_id, score, deleted)
SELECT (SELECT gr.id FROM grade_reports gr
        JOIN course_registrations cr ON gr.registration_id = cr.id
        JOIN students st ON st.id = cr.student_id
        JOIN course_sections cs ON cs.id = cr.section_id
        WHERE st.student_code = 'SE170001' AND cs.section_code = 'CS101-01'
        LIMIT 1),
       (SELECT gc.id FROM grade_components gc
        JOIN courses c ON c.id = gc.course_id
        WHERE c.course_code = 'CS101' AND gc.component_name = 'Final'
        LIMIT 1),
       8.1,
       b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM grade_details gd
    WHERE gd.report_id = (SELECT gr.id FROM grade_reports gr
                          JOIN course_registrations cr ON gr.registration_id = cr.id
                          JOIN students st ON st.id = cr.student_id
                          JOIN course_sections cs ON cs.id = cr.section_id
                          WHERE st.student_code = 'SE170001' AND cs.section_code = 'CS101-01'
                          LIMIT 1)
      AND gd.component_id = (SELECT gc.id FROM grade_components gc
                             JOIN courses c ON c.id = gc.course_id
                             WHERE c.course_code = 'CS101' AND gc.component_name = 'Final'
                             LIMIT 1)
);

INSERT INTO attendance (session_id, registration_id, attendance_status, note, deleted)
SELECT (SELECT ss.id FROM class_sessions ss
        JOIN course_sections cs ON cs.id = ss.section_id
        WHERE cs.section_code = 'CS101-01' AND ss.session_date = '2025-09-08'
        LIMIT 1),
       (SELECT cr.id FROM course_registrations cr
        JOIN students st ON st.id = cr.student_id
        JOIN course_sections cs ON cs.id = cr.section_id
        WHERE st.student_code = 'SE170001' AND cs.section_code = 'CS101-01'
        LIMIT 1),
       'PRESENT',
       'On time',
       b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM attendance a
    WHERE a.session_id = (SELECT ss.id FROM class_sessions ss
                          JOIN course_sections cs ON cs.id = ss.section_id
                          WHERE cs.section_code = 'CS101-01' AND ss.session_date = '2025-09-08'
                          LIMIT 1)
      AND a.registration_id = (SELECT cr.id FROM course_registrations cr
                               JOIN students st ON st.id = cr.student_id
                               JOIN course_sections cs ON cs.id = cr.section_id
                               WHERE st.student_code = 'SE170001' AND cs.section_code = 'CS101-01'
                               LIMIT 1)
);

