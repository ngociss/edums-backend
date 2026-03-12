-- ============================================================
-- V4__comprehensive_seed_data.sql
-- EduMS - Comprehensive Seed Data (All Tables)
-- Sử dụng subquery để tránh phụ thuộc hardcode ID
-- ============================================================

-- ----------------------------
-- accounts
-- password hash = 'password123'
-- ----------------------------
INSERT INTO accounts (role_id, username, password, status, avatar_url, created_at, deleted) VALUES
((SELECT id FROM roles WHERE role_name='ADMIN'),    'admin',         '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPVzTTfXrpG', 'ACTIVE', NULL, NOW(), FALSE),
((SELECT id FROM roles WHERE role_name='LECTURER'), 'gv.nguyenvana', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPVzTTfXrpG', 'ACTIVE', NULL, NOW(), FALSE),
((SELECT id FROM roles WHERE role_name='LECTURER'), 'gv.tranthib',   '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPVzTTfXrpG', 'ACTIVE', NULL, NOW(), FALSE),
((SELECT id FROM roles WHERE role_name='LECTURER'), 'gv.levanc',     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPVzTTfXrpG', 'ACTIVE', NULL, NOW(), FALSE),
((SELECT id FROM roles WHERE role_name='LECTURER'), 'gv.phamthid',   '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPVzTTfXrpG', 'ACTIVE', NULL, NOW(), FALSE),
((SELECT id FROM roles WHERE role_name='STUDENT'),  'sv.2200001',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPVzTTfXrpG', 'ACTIVE', NULL, NOW(), FALSE),
((SELECT id FROM roles WHERE role_name='STUDENT'),  'sv.2200002',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPVzTTfXrpG', 'ACTIVE', NULL, NOW(), FALSE),
((SELECT id FROM roles WHERE role_name='STUDENT'),  'sv.2200003',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPVzTTfXrpG', 'ACTIVE', NULL, NOW(), FALSE),
((SELECT id FROM roles WHERE role_name='STUDENT'),  'sv.2200004',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPVzTTfXrpG', 'ACTIVE', NULL, NOW(), FALSE),
((SELECT id FROM roles WHERE role_name='STUDENT'),  'sv.2300001',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPVzTTfXrpG', 'ACTIVE', NULL, NOW(), FALSE),
((SELECT id FROM roles WHERE role_name='STUDENT'),  'sv.2300002',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPVzTTfXrpG', 'ACTIVE', NULL, NOW(), FALSE),
((SELECT id FROM roles WHERE role_name='GUARDIAN'), 'ph.nguyen01',   '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPVzTTfXrpG', 'ACTIVE', NULL, NOW(), FALSE),
((SELECT id FROM roles WHERE role_name='GUARDIAN'), 'ph.tran02',     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPVzTTfXrpG', 'ACTIVE', NULL, NOW(), FALSE),
((SELECT id FROM roles WHERE role_name='GUARDIAN'), 'ph.le03',       '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPVzTTfXrpG', 'ACTIVE', NULL, NOW(), FALSE),
((SELECT id FROM roles WHERE role_name='GUARDIAN'), 'ph.pham04',     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPVzTTfXrpG', 'ACTIVE', NULL, NOW(), FALSE),
((SELECT id FROM roles WHERE role_name='GUARDIAN'), 'ph.hoang05',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPVzTTfXrpG', 'ACTIVE', NULL, NOW(), FALSE),
((SELECT id FROM roles WHERE role_name='GUARDIAN'), 'ph.do06',       '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPVzTTfXrpG', 'ACTIVE', NULL, NOW(), FALSE);

-- ----------------------------
-- role_permissions
-- ----------------------------
INSERT INTO role_permissions (role_id, function_code, deleted) VALUES
((SELECT id FROM roles WHERE role_name='ADMIN'), 'MANAGE_ROLES',           FALSE),
((SELECT id FROM roles WHERE role_name='ADMIN'), 'MANAGE_ACCOUNTS',        FALSE),
((SELECT id FROM roles WHERE role_name='ADMIN'), 'MANAGE_FACULTIES',       FALSE),
((SELECT id FROM roles WHERE role_name='ADMIN'), 'MANAGE_MAJORS',          FALSE),
((SELECT id FROM roles WHERE role_name='ADMIN'), 'MANAGE_SPECIALIZATIONS', FALSE),
((SELECT id FROM roles WHERE role_name='ADMIN'), 'MANAGE_COHORTS',         FALSE),
((SELECT id FROM roles WHERE role_name='ADMIN'), 'MANAGE_CLASSROOMS',      FALSE),
((SELECT id FROM roles WHERE role_name='ADMIN'), 'MANAGE_LECTURERS',       FALSE),
((SELECT id FROM roles WHERE role_name='ADMIN'), 'MANAGE_STUDENTS',        FALSE),
((SELECT id FROM roles WHERE role_name='ADMIN'), 'MANAGE_COURSES',         FALSE),
((SELECT id FROM roles WHERE role_name='ADMIN'), 'MANAGE_SECTIONS',        FALSE),
((SELECT id FROM roles WHERE role_name='ADMIN'), 'MANAGE_SEMESTERS',       FALSE),
((SELECT id FROM roles WHERE role_name='ADMIN'), 'VIEW_REPORTS',           FALSE),
((SELECT id FROM roles WHERE role_name='LECTURER'), 'VIEW_SECTIONS',     FALSE),
((SELECT id FROM roles WHERE role_name='LECTURER'), 'MANAGE_ATTENDANCE', FALSE),
((SELECT id FROM roles WHERE role_name='LECTURER'), 'MANAGE_GRADES',     FALSE),
((SELECT id FROM roles WHERE role_name='LECTURER'), 'VIEW_STUDENTS',     FALSE),
((SELECT id FROM roles WHERE role_name='STUDENT'), 'VIEW_COURSES',      FALSE),
((SELECT id FROM roles WHERE role_name='STUDENT'), 'REGISTER_COURSES',  FALSE),
((SELECT id FROM roles WHERE role_name='STUDENT'), 'VIEW_GRADES',       FALSE),
((SELECT id FROM roles WHERE role_name='STUDENT'), 'VIEW_ATTENDANCE',   FALSE),
((SELECT id FROM roles WHERE role_name='GUARDIAN'), 'VIEW_STUDENT_INFO', FALSE),
((SELECT id FROM roles WHERE role_name='GUARDIAN'), 'VIEW_GRADES',       FALSE),
((SELECT id FROM roles WHERE role_name='GUARDIAN'), 'VIEW_ATTENDANCE',   FALSE);

-- ----------------------------
-- lecturers
-- ----------------------------
INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted) VALUES
((SELECT id FROM accounts WHERE username='gv.nguyenvana'), 'Nguyen Van An',  'PGS.TS', 'nguyenvana@edu.vn', '0961234567', FALSE),
((SELECT id FROM accounts WHERE username='gv.tranthib'),   'Tran Thi Binh',  'TS',     'tranthib@edu.vn',   '0972345678', FALSE),
((SELECT id FROM accounts WHERE username='gv.levanc'),     'Le Van Cuong',   'ThS',    'levanc@edu.vn',     '0983456789', FALSE),
((SELECT id FROM accounts WHERE username='gv.phamthid'),   'Pham Thi Dung',  'GS.TS',  'phamthid@edu.vn',   '0994567890', FALSE);

-- ----------------------------
-- guardians
-- ----------------------------
INSERT INTO guardians (account_id, full_name, phone, relationship, deleted) VALUES
((SELECT id FROM accounts WHERE username='ph.nguyen01'), 'Nguyen Van Hung',  '0901234567', 'Father', FALSE),
((SELECT id FROM accounts WHERE username='ph.tran02'),   'Tran Thi Hoa',     '0912345678', 'Mother', FALSE),
((SELECT id FROM accounts WHERE username='ph.le03'),     'Le Van Dung',      '0923456789', 'Father', FALSE),
((SELECT id FROM accounts WHERE username='ph.pham04'),   'Pham Thi Lan',     '0934567890', 'Mother', FALSE),
((SELECT id FROM accounts WHERE username='ph.hoang05'),  'Hoang Van Minh',   '0945678901', 'Father', FALSE),
((SELECT id FROM accounts WHERE username='ph.do06'),     'Do Thi Huong',     '0956789012', 'Mother', FALSE);

-- ----------------------------
-- administrative_classes
-- ----------------------------
INSERT INTO administrative_classes (class_name, head_lecturer_id, cohort_id, major_id, max_capacity, deleted) VALUES
('KTPM22A', (SELECT id FROM lecturers WHERE email='nguyenvana@edu.vn'), (SELECT id FROM cohorts WHERE cohort_name='K2022'), (SELECT id FROM majors WHERE major_code='KTPM'), 40, FALSE),
('KTPM22B', (SELECT id FROM lecturers WHERE email='tranthib@edu.vn'),   (SELECT id FROM cohorts WHERE cohort_name='K2022'), (SELECT id FROM majors WHERE major_code='KTPM'), 40, FALSE),
('HTTT22A', (SELECT id FROM lecturers WHERE email='levanc@edu.vn'),     (SELECT id FROM cohorts WHERE cohort_name='K2022'), (SELECT id FROM majors WHERE major_code='HTTT'), 40, FALSE),
('KTPM23A', (SELECT id FROM lecturers WHERE email='nguyenvana@edu.vn'), (SELECT id FROM cohorts WHERE cohort_name='K2023'), (SELECT id FROM majors WHERE major_code='KTPM'), 40, FALSE),
('HTTT23A', (SELECT id FROM lecturers WHERE email='tranthib@edu.vn'),   (SELECT id FROM cohorts WHERE cohort_name='K2023'), (SELECT id FROM majors WHERE major_code='HTTT'), 40, FALSE);

-- ----------------------------
-- students
-- ----------------------------
INSERT INTO students (
    account_id, class_id, specialization_id, guardian_id,
    student_code, full_name, email, address,
    date_of_birth, gender, phone, national_id,
    ethnicity, religion, place_of_birth, nationality,
    status, created_at, deleted
) VALUES
(
    (SELECT id FROM accounts WHERE username='sv.2200001'),
    (SELECT id FROM administrative_classes WHERE class_name='KTPM22A'),
    (SELECT s.id FROM specializations s JOIN majors m ON s.major_id=m.id WHERE m.major_code='KTPM' ORDER BY s.id LIMIT 1),
    (SELECT id FROM guardians WHERE phone='0901234567'),
    '2200001', 'Nguyen Minh Tuan', 'tuannm22@student.edu.vn', '12 Le Loi, Q1, TP.HCM',
    '2004-03-15', TRUE, '0901111111', '079204012345', 'Kinh', 'Khong', 'TP.HCM', 'Viet Nam', 'ACTIVE', NOW(), FALSE
),
(
    (SELECT id FROM accounts WHERE username='sv.2200002'),
    (SELECT id FROM administrative_classes WHERE class_name='KTPM22A'),
    (SELECT s.id FROM specializations s JOIN majors m ON s.major_id=m.id WHERE m.major_code='KTPM' ORDER BY s.id LIMIT 1),
    (SELECT id FROM guardians WHERE phone='0912345678'),
    '2200002', 'Tran Thi Thu Ha', 'hatrtt22@student.edu.vn', '45 Nguyen Hue, Q1, TP.HCM',
    '2004-07-22', FALSE, '0902222222', '079204023456', 'Kinh', 'Khong', 'TP.HCM', 'Viet Nam', 'ACTIVE', NOW(), FALSE
),
(
    (SELECT id FROM accounts WHERE username='sv.2200003'),
    (SELECT id FROM administrative_classes WHERE class_name='KTPM22B'),
    (SELECT s.id FROM specializations s JOIN majors m ON s.major_id=m.id WHERE m.major_code='KTPM' ORDER BY s.id LIMIT 1 OFFSET 1),
    (SELECT id FROM guardians WHERE phone='0923456789'),
    '2200003', 'Le Hoang Nam', 'namlh22@student.edu.vn', '78 Tran Phu, Q5, TP.HCM',
    '2004-11-08', TRUE, '0903333333', '079204034567', 'Kinh', 'Khong', 'Binh Duong', 'Viet Nam', 'ACTIVE', NOW(), FALSE
),
(
    (SELECT id FROM accounts WHERE username='sv.2200004'),
    (SELECT id FROM administrative_classes WHERE class_name='HTTT22A'),
    (SELECT s.id FROM specializations s JOIN majors m ON s.major_id=m.id WHERE m.major_code='HTTT' ORDER BY s.id LIMIT 1),
    (SELECT id FROM guardians WHERE phone='0934567890'),
    '2200004', 'Pham Thuy Linh', 'linhpt22@student.edu.vn', '23 Dien Bien Phu, Q3, TP.HCM',
    '2004-05-30', FALSE, '0904444444', '079204045678', 'Kinh', 'Khong', 'Dong Nai', 'Viet Nam', 'ACTIVE', NOW(), FALSE
),
(
    (SELECT id FROM accounts WHERE username='sv.2300001'),
    (SELECT id FROM administrative_classes WHERE class_name='KTPM23A'),
    (SELECT s.id FROM specializations s JOIN majors m ON s.major_id=m.id WHERE m.major_code='KTPM' ORDER BY s.id LIMIT 1),
    (SELECT id FROM guardians WHERE phone='0945678901'),
    '2300001', 'Hoang Duc Anh', 'anhhd23@student.edu.vn', '56 CMT8, Q3, TP.HCM',
    '2005-01-17', TRUE, '0905555555', '079205056789', 'Kinh', 'Khong', 'Ha Noi', 'Viet Nam', 'ACTIVE', NOW(), FALSE
),
(
    (SELECT id FROM accounts WHERE username='sv.2300002'),
    (SELECT id FROM administrative_classes WHERE class_name='HTTT23A'),
    (SELECT s.id FROM specializations s JOIN majors m ON s.major_id=m.id WHERE m.major_code='HTTT' ORDER BY s.id LIMIT 1),
    (SELECT id FROM guardians WHERE phone='0956789012'),
    '2300002', 'Do Thi Ngoc Anh', 'anhdtn23@student.edu.vn', '89 Vo Thi Sau, Q3, TP.HCM',
    '2005-09-25', FALSE, '0906666666', '079205067890', 'Kinh', 'Khong', 'Can Tho', 'Viet Nam', 'ACTIVE', NOW(), FALSE
);

-- ----------------------------
-- admission_applications
-- ----------------------------
INSERT INTO admission_applications (
    full_name, date_of_birth, email, phone, national_id, address,
    major_id, total_score, exam_group, status, approval_date, deleted
) VALUES
('Vu Thanh Long',    '2007-04-10', 'longvt25@gmail.com',   '0911111111', '079207001111', '10 Phan Dinh Phung, Ha Noi', (SELECT id FROM majors WHERE major_code='KTPM'), 26.5, 'A00', 'APPROVED', '2025-08-15 09:00:00', FALSE),
('Bui Thi My Duyen', '2007-08-22', 'duyenbtm25@gmail.com', '0922222222', '079207002222', '5 Tran Hung Dao, TP.HCM',   (SELECT id FROM majors WHERE major_code='KTPM'), 25.0, 'D01', 'APPROVED', '2025-08-15 10:00:00', FALSE),
('Ngo Van Phuc',     '2007-02-14', 'phucnv25@gmail.com',   '0933333333', '079207003333', '33 Ly Tu Trong, Da Nang',   (SELECT id FROM majors WHERE major_code='HTTT'), 23.5, 'A00', 'PENDING',  NULL,                  FALSE),
('Dinh Thi Kim Yen', '2007-06-30', 'yendtk25@gmail.com',   '0944444444', '079207004444', '77 Nguyen Trai, Can Tho',   (SELECT id FROM majors WHERE major_code='ATTT'), 24.0, 'A00', 'PENDING',  NULL,                  FALSE),
('Ly Quang Vinh',    '2007-12-05', 'vinhlq25@gmail.com',   '0955555555', '079207005555', '18 Hung Vuong, Hue',        (SELECT id FROM majors WHERE major_code='KT'),   19.5, 'D01', 'REJECTED', '2025-08-20 14:00:00', FALSE);

-- ----------------------------
-- course_sections
-- ----------------------------
INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, deleted) VALUES
('CS101-01', 'Nhap mon Lap trinh - Nhom 1',        (SELECT id FROM courses WHERE course_code='CS101'), (SELECT id FROM lecturers WHERE email='nguyenvana@edu.vn'), (SELECT id FROM semesters WHERE semester_number=1 AND academic_year='2023-2024'), 40, 'FINISHED', FALSE),
('CS101-02', 'Nhap mon Lap trinh - Nhom 2',        (SELECT id FROM courses WHERE course_code='CS101'), (SELECT id FROM lecturers WHERE email='tranthib@edu.vn'),   (SELECT id FROM semesters WHERE semester_number=1 AND academic_year='2023-2024'), 40, 'FINISHED', FALSE),
('MT101-01', 'Toan roi rac - Nhom 1',              (SELECT id FROM courses WHERE course_code='MT101'), (SELECT id FROM lecturers WHERE email='levanc@edu.vn'),     (SELECT id FROM semesters WHERE semester_number=1 AND academic_year='2023-2024'), 40, 'FINISHED', FALSE),
('MT102-01', 'Giai tich - Nhom 1',                 (SELECT id FROM courses WHERE course_code='MT102'), (SELECT id FROM lecturers WHERE email='phamthid@edu.vn'),   (SELECT id FROM semesters WHERE semester_number=1 AND academic_year='2023-2024'), 40, 'FINISHED', FALSE),
('CS201-01', 'Lap trinh Huong doi tuong - Nhom 1', (SELECT id FROM courses WHERE course_code='CS201'), (SELECT id FROM lecturers WHERE email='nguyenvana@edu.vn'), (SELECT id FROM semesters WHERE semester_number=2 AND academic_year='2023-2024'), 40, 'FINISHED', FALSE),
('CS301-01', 'Co so Du lieu - Nhom 1',             (SELECT id FROM courses WHERE course_code='CS301'), (SELECT id FROM lecturers WHERE email='tranthib@edu.vn'),   (SELECT id FROM semesters WHERE semester_number=2 AND academic_year='2023-2024'), 40, 'FINISHED', FALSE),
('CS301-02', 'Co so Du lieu - Nhom 2',             (SELECT id FROM courses WHERE course_code='CS301'), (SELECT id FROM lecturers WHERE email='levanc@edu.vn'),     (SELECT id FROM semesters WHERE semester_number=2 AND academic_year='2023-2024'), 35, 'FINISHED', FALSE),
('CS102-01', 'Cau truc Du lieu va GT - Nhom 1',    (SELECT id FROM courses WHERE course_code='CS102'), (SELECT id FROM lecturers WHERE email='nguyenvana@edu.vn'), (SELECT id FROM semesters WHERE semester_number=1 AND academic_year='2024-2025'), 40, 'ONGOING',  FALSE),
('CS302-01', 'Cong nghe Phan mem - Nhom 1',        (SELECT id FROM courses WHERE course_code='CS302'), (SELECT id FROM lecturers WHERE email='tranthib@edu.vn'),   (SELECT id FROM semesters WHERE semester_number=1 AND academic_year='2024-2025'), 40, 'ONGOING',  FALSE),
('CS401-01', 'Lap trinh Web - Nhom 1',             (SELECT id FROM courses WHERE course_code='CS401'), (SELECT id FROM lecturers WHERE email='levanc@edu.vn'),     (SELECT id FROM semesters WHERE semester_number=1 AND academic_year='2024-2025'), 35, 'OPEN',     FALSE),
('CS402-01', 'Lap trinh Di dong - Nhom 1',         (SELECT id FROM courses WHERE course_code='CS402'), (SELECT id FROM lecturers WHERE email='phamthid@edu.vn'),   (SELECT id FROM semesters WHERE semester_number=1 AND academic_year='2024-2025'), 35, 'OPEN',     FALSE),
('CS302-02', 'Cong nghe Phan mem - Nhom 2',        (SELECT id FROM courses WHERE course_code='CS302'), (SELECT id FROM lecturers WHERE email='nguyenvana@edu.vn'), (SELECT id FROM semesters WHERE semester_number=2 AND academic_year='2024-2025'), 40, 'DRAFT',    FALSE),
('EC101-01', 'Kinh te vi mo - Nhom 1',             (SELECT id FROM courses WHERE course_code='EC101'), (SELECT id FROM lecturers WHERE email='phamthid@edu.vn'),   (SELECT id FROM semesters WHERE semester_number=2 AND academic_year='2024-2025'), 40, 'DRAFT',    FALSE);

-- ----------------------------
-- recurring_schedules
-- ----------------------------
INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period) VALUES
((SELECT id FROM course_sections WHERE section_code='CS101-01'), (SELECT id FROM classrooms WHERE room_name='A101'), 2, 1, 3),
((SELECT id FROM course_sections WHERE section_code='CS101-02'), (SELECT id FROM classrooms WHERE room_name='A102'), 4, 1, 3),
((SELECT id FROM course_sections WHERE section_code='MT101-01'), (SELECT id FROM classrooms WHERE room_name='A101'), 3, 4, 6),
((SELECT id FROM course_sections WHERE section_code='MT102-01'), (SELECT id FROM classrooms WHERE room_name='A102'), 5, 1, 3),
((SELECT id FROM course_sections WHERE section_code='CS201-01'), (SELECT id FROM classrooms WHERE room_name='A101'), 2, 4, 6),
((SELECT id FROM course_sections WHERE section_code='CS301-01'), (SELECT id FROM classrooms WHERE room_name='B201'), 3, 1, 3),
((SELECT id FROM course_sections WHERE section_code='CS301-02'), (SELECT id FROM classrooms WHERE room_name='B202'), 5, 4, 6),
((SELECT id FROM course_sections WHERE section_code='CS102-01'), (SELECT id FROM classrooms WHERE room_name='A101'), 2, 1, 3),
((SELECT id FROM course_sections WHERE section_code='CS302-01'), (SELECT id FROM classrooms WHERE room_name='A102'), 4, 4, 6),
((SELECT id FROM course_sections WHERE section_code='CS401-01'), (SELECT id FROM classrooms WHERE room_name='B201'), 3, 1, 3),
((SELECT id FROM course_sections WHERE section_code='CS402-01'), (SELECT id FROM classrooms WHERE room_name='B202'), 6, 1, 3);

-- ----------------------------
-- class_sessions
-- ----------------------------
INSERT INTO class_sessions (section_id, room_id, session_date, start_period, end_period, lesson_content, status) VALUES
((SELECT id FROM course_sections WHERE section_code='CS101-01'), (SELECT id FROM classrooms WHERE room_name='A101'), '2023-09-04', 1, 3, 'Gioi thieu mon hoc, cai dat moi truong Python', 'NORMAL'),
((SELECT id FROM course_sections WHERE section_code='CS101-01'), (SELECT id FROM classrooms WHERE room_name='A101'), '2023-09-11', 1, 3, 'Kieu du lieu, bien, toan tu',                   'NORMAL'),
((SELECT id FROM course_sections WHERE section_code='CS101-01'), (SELECT id FROM classrooms WHERE room_name='A101'), '2023-09-18', 1, 3, 'Cau lenh dieu kien if-else',                    'NORMAL'),
((SELECT id FROM course_sections WHERE section_code='CS101-01'), (SELECT id FROM classrooms WHERE room_name='A101'), '2023-09-25', 1, 3, 'Vong lap for, while',                           'NORMAL'),
((SELECT id FROM course_sections WHERE section_code='CS101-02'), (SELECT id FROM classrooms WHERE room_name='A102'), '2023-09-06', 1, 3, 'Gioi thieu mon hoc, cai dat moi truong Python', 'NORMAL'),
((SELECT id FROM course_sections WHERE section_code='CS101-02'), (SELECT id FROM classrooms WHERE room_name='A102'), '2023-09-13', 1, 3, 'Kieu du lieu, bien, toan tu',                   'NORMAL'),
((SELECT id FROM course_sections WHERE section_code='CS101-02'), (SELECT id FROM classrooms WHERE room_name='A102'), '2023-09-20', 1, 3, 'Cau lenh dieu kien if-else',                    'NORMAL'),
((SELECT id FROM course_sections WHERE section_code='CS201-01'), (SELECT id FROM classrooms WHERE room_name='A101'), '2024-02-05', 4, 6, 'Gioi thieu OOP, Class va Object', 'NORMAL'),
((SELECT id FROM course_sections WHERE section_code='CS201-01'), (SELECT id FROM classrooms WHERE room_name='A101'), '2024-02-12', 4, 6, 'Ke thua (Inheritance)',           'NORMAL'),
((SELECT id FROM course_sections WHERE section_code='CS201-01'), (SELECT id FROM classrooms WHERE room_name='A101'), '2024-02-19', 4, 6, 'Da hinh (Polymorphism)',          'NORMAL'),
((SELECT id FROM course_sections WHERE section_code='CS102-01'), (SELECT id FROM classrooms WHERE room_name='A101'), '2024-09-02', 1, 3, 'Gioi thieu CTDL, Do phuc tap thuat toan', 'NORMAL'),
((SELECT id FROM course_sections WHERE section_code='CS102-01'), (SELECT id FROM classrooms WHERE room_name='A101'), '2024-09-09', 1, 3, 'Mang va Danh sach lien ket',              'NORMAL'),
((SELECT id FROM course_sections WHERE section_code='CS102-01'), (SELECT id FROM classrooms WHERE room_name='A101'), '2024-09-16', 1, 3, 'Stack va Queue',                           'NORMAL');

-- ----------------------------
-- course_registrations
-- ----------------------------
INSERT INTO course_registrations (student_id, section_id, registration_time, status, deleted) VALUES
((SELECT id FROM students WHERE student_code='2200001'), (SELECT id FROM course_sections WHERE section_code='CS101-01'), '2023-08-25 08:00:00', 'CONFIRMED', FALSE),
((SELECT id FROM students WHERE student_code='2200001'), (SELECT id FROM course_sections WHERE section_code='MT101-01'), '2023-08-25 08:05:00', 'CONFIRMED', FALSE),
((SELECT id FROM students WHERE student_code='2200002'), (SELECT id FROM course_sections WHERE section_code='CS101-01'), '2023-08-25 09:00:00', 'CONFIRMED', FALSE),
((SELECT id FROM students WHERE student_code='2200002'), (SELECT id FROM course_sections WHERE section_code='MT102-01'), '2023-08-25 09:05:00', 'CONFIRMED', FALSE),
((SELECT id FROM students WHERE student_code='2200003'), (SELECT id FROM course_sections WHERE section_code='CS101-02'), '2023-08-26 08:00:00', 'CONFIRMED', FALSE),
((SELECT id FROM students WHERE student_code='2200003'), (SELECT id FROM course_sections WHERE section_code='MT101-01'), '2023-08-26 08:05:00', 'CONFIRMED', FALSE),
((SELECT id FROM students WHERE student_code='2200004'), (SELECT id FROM course_sections WHERE section_code='CS101-02'), '2023-08-26 09:00:00', 'CONFIRMED', FALSE),
((SELECT id FROM students WHERE student_code='2200004'), (SELECT id FROM course_sections WHERE section_code='MT102-01'), '2023-08-26 09:05:00', 'CONFIRMED', FALSE),
((SELECT id FROM students WHERE student_code='2200001'), (SELECT id FROM course_sections WHERE section_code='CS201-01'), '2024-01-20 08:00:00', 'CONFIRMED', FALSE),
((SELECT id FROM students WHERE student_code='2200002'), (SELECT id FROM course_sections WHERE section_code='CS201-01'), '2024-01-20 08:10:00', 'CONFIRMED', FALSE),
((SELECT id FROM students WHERE student_code='2200003'), (SELECT id FROM course_sections WHERE section_code='CS301-01'), '2024-01-20 09:00:00', 'CONFIRMED', FALSE),
((SELECT id FROM students WHERE student_code='2200004'), (SELECT id FROM course_sections WHERE section_code='CS301-02'), '2024-01-20 09:10:00', 'CONFIRMED', FALSE),
((SELECT id FROM students WHERE student_code='2200001'), (SELECT id FROM course_sections WHERE section_code='CS102-01'), '2024-08-20 08:00:00', 'CONFIRMED', FALSE),
((SELECT id FROM students WHERE student_code='2200002'), (SELECT id FROM course_sections WHERE section_code='CS102-01'), '2024-08-20 08:10:00', 'CONFIRMED', FALSE),
((SELECT id FROM students WHERE student_code='2300001'), (SELECT id FROM course_sections WHERE section_code='CS302-01'), '2024-08-20 09:00:00', 'CONFIRMED', FALSE),
((SELECT id FROM students WHERE student_code='2300002'), (SELECT id FROM course_sections WHERE section_code='CS302-01'), '2024-08-20 09:10:00', 'CONFIRMED', FALSE),
((SELECT id FROM students WHERE student_code='2200001'), (SELECT id FROM course_sections WHERE section_code='CS401-01'), '2024-08-21 08:00:00', 'PENDING', FALSE),
((SELECT id FROM students WHERE student_code='2300001'), (SELECT id FROM course_sections WHERE section_code='CS402-01'), '2024-08-21 08:05:00', 'PENDING', FALSE);

-- ----------------------------
-- attendance
-- ----------------------------
INSERT INTO attendance (session_id, registration_id, attendance_status, note, deleted) VALUES
-- CS101-01 buoi 1 (2023-09-04)
((SELECT cs.id FROM class_sessions cs JOIN course_sections sec ON cs.section_id=sec.id WHERE sec.section_code='CS101-01' AND cs.session_date='2023-09-04'), (SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200001' AND sec.section_code='CS101-01'), 'PRESENT', NULL, FALSE),
((SELECT cs.id FROM class_sessions cs JOIN course_sections sec ON cs.section_id=sec.id WHERE sec.section_code='CS101-01' AND cs.session_date='2023-09-04'), (SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200002' AND sec.section_code='CS101-01'), 'PRESENT', NULL, FALSE),
-- CS101-01 buoi 2 (2023-09-11)
((SELECT cs.id FROM class_sessions cs JOIN course_sections sec ON cs.section_id=sec.id WHERE sec.section_code='CS101-01' AND cs.session_date='2023-09-11'), (SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200001' AND sec.section_code='CS101-01'), 'PRESENT', NULL, FALSE),
((SELECT cs.id FROM class_sessions cs JOIN course_sections sec ON cs.section_id=sec.id WHERE sec.section_code='CS101-01' AND cs.session_date='2023-09-11'), (SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200002' AND sec.section_code='CS101-01'), 'ABSENT', 'Nghi co phep', FALSE),
-- CS101-01 buoi 3 (2023-09-18)
((SELECT cs.id FROM class_sessions cs JOIN course_sections sec ON cs.section_id=sec.id WHERE sec.section_code='CS101-01' AND cs.session_date='2023-09-18'), (SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200001' AND sec.section_code='CS101-01'), 'LATE', 'Den muon 10 phut', FALSE),
((SELECT cs.id FROM class_sessions cs JOIN course_sections sec ON cs.section_id=sec.id WHERE sec.section_code='CS101-01' AND cs.session_date='2023-09-18'), (SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200002' AND sec.section_code='CS101-01'), 'PRESENT', NULL, FALSE),
-- CS101-02 buoi 1 (2023-09-06)
((SELECT cs.id FROM class_sessions cs JOIN course_sections sec ON cs.section_id=sec.id WHERE sec.section_code='CS101-02' AND cs.session_date='2023-09-06'), (SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200003' AND sec.section_code='CS101-02'), 'PRESENT', NULL, FALSE),
((SELECT cs.id FROM class_sessions cs JOIN course_sections sec ON cs.section_id=sec.id WHERE sec.section_code='CS101-02' AND cs.session_date='2023-09-06'), (SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200004' AND sec.section_code='CS101-02'), 'PRESENT', NULL, FALSE),
-- CS101-02 buoi 2 (2023-09-13)
((SELECT cs.id FROM class_sessions cs JOIN course_sections sec ON cs.section_id=sec.id WHERE sec.section_code='CS101-02' AND cs.session_date='2023-09-13'), (SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200003' AND sec.section_code='CS101-02'), 'ABSENT', 'Nghi khong phep', FALSE),
((SELECT cs.id FROM class_sessions cs JOIN course_sections sec ON cs.section_id=sec.id WHERE sec.section_code='CS101-02' AND cs.session_date='2023-09-13'), (SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200004' AND sec.section_code='CS101-02'), 'PRESENT', NULL, FALSE),
-- CS201-01 buoi 1 (2024-02-05)
((SELECT cs.id FROM class_sessions cs JOIN course_sections sec ON cs.section_id=sec.id WHERE sec.section_code='CS201-01' AND cs.session_date='2024-02-05'), (SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200001' AND sec.section_code='CS201-01'), 'PRESENT', NULL, FALSE),
((SELECT cs.id FROM class_sessions cs JOIN course_sections sec ON cs.section_id=sec.id WHERE sec.section_code='CS201-01' AND cs.session_date='2024-02-05'), (SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200002' AND sec.section_code='CS201-01'), 'PRESENT', NULL, FALSE),
-- CS201-01 buoi 2 (2024-02-12)
((SELECT cs.id FROM class_sessions cs JOIN course_sections sec ON cs.section_id=sec.id WHERE sec.section_code='CS201-01' AND cs.session_date='2024-02-12'), (SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200001' AND sec.section_code='CS201-01'), 'PRESENT', NULL, FALSE),
((SELECT cs.id FROM class_sessions cs JOIN course_sections sec ON cs.section_id=sec.id WHERE sec.section_code='CS201-01' AND cs.session_date='2024-02-12'), (SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200002' AND sec.section_code='CS201-01'), 'ABSENT', 'Nghi co phep', FALSE),
-- CS102-01 buoi 1 (2024-09-02)
((SELECT cs.id FROM class_sessions cs JOIN course_sections sec ON cs.section_id=sec.id WHERE sec.section_code='CS102-01' AND cs.session_date='2024-09-02'), (SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200001' AND sec.section_code='CS102-01'), 'PRESENT', NULL, FALSE),
((SELECT cs.id FROM class_sessions cs JOIN course_sections sec ON cs.section_id=sec.id WHERE sec.section_code='CS102-01' AND cs.session_date='2024-09-02'), (SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200002' AND sec.section_code='CS102-01'), 'PRESENT', NULL, FALSE),
-- CS102-01 buoi 2 (2024-09-09)
((SELECT cs.id FROM class_sessions cs JOIN course_sections sec ON cs.section_id=sec.id WHERE sec.section_code='CS102-01' AND cs.session_date='2024-09-09'), (SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200001' AND sec.section_code='CS102-01'), 'LATE', 'Den muon 15 phut', FALSE),
((SELECT cs.id FROM class_sessions cs JOIN course_sections sec ON cs.section_id=sec.id WHERE sec.section_code='CS102-01' AND cs.session_date='2024-09-09'), (SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200002' AND sec.section_code='CS102-01'), 'PRESENT', NULL, FALSE);

-- ----------------------------
-- grade_components (bo sung cho cac mon chua co trong V2)
-- V2 da co: CS101 (id 1,2,3), CS102 (id 4,5,6)
-- ----------------------------
INSERT INTO grade_components (component_name, weight_percentage, course_id, deleted) VALUES
('Chuyen can', 10.0, (SELECT id FROM courses WHERE course_code='CS201'), FALSE),
('Giua ky',    30.0, (SELECT id FROM courses WHERE course_code='CS201'), FALSE),
('Cuoi ky',    60.0, (SELECT id FROM courses WHERE course_code='CS201'), FALSE),
('Chuyen can', 10.0, (SELECT id FROM courses WHERE course_code='CS301'), FALSE),
('Thuc hanh',  20.0, (SELECT id FROM courses WHERE course_code='CS301'), FALSE),
('Giua ky',    20.0, (SELECT id FROM courses WHERE course_code='CS301'), FALSE),
('Cuoi ky',    50.0, (SELECT id FROM courses WHERE course_code='CS301'), FALSE),
('Chuyen can', 10.0, (SELECT id FROM courses WHERE course_code='CS302'), FALSE),
('Do an',      40.0, (SELECT id FROM courses WHERE course_code='CS302'), FALSE),
('Cuoi ky',    50.0, (SELECT id FROM courses WHERE course_code='CS302'), FALSE),
('Chuyen can', 10.0, (SELECT id FROM courses WHERE course_code='CS401'), FALSE),
('Thuc hanh',  30.0, (SELECT id FROM courses WHERE course_code='CS401'), FALSE),
('Cuoi ky',    60.0, (SELECT id FROM courses WHERE course_code='CS401'), FALSE),
('Chuyen can', 10.0, (SELECT id FROM courses WHERE course_code='CS402'), FALSE),
('Thuc hanh',  30.0, (SELECT id FROM courses WHERE course_code='CS402'), FALSE),
('Cuoi ky',    60.0, (SELECT id FROM courses WHERE course_code='CS402'), FALSE),
('Chuyen can', 10.0, (SELECT id FROM courses WHERE course_code='MT101'), FALSE),
('Giua ky',    30.0, (SELECT id FROM courses WHERE course_code='MT101'), FALSE),
('Cuoi ky',    60.0, (SELECT id FROM courses WHERE course_code='MT101'), FALSE),
('Chuyen can', 10.0, (SELECT id FROM courses WHERE course_code='MT102'), FALSE),
('Giua ky',    30.0, (SELECT id FROM courses WHERE course_code='MT102'), FALSE),
('Cuoi ky',    60.0, (SELECT id FROM courses WHERE course_code='MT102'), FALSE),
('Chuyen can', 10.0, (SELECT id FROM courses WHERE course_code='EC101'), FALSE),
('Giua ky',    30.0, (SELECT id FROM courses WHERE course_code='EC101'), FALSE),
('Cuoi ky',    60.0, (SELECT id FROM courses WHERE course_code='EC101'), FALSE);

-- ----------------------------
-- grade_reports
-- ----------------------------
INSERT INTO grade_reports (registration_id, final_score, letter_grade, status, created_at, deleted) VALUES
((SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200001' AND sec.section_code='CS101-01'), 8.5, 'B+', 'PUBLISHED', NOW(), FALSE),
((SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200002' AND sec.section_code='CS101-01'), 7.8, 'B',  'PUBLISHED', NOW(), FALSE),
((SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200001' AND sec.section_code='MT101-01'), 9.0, 'A',  'PUBLISHED', NOW(), FALSE),
((SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200002' AND sec.section_code='MT102-01'), 6.5, 'C+', 'PUBLISHED', NOW(), FALSE),
((SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200004' AND sec.section_code='MT102-01'), 7.0, 'B-', 'PUBLISHED', NOW(), FALSE),
((SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200003' AND sec.section_code='CS101-02'), 8.0, 'B',  'PUBLISHED', NOW(), FALSE),
((SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200004' AND sec.section_code='CS101-02'), 7.5, 'B',  'PUBLISHED', NOW(), FALSE),
((SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200001' AND sec.section_code='CS201-01'), 8.8, 'A-', 'PUBLISHED', NOW(), FALSE),
((SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200002' AND sec.section_code='CS201-01'), 7.2, 'B-', 'PUBLISHED', NOW(), FALSE),
((SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200003' AND sec.section_code='CS301-01'), 8.3, 'B+', 'DRAFT',     NOW(), FALSE),
((SELECT cr.id FROM course_registrations cr JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200004' AND sec.section_code='CS301-02'), 9.1, 'A',  'DRAFT',     NOW(), FALSE);

-- ----------------------------
-- grade_details
-- ----------------------------
INSERT INTO grade_details (report_id, component_id, score, deleted) VALUES
-- sv 2200001 - CS101-01
((SELECT gr.id FROM grade_reports gr JOIN course_registrations cr ON gr.registration_id=cr.id JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200001' AND sec.section_code='CS101-01'), (SELECT id FROM grade_components WHERE course_id=(SELECT id FROM courses WHERE course_code='CS101') AND component_name='Attendance'), 9.0, FALSE),
((SELECT gr.id FROM grade_reports gr JOIN course_registrations cr ON gr.registration_id=cr.id JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200001' AND sec.section_code='CS101-01'), (SELECT id FROM grade_components WHERE course_id=(SELECT id FROM courses WHERE course_code='CS101') AND component_name='Midterm'),    8.0, FALSE),
((SELECT gr.id FROM grade_reports gr JOIN course_registrations cr ON gr.registration_id=cr.id JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200001' AND sec.section_code='CS101-01'), (SELECT id FROM grade_components WHERE course_id=(SELECT id FROM courses WHERE course_code='CS101') AND component_name='Final'),       8.7, FALSE),
-- sv 2200002 - CS101-01
((SELECT gr.id FROM grade_reports gr JOIN course_registrations cr ON gr.registration_id=cr.id JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200002' AND sec.section_code='CS101-01'), (SELECT id FROM grade_components WHERE course_id=(SELECT id FROM courses WHERE course_code='CS101') AND component_name='Attendance'), 8.0, FALSE),
((SELECT gr.id FROM grade_reports gr JOIN course_registrations cr ON gr.registration_id=cr.id JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200002' AND sec.section_code='CS101-01'), (SELECT id FROM grade_components WHERE course_id=(SELECT id FROM courses WHERE course_code='CS101') AND component_name='Midterm'),    7.5, FALSE),
((SELECT gr.id FROM grade_reports gr JOIN course_registrations cr ON gr.registration_id=cr.id JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200002' AND sec.section_code='CS101-01'), (SELECT id FROM grade_components WHERE course_id=(SELECT id FROM courses WHERE course_code='CS101') AND component_name='Final'),       7.9, FALSE),
-- sv 2200001 - MT101-01
((SELECT gr.id FROM grade_reports gr JOIN course_registrations cr ON gr.registration_id=cr.id JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200001' AND sec.section_code='MT101-01'), (SELECT id FROM grade_components WHERE course_id=(SELECT id FROM courses WHERE course_code='MT101') AND component_name='Chuyen can'), 10.0, FALSE),
((SELECT gr.id FROM grade_reports gr JOIN course_registrations cr ON gr.registration_id=cr.id JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200001' AND sec.section_code='MT101-01'), (SELECT id FROM grade_components WHERE course_id=(SELECT id FROM courses WHERE course_code='MT101') AND component_name='Giua ky'),    8.5, FALSE),
((SELECT gr.id FROM grade_reports gr JOIN course_registrations cr ON gr.registration_id=cr.id JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200001' AND sec.section_code='MT101-01'), (SELECT id FROM grade_components WHERE course_id=(SELECT id FROM courses WHERE course_code='MT101') AND component_name='Cuoi ky'),    9.2, FALSE),
-- sv 2200001 - CS201-01
((SELECT gr.id FROM grade_reports gr JOIN course_registrations cr ON gr.registration_id=cr.id JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200001' AND sec.section_code='CS201-01'), (SELECT id FROM grade_components WHERE course_id=(SELECT id FROM courses WHERE course_code='CS201') AND component_name='Chuyen can'), 10.0, FALSE),
((SELECT gr.id FROM grade_reports gr JOIN course_registrations cr ON gr.registration_id=cr.id JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200001' AND sec.section_code='CS201-01'), (SELECT id FROM grade_components WHERE course_id=(SELECT id FROM courses WHERE course_code='CS201') AND component_name='Giua ky'),    8.5, FALSE),
((SELECT gr.id FROM grade_reports gr JOIN course_registrations cr ON gr.registration_id=cr.id JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200001' AND sec.section_code='CS201-01'), (SELECT id FROM grade_components WHERE course_id=(SELECT id FROM courses WHERE course_code='CS201') AND component_name='Cuoi ky'),    8.8, FALSE),
-- sv 2200002 - CS201-01
((SELECT gr.id FROM grade_reports gr JOIN course_registrations cr ON gr.registration_id=cr.id JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200002' AND sec.section_code='CS201-01'), (SELECT id FROM grade_components WHERE course_id=(SELECT id FROM courses WHERE course_code='CS201') AND component_name='Chuyen can'), 8.0, FALSE),
((SELECT gr.id FROM grade_reports gr JOIN course_registrations cr ON gr.registration_id=cr.id JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200002' AND sec.section_code='CS201-01'), (SELECT id FROM grade_components WHERE course_id=(SELECT id FROM courses WHERE course_code='CS201') AND component_name='Giua ky'),    7.0, FALSE),
((SELECT gr.id FROM grade_reports gr JOIN course_registrations cr ON gr.registration_id=cr.id JOIN students s ON cr.student_id=s.id JOIN course_sections sec ON cr.section_id=sec.id WHERE s.student_code='2200002' AND sec.section_code='CS201-01'), (SELECT id FROM grade_components WHERE course_id=(SELECT id FROM courses WHERE course_code='CS201') AND component_name='Cuoi ky'),    7.3, FALSE);


