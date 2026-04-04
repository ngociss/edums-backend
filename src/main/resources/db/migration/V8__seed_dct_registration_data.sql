-- Seed registration offerings from DCT.pdf
-- Source: C:/Users/User/Desktop/DCT.pdf
-- Notes:
-- 1) This file seeds semester, registration period, lecturers, classrooms, course sections, and recurring schedules for Semester 2 - 2025-2026.
-- 2) Tuan hoc in the PDF is stored here as an approximated start_week/end_week range because the current schema does not support sparse week masks.
-- 3) section_code is the PDF group number.

INSERT INTO faculties (faculty_name, faculty_code, deleted)
SELECT 'Faculty of Information Technology', 'FIT', b'0'
WHERE NOT EXISTS (SELECT 1 FROM faculties WHERE faculty_code = 'FIT');

INSERT INTO semesters (semester_number, academic_year, start_date, end_date, total_weeks, deleted)
SELECT 2, '2025-2026', '2025-12-22', '2026-05-10', 25, b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM semesters s WHERE s.semester_number = 2 AND s.academic_year = '2025-2026'
);

INSERT INTO registration_periods (semester_id, name, start_time, end_time, status, created_at, deleted)
SELECT s.id, 'DCT Registration Window - Semester 2', '2025-11-17 00:00:00', '2026-02-15 23:59:59', 'OPEN', NOW(), b'0'
FROM semesters s
WHERE s.semester_number = 2
  AND s.academic_year = '2025-2026'
  AND NOT EXISTS (
      SELECT 1 FROM registration_periods rp WHERE rp.semester_id = s.id AND rp.name = 'DCT Registration Window - Semester 2'
  );

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841048', 'Phân tích thiết kế hệ thống thông tin', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841048');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841058', 'Hệ điều hành mã nguồn mở', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841058');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841068', 'Hệ thống thông tin doanh nghiệp', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841068');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841070', 'Thực tập tốt nghiệp (DCT)', 6,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841070');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841072', 'Các công nghệ lập trình hiện đại', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841072');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841107', 'Ngôn ngữ lập trình Java', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841107');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841108', 'Cấu trúc dữ liệu và giải thuật', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841108');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841110', 'Cơ sở trí tuệ nhân tạo', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841110');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841113', 'Phát triển phần mềm mã nguồn mở', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841113');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841303', 'Kỹ thuật lập trình', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841303');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841404', 'Mạng máy tính', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841404');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841407', 'Các hệ quản trị cơ sở dữ liệu', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841407');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841408', 'Kiểm thử phần mềm', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841408');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841410', 'An ninh mạng máy tính', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841410');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841413', 'Cơ sở dữ liệu phân tán', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841413');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841414', 'Thiết kế và phân tích giải thuật', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841414');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841415', 'Luật pháp và CNTT', 2,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841415');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841423', 'Ngôn ngữ lập trình C#', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841423');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841434', 'Thương mại điện tử và ứng dụng', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841434');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841438', 'Lập trình ứng dụng mạng', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841438');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841439', 'Mạng không dây', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841439');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841440', 'Phân tích và thiết kế mạng máy tính', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841440');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841442', 'Mạng đa phương tiện và di động (ngành CNTT)', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841442');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841446', 'Phân tích và xử lý ảnh', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841446');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841447', 'Khai thác dữ liệu và ứng dụng', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841447');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841448', 'Xử lý ngôn ngữ tự nhiên', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841448');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841456', 'Công nghệ tri thức', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841456');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841462', 'Phát triển ứng dụng trên thiết bị di động (ngành KTPM)', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841462');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841463', 'Phát triển ứng dụng trên thiết bị di động nâng cao', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841463');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841464', 'Lập trình Web và ứng dụng nâng cao', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841464');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841476', 'Đồ án chuyên ngành (ngành CNTT, ngành KTPM)', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841476');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841480', 'Xây dựng phần mềm theo mô hình phân lớp (2020)', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841480');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841481', 'Thiết kế giao diện', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841481');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841482', 'Seminar chuyên đề (ngành CNTT, ngành KTPM)', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841482');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Cao Minh Thành', NULL, '10011@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '10011@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Lai Đình Khải', NULL, '10063@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '10063@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Cổ Tồn Minh Đăng', NULL, '10094@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '10094@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Cao Thái Phương Thanh', NULL, '10220@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '10220@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Phùng Thái Thiên Trang', NULL, '10227@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '10227@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Nguyễn Quốc Huy', NULL, '10600@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '10600@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Trần Đình Nghĩa', NULL, '10601@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '10601@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Trần Nguyễn Minh Hiếu', NULL, '10615@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '10615@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Phan Tấn Quốc', NULL, '10631@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '10631@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Hoàng Mạnh Hà', NULL, '10875@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '10875@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Nguyễn Hòa', NULL, '10943@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '10943@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Nguyễn Võ Lam Giang', NULL, '10944@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '10944@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Nguyễn Thanh Sang', NULL, '10991@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '10991@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Huỳnh Lê Minh Thiện', NULL, '11137@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '11137@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Nguyễn Thị Hồng Anh', NULL, '11271@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '11271@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Lê Nhị Lãm Thúy', NULL, '11363@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '11363@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Lương Minh Huấn', NULL, '11364@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '11364@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Nguyễn Trung Tín', NULL, '11377@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '11377@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Phạm Thế Bảo', NULL, '11381@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '11381@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Trịnh Tấn Đạt', NULL, '11383@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '11383@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Trương Tấn Khoa', NULL, '11384@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '11384@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Phạm Thi Vương', NULL, '11426@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '11426@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Nguyễn Tuấn Đăng', NULL, '11453@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '11453@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Phan Nguyệt Minh', NULL, '11541@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '11541@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Hà Thanh Dũng', NULL, '11544@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '11544@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Huỳnh Nguyễn Khắc Huy', NULL, '11556@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '11556@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Nguyễn Quốc Phong', NULL, '11562@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '11562@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Lê Tấn Long', NULL, '11610@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '11610@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Từ Lãng Phiêu', NULL, '11636@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '11636@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Quách Thị Ngọc Thiện', NULL, '11657@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '11657@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Nguyễn Thanh Phước', NULL, '11675@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '11675@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Nguyễn Duy Hàm', NULL, '11717@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '11717@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Đỗ Như Tài', NULL, '11742@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '11742@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Trần Việt Chương', NULL, '11798@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '11798@sgu.local');

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Huỳnh Minh Trí', NULL, '20766@sgu.local', NULL, b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers le WHERE le.email = '20766@sgu.local');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT '1.A101', 120, 'LAB', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = '1.A101');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT '1.A201', 120, 'LAB', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = '1.A201');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT '1.A202', 120, 'LAB', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = '1.A202');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT '1.B003', 120, 'LAB', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = '1.B003');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT '1.B101', 120, 'LAB', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = '1.B101');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT '1.C004', 120, 'LAB', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = '1.C004');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT '1.C101', 120, 'LAB', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = '1.C101');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT '1.C102', 120, 'LAB', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = '1.C102');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT '1.C105', 120, 'LAB', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = '1.C105');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT '1.C202', 120, 'LAB', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = '1.C202');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.A102', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.A102');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.A103', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.A103');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.A108', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.A108');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.A109', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.A109');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.A208', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.A208');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.A209', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.A209');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.A210', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.A210');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.A303', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.A303');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.A308', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.A308');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.A411', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.A411');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.A412', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.A412');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.B003', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.B003');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.B103', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.B103');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.B107', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.B107');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.B108', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.B108');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.C101', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.C101');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.C103', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.C103');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.C107', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.C107');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.D401', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.D401');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.E001', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.E001');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.E103', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.E103');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.E201', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.E201');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.E203', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.E203');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.E205', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.E205');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.E302', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.E302');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.E304', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.E304');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.E402', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.E402');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.E403', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.E403');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.E501', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.E501');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.E502', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.E502');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.E503', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.E503');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.E504', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.E504');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.E601', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.E601');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.E603', 120, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.E603');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.HB403', 120, 'LAB', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.HB403');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'C.HB406', 120, 'LAB', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'C.HB406');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'TTSP03', 120, 'INTERNSHIP', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'TTSP03');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'TTSP29', 120, 'INTERNSHIP', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms c WHERE c.room_name = 'TTSP29');

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841048 - Group 01 - DKP1241',
       (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11541@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1),
    4, 6, 7, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 6
      AND rs.end_period = 7
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1),
    4, 8, 10, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 8
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E503' LIMIT 1),
    5, 3, 5, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E503' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '02', '841048 - Group 02 - DCT1242',
       (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11541@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '02'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1),
    3, 1, 3, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1),
    3, 4, 5, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E201' LIMIT 1),
    5, 6, 8, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E201' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '03', '841048 - Group 03 - DCT1243',
       (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11541@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '03'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1),
    2, 6, 8, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1),
    2, 9, 10, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E201' LIMIT 1),
    6, 6, 8, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E201' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '04', '841048 - Group 04 - DCT1244',
       (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11556@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '04'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1),
    5, 1, 2, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1),
    5, 3, 5, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.C107' LIMIT 1),
    6, 1, 3, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.C107' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '05', '841048 - Group 05 - DCT1241',
       (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11384@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '05'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1),
    4, 6, 8, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1),
    4, 9, 10, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.C107' LIMIT 1),
    5, 8, 10, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.C107' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 8
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '06', '841048 - Group 06 - DCT1245',
       (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11384@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '06'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1),
    5, 1, 2, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1),
    5, 3, 5, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1),
    4, 1, 5, 1, 6, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 1
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 6
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841058 - Group 01 - DCT1241',
       (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11364@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       80, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E503' LIMIT 1),
    6, 6, 7, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E503' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 6
      AND rs.end_period = 7
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E603' LIMIT 1),
    7, 4, 5, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E603' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1),
    5, 6, 7, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 6
      AND rs.end_period = 7
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '02', '841058 - Group 02 - DCT1242',
       (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11364@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       120, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '02'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E001' LIMIT 1),
    2, 1, 2, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E001' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E304' LIMIT 1),
    6, 1, 2, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E304' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E001' LIMIT 1),
    5, 9, 10, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E001' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '03', '841058 - Group 03 - DKP1241',
       (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11377@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       80, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '03'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.C101' LIMIT 1),
    3, 9, 10, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.C101' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E205' LIMIT 1),
    6, 1, 2, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E205' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.D401' LIMIT 1),
    6, 4, 5, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841058' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.D401' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841068 - Group 01 - DCT1233,DKP1231',
       (SELECT c.id FROM courses c WHERE c.course_code = '841068' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11363@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841068' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841068' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A303' LIMIT 1),
    4, 8, 10, 11, 20, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841068' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A303' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 8
      AND rs.end_period = 10
      AND rs.start_week = 11
      AND rs.end_week = 20
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841068' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1),
    2, 6, 7, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841068' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 6
      AND rs.end_period = 7
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841070 - Group 01 - DCT1225',
       (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1),
       NULL,
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       400, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1),
    2, 1, 5, 12, 19, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 1
      AND rs.end_period = 5
      AND rs.start_week = 12
      AND rs.end_week = 19
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1),
    2, 6, 10, 12, 19, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 6
      AND rs.end_period = 10
      AND rs.start_week = 12
      AND rs.end_week = 19
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1),
    3, 1, 5, 12, 19, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 1
      AND rs.end_period = 5
      AND rs.start_week = 12
      AND rs.end_week = 19
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1),
    3, 6, 10, 12, 19, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 6
      AND rs.end_period = 10
      AND rs.start_week = 12
      AND rs.end_week = 19
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1),
    4, 1, 5, 12, 19, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 1
      AND rs.end_period = 5
      AND rs.start_week = 12
      AND rs.end_week = 19
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1),
    4, 6, 10, 12, 19, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 6
      AND rs.end_period = 10
      AND rs.start_week = 12
      AND rs.end_week = 19
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1),
    5, 1, 5, 12, 19, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 1
      AND rs.end_period = 5
      AND rs.start_week = 12
      AND rs.end_week = 19
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1),
    5, 6, 10, 12, 19, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 6
      AND rs.end_period = 10
      AND rs.start_week = 12
      AND rs.end_week = 19
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1),
    6, 1, 5, 12, 19, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 1
      AND rs.end_period = 5
      AND rs.start_week = 12
      AND rs.end_week = 19
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1),
    6, 6, 10, 12, 19, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 6
      AND rs.end_period = 10
      AND rs.start_week = 12
      AND rs.end_week = 19
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1),
    7, 1, 5, 12, 19, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 1
      AND rs.end_period = 5
      AND rs.start_week = 12
      AND rs.end_week = 19
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1),
    7, 6, 10, 12, 19, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP29' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 6
      AND rs.end_period = 10
      AND rs.start_week = 12
      AND rs.end_week = 19
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP03' LIMIT 1),
    8, 1, 5, 12, 19, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP03' LIMIT 1)
      AND rs.day_of_week = 8
      AND rs.start_period = 1
      AND rs.end_period = 5
      AND rs.start_week = 12
      AND rs.end_week = 19
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP03' LIMIT 1),
    8, 6, 10, 12, 19, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841070' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'TTSP03' LIMIT 1)
      AND rs.day_of_week = 8
      AND rs.start_period = 6
      AND rs.end_period = 10
      AND rs.start_week = 12
      AND rs.end_week = 19
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841072 - Group 01 - DCT1221',
       (SELECT c.id FROM courses c WHERE c.course_code = '841072' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11426@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       120, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841072' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841072' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E603' LIMIT 1),
    7, 6, 7, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841072' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E603' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 6
      AND rs.end_period = 7
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841072' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1),
    7, 8, 9, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841072' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 8
      AND rs.end_period = 9
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '02', '841072 - Group 02 - DKP1221',
       (SELECT c.id FROM courses c WHERE c.course_code = '841072' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11426@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       120, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841072' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '02'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841072' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E603' LIMIT 1),
    7, 2, 3, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841072' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E603' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 2
      AND rs.end_period = 3
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841072' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1),
    7, 4, 5, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841072' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841107 - Group 01 - DCT1241',
       (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11271@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       100, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1),
    2, 1, 3, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1),
    2, 4, 5, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E205' LIMIT 1),
    4, 1, 3, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E205' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '02', '841107 - Group 02 - DCT1242',
       (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11271@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       80, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '02'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1),
    4, 6, 8, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1),
    4, 9, 10, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1),
    6, 6, 8, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '03', '841107 - Group 03 - DCT1243',
       (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10991@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       80, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '03'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1),
    2, 1, 2, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1),
    2, 3, 5, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1),
    5, 3, 5, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '04', '841107 - Group 04 - DCT1244',
       (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10991@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       100, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '04'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1),
    3, 1, 3, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1),
    6, 9, 10, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1),
    6, 6, 8, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '05', '841107 - Group 05 - DKP1241',
       (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10227@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       80, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '05'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1),
    2, 6, 8, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1),
    2, 9, 10, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C004' LIMIT 1),
    6, 8, 10, 3, 15, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C004' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 8
      AND rs.end_period = 10
      AND rs.start_week = 3
      AND rs.end_week = 15
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '06', '841107 - Group 06 - DNT1241',
       (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10227@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       100, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '06'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1),
    4, 1, 3, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1),
    4, 4, 5, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C004' LIMIT 1),
    3, 6, 8, 3, 15, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C004' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 3
      AND rs.end_week = 15
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841108 - Group 01 - DCT1251',
       (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11717@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       80, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1),
    3, 1, 2, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1),
    3, 3, 5, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.D401' LIMIT 1),
    4, 3, 5, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.D401' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '02', '841108 - Group 02 - DCT1252',
       (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11717@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       100, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '02'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1),
    3, 6, 8, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1),
    3, 9, 10, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E205' LIMIT 1),
    4, 6, 8, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E205' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '03', '841108 - Group 03 - DCT1253',
       (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11675@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       80, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '03'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1),
    2, 1, 2, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1),
    2, 3, 5, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E103' LIMIT 1),
    7, 1, 3, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E103' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '04', '841108 - Group 04 - DKP1251',
       (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11610@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       80, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '04'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1),
    6, 6, 7, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 6
      AND rs.end_period = 7
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1),
    6, 8, 10, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 8
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E302' LIMIT 1),
    4, 8, 10, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E302' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 8
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '05', '841108 - Group 05 - DNT1251',
       (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11381@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       120, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '05'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1),
    3, 6, 8, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1),
    3, 9, 10, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E205' LIMIT 1),
    2, 6, 8, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841108' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E205' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841110 - Group 01 - DCT1245',
       (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11742@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       80, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.D401' LIMIT 1),
    7, 6, 7, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.D401' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 6
      AND rs.end_period = 7
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E001' LIMIT 1),
    7, 8, 10, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E001' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 8
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.C101' LIMIT 1),
    2, 1, 3, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.C101' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '02', '841110 - Group 02 - DCT1241',
       (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11742@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       100, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '02'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1),
    2, 6, 7, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 6
      AND rs.end_period = 7
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1),
    2, 8, 10, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 8
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E304' LIMIT 1),
    7, 1, 3, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E304' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '03', '841110 - Group 03 - DCT1243',
       (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '20766@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       80, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '03'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1),
    4, 1, 3, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1),
    4, 4, 5, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.B107' LIMIT 1),
    6, 1, 3, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.B107' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '04', '841110 - Group 04 - DCT1244',
       (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '20766@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       120, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '04'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1),
    4, 6, 8, 4, 15, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 4
      AND rs.end_week = 15
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1),
    4, 9, 10, 4, 15, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 4
      AND rs.end_week = 15
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E201' LIMIT 1),
    7, 6, 8, 3, 15, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E201' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 3
      AND rs.end_week = 15
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '05', '841110 - Group 05 - DCT1245',
       (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '20766@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       80, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '05'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.A201' LIMIT 1),
    5, 8, 10, 3, 14, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.A201' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 8
      AND rs.end_period = 10
      AND rs.start_week = 3
      AND rs.end_week = 14
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E203' LIMIT 1),
    7, 4, 5, 3, 14, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E203' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 3
      AND rs.end_week = 14
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E203' LIMIT 1),
    7, 1, 3, 3, 15, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E203' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 3
      AND rs.end_week = 15
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '06', '841110 - Group 06 - DKP1241',
       (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11610@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       100, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '06'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1),
    3, 4, 5, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1),
    3, 6, 8, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E205' LIMIT 1),
    7, 6, 8, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E205' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '07', '841110 - Group 07 - DNT1241',
       (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11610@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '07'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '07' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1),
    7, 4, 5, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '07' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '07' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1),
    7, 9, 10, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '07' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '07' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E205' LIMIT 1),
    3, 1, 3, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841110' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '07' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E205' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841113 - Group 01 - DCT1231',
       (SELECT c.id FROM courses c WHERE c.course_code = '841113' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11636@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841113' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841113' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1),
    2, 6, 8, 11, 20, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841113' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 11
      AND rs.end_week = 20
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841113' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.D401' LIMIT 1),
    4, 9, 10, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841113' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.D401' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '02', '841113 - Group 02 - DCT1232',
       (SELECT c.id FROM courses c WHERE c.course_code = '841113' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11636@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841113' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '02'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841113' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A303' LIMIT 1),
    2, 3, 5, 11, 20, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841113' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A303' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 11
      AND rs.end_week = 20
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841113' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1),
    5, 4, 5, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841113' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '03', '841113 - Group 03 - DKP1231',
       (SELECT c.id FROM courses c WHERE c.course_code = '841113' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11636@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841113' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '03'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841113' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A303' LIMIT 1),
    4, 3, 5, 11, 20, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841113' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A303' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 11
      AND rs.end_week = 20
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841113' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1),
    5, 1, 2, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841113' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841303 - Group 01 - DCT1251',
       (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11383@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       50, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E501' LIMIT 1),
    5, 1, 3, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E501' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E501' LIMIT 1),
    5, 4, 5, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E501' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A209' LIMIT 1),
    3, 6, 8, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A209' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '02', '841303 - Group 02 - DCT1252',
       (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10631@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       50, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '02'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A109' LIMIT 1),
    2, 1, 3, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A109' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A109' LIMIT 1),
    2, 4, 5, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A109' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A109' LIMIT 1),
    6, 6, 8, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A109' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '03', '841303 - Group 03 - DCT1253',
       (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10063@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       50, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '03'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E504' LIMIT 1),
    4, 1, 2, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E504' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A108' LIMIT 1),
    5, 3, 5, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A108' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A209' LIMIT 1),
    4, 3, 5, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A209' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '04', '841303 - Group 04 - DCT1254',
       (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11544@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       50, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '04'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E103' LIMIT 1),
    3, 6, 7, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E103' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 6
      AND rs.end_period = 7
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E501' LIMIT 1),
    3, 8, 10, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E501' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 8
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.D401' LIMIT 1),
    2, 8, 10, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.D401' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 8
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '05', '841303 - Group 05 - DCT1255',
       (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11544@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       50, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '05'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E601' LIMIT 1),
    2, 1, 3, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E601' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.B003' LIMIT 1),
    2, 4, 5, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.B003' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A209' LIMIT 1),
    3, 3, 5, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A209' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '06', '841303 - Group 06 - DKP1251',
       (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11798@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       50, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '06'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A109' LIMIT 1),
    3, 6, 8, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A109' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A109' LIMIT 1),
    3, 9, 10, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A109' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A209' LIMIT 1),
    2, 3, 5, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A209' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '07', '841303 - Group 07 - DKP1252',
       (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10615@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       50, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '07'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '07' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E103' LIMIT 1),
    4, 1, 2, 1, 14, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '07' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E103' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 1
      AND rs.end_week = 14
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '07' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E103' LIMIT 1),
    5, 1, 2, 1, 14, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '07' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E103' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 1
      AND rs.end_week = 14
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '07' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.D401' LIMIT 1),
    5, 3, 5, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '07' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.D401' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '08', '841303 - Group 08 - DNT1251',
       (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11383@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       50, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '08'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '08' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E103' LIMIT 1),
    5, 6, 7, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '08' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E103' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 6
      AND rs.end_period = 7
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '08' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E103' LIMIT 1),
    5, 8, 10, 1, 12, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '08' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E103' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 8
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 12
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '08' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.D401' LIMIT 1),
    4, 6, 8, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841303' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '08' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.D401' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841404 - Group 01 - DCT1251',
       (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11364@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       50, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E103' LIMIT 1),
    6, 3, 5, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E103' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.D401' LIMIT 1),
    2, 3, 5, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.D401' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '02', '841404 - Group 02 - DCT1252',
       (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11798@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       50, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '02'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A210' LIMIT 1),
    3, 3, 5, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A210' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A109' LIMIT 1),
    2, 6, 8, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A109' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '03', '841404 - Group 03 - DCT1253',
       (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11562@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       50, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '03'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.B103' LIMIT 1),
    3, 8, 10, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.B103' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 8
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A210' LIMIT 1),
    6, 3, 5, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A210' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '04', '841404 - Group 04 - DCT1254',
       (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11562@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       50, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '04'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E504' LIMIT 1),
    2, 1, 3, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E504' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A209' LIMIT 1),
    4, 6, 8, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A209' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '05', '841404 - Group 05 - DCT1255',
       (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10094@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       50, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '05'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.B003' LIMIT 1),
    4, 8, 10, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.B003' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 8
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A209' LIMIT 1),
    5, 6, 8, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A209' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '06', '841404 - Group 06 - DKP1251',
       (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10094@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       50, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '06'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E103' LIMIT 1),
    2, 6, 8, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E103' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1),
    6, 1, 3, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841404' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '06' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841407 - Group 01 - DCT1233',
       (SELECT c.id FROM courses c WHERE c.course_code = '841407' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11363@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       80, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841407' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841407' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1),
    4, 6, 7, 11, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841407' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 6
      AND rs.end_period = 7
      AND rs.start_week = 11
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841407' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1),
    6, 1, 2, 11, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841407' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 11
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841407' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A103' LIMIT 1),
    5, 6, 8, 11, 20, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841407' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A103' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 11
      AND rs.end_week = 20
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '02', '841407 - Group 02 - DKP1231',
       (SELECT c.id FROM courses c WHERE c.course_code = '841407' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11363@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       100, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841407' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '02'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841407' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A102' LIMIT 1),
    3, 6, 7, 11, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841407' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A102' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 6
      AND rs.end_period = 7
      AND rs.start_week = 11
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841407' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1),
    6, 3, 4, 11, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841407' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 3
      AND rs.end_period = 4
      AND rs.start_week = 11
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841407' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A102' LIMIT 1),
    3, 8, 10, 11, 20, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841407' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A102' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 8
      AND rs.end_period = 10
      AND rs.start_week = 11
      AND rs.end_week = 20
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841408 - Group 01 - DKP1231',
       (SELECT c.id FROM courses c WHERE c.course_code = '841408' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11636@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841408' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841408' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A303' LIMIT 1),
    3, 1, 2, 11, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841408' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A303' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 11
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841408' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A303' LIMIT 1),
    4, 6, 7, 11, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841408' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A303' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 6
      AND rs.end_period = 7
      AND rs.start_week = 11
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841408' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1),
    3, 3, 5, 11, 20, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841408' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 11
      AND rs.end_week = 20
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841410 - Group 01 - DCT1234,DKP1231',
       (SELECT c.id FROM courses c WHERE c.course_code = '841410' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10944@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841410' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841410' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E304' LIMIT 1),
    5, 6, 8, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841410' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E304' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841410' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E304' LIMIT 1),
    5, 9, 10, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841410' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E304' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841413 - Group 01 - DCT1233',
       (SELECT c.id FROM courses c WHERE c.course_code = '841413' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11556@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841413' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841413' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A303' LIMIT 1),
    3, 3, 4, 11, 20, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841413' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A303' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 3
      AND rs.end_period = 4
      AND rs.start_week = 11
      AND rs.end_week = 20
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841413' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E304' LIMIT 1),
    6, 4, 5, 11, 20, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841413' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E304' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 11
      AND rs.end_week = 20
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841413' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1),
    3, 1, 2, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841413' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841414 - Group 01 - DCT1231',
       (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10943@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       130, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.A202' LIMIT 1),
    2, 1, 2, 4, 17, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.A202' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 4
      AND rs.end_week = 17
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.B101' LIMIT 1),
    3, 4, 5, 4, 17, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.B101' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 4
      AND rs.end_week = 17
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '02', '841414 - Group 02 - DCT1232',
       (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10943@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       120, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '02'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.A101' LIMIT 1),
    3, 9, 10, 4, 17, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.A101' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 4
      AND rs.end_week = 17
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.A202' LIMIT 1),
    4, 4, 5, 4, 17, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.A202' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 4
      AND rs.end_week = 17
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '03', '841414 - Group 03 - DCT1233',
       (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10943@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       110, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '03'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.B003' LIMIT 1),
    2, 9, 10, 4, 17, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.B003' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 4
      AND rs.end_week = 17
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C004' LIMIT 1),
    5, 4, 5, 4, 17, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C004' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 4
      AND rs.end_week = 17
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '04', '841414 - Group 04 - DCT1234',
       (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10943@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       120, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '04'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.A202' LIMIT 1),
    3, 1, 2, 4, 17, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.A202' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 4
      AND rs.end_week = 17
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.B003' LIMIT 1),
    4, 6, 7, 4, 17, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.B003' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 6
      AND rs.end_period = 7
      AND rs.start_week = 4
      AND rs.end_week = 17
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '05', '841414 - Group 05 - DCT1235',
       (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10943@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       120, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '05'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.A202' LIMIT 1),
    4, 9, 10, 4, 17, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.A202' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 4
      AND rs.end_week = 17
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.A202' LIMIT 1),
    5, 9, 10, 4, 17, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841414' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '05' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.A202' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 4
      AND rs.end_week = 17
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841415 - Group 01 - DCT1241',
       (SELECT c.id FROM courses c WHERE c.course_code = '841415' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11657@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       140, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841415' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841415' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.C103' LIMIT 1),
    3, 6, 8, 1, 13, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841415' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.C103' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 1
      AND rs.end_week = 13
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '02', '841415 - Group 02 - DKP1241',
       (SELECT c.id FROM courses c WHERE c.course_code = '841415' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11657@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       100, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841415' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '02'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841415' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A208' LIMIT 1),
    4, 3, 5, 1, 15, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841415' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A208' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 15
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841423 - Group 01 - DCT1231',
       (SELECT c.id FROM courses c WHERE c.course_code = '841423' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11636@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841423' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841423' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1),
    2, 9, 10, 11, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841423' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 11
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841423' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A303' LIMIT 1),
    3, 9, 10, 11, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841423' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A303' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 11
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841423' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.D401' LIMIT 1),
    3, 6, 7, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841423' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.D401' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 6
      AND rs.end_period = 7
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '02', '841423 - Group 02 - DCT1232',
       (SELECT c.id FROM courses c WHERE c.course_code = '841423' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11636@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841423' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '02'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841423' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1),
    7, 1, 3, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841423' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841423' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.D401' LIMIT 1),
    2, 1, 2, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841423' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.D401' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841434 - Group 01 - DKP1231',
       (SELECT c.id FROM courses c WHERE c.course_code = '841434' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10875@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       120, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841434' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841434' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1),
    2, 3, 5, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841434' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841434' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1),
    2, 1, 2, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841434' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841438 - Group 01 - DCT1234',
       (SELECT c.id FROM courses c WHERE c.course_code = '841438' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10944@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       70, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841438' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841438' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E103' LIMIT 1),
    6, 6, 8, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841438' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E103' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841438' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E103' LIMIT 1),
    6, 9, 10, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841438' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E103' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841439 - Group 01 - DCT1224',
       (SELECT c.id FROM courses c WHERE c.course_code = '841439' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11377@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       40, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841439' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841439' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C105' LIMIT 1),
    5, 6, 8, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841439' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C105' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841439' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C105' LIMIT 1),
    5, 9, 10, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841439' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C105' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841440 - Group 01 - DCT1234',
       (SELECT c.id FROM courses c WHERE c.course_code = '841440' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11364@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841440' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841440' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1),
    5, 1, 2, 11, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841440' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 11
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841440' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E201' LIMIT 1),
    7, 1, 2, 11, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841440' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E201' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 11
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841440' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1),
    5, 3, 5, 11, 20, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841440' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 11
      AND rs.end_week = 20
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841442 - Group 01 - DCT1224',
       (SELECT c.id FROM courses c WHERE c.course_code = '841442' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11137@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       40, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841442' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841442' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C101' LIMIT 1),
    6, 1, 3, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841442' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C101' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841446 - Group 01 - DCT1235',
       (SELECT c.id FROM courses c WHERE c.course_code = '841446' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11381@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841446' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841446' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E001' LIMIT 1),
    3, 1, 3, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841446' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E001' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841446' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1),
    2, 9, 10, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841446' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841447 - Group 01 - DCT1235',
       (SELECT c.id FROM courses c WHERE c.course_code = '841447' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11675@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841447' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841447' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1),
    4, 1, 2, 11, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841447' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 11
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841447' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A303' LIMIT 1),
    5, 3, 4, 11, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841447' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A303' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 3
      AND rs.end_period = 4
      AND rs.start_week = 11
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841447' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1),
    4, 3, 5, 11, 20, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841447' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 11
      AND rs.end_week = 20
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '02', '841447 - Group 02 - DCT1236',
       (SELECT c.id FROM courses c WHERE c.course_code = '841447' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11675@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841447' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '02'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841447' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1),
    3, 6, 7, 11, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841447' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 6
      AND rs.end_period = 7
      AND rs.start_week = 11
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841447' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A303' LIMIT 1),
    5, 1, 2, 11, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841447' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A303' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 11
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841447' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1),
    3, 8, 10, 11, 20, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841447' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 8
      AND rs.end_period = 10
      AND rs.start_week = 11
      AND rs.end_week = 20
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841448 - Group 01 - DNT1241',
       (SELECT c.id FROM courses c WHERE c.course_code = '841448' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11453@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841448' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841448' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C004' LIMIT 1),
    6, 1, 2, 3, 14, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841448' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C004' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 3
      AND rs.end_week = 14
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841448' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A411' LIMIT 1),
    7, 1, 3, 3, 14, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841448' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A411' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 3
      AND rs.end_week = 14
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841448' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1),
    7, 6, 8, 3, 15, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841448' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB403' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 3
      AND rs.end_week = 15
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841456 - Group 01 - DCT1235',
       (SELECT c.id FROM courses c WHERE c.course_code = '841456' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '20766@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841456' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841456' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E503' LIMIT 1),
    7, 9, 11, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841456' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E503' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 9
      AND rs.end_period = 11
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841456' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1),
    6, 4, 5, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841456' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.HB406' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841462 - Group 01 - DKP1221',
       (SELECT c.id FROM courses c WHERE c.course_code = '841462' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10601@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841462' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841462' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1),
    2, 1, 2, 11, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841462' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 11
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841462' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1),
    3, 1, 2, 11, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841462' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 11
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841462' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1),
    2, 3, 5, 11, 20, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841462' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A308' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 11
      AND rs.end_week = 20
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841463 - Group 01 - DCT1221',
       (SELECT c.id FROM courses c WHERE c.course_code = '841463' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11541@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       120, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841463' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841463' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.B108' LIMIT 1),
    2, 3, 5, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841463' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.B108' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841463' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E304' LIMIT 1),
    2, 1, 2, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841463' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E304' LIMIT 1)
      AND rs.day_of_week = 2
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '02', '841463 - Group 02 - DCT1222',
       (SELECT c.id FROM courses c WHERE c.course_code = '841463' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11541@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       120, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841463' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '02'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841463' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1),
    4, 1, 3, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841463' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841463' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1),
    4, 4, 5, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841463' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1)
      AND rs.day_of_week = 4
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '03', '841463 - Group 03 - DCT1223',
       (SELECT c.id FROM courses c WHERE c.course_code = '841463' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11541@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       120, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841463' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '03'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841463' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1),
    6, 1, 3, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841463' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841463' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1),
    6, 4, 5, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841463' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841464 - Group 01 - DCT1242',
       (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10220@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E205' LIMIT 1),
    7, 1, 2, 1, 15, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E205' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 1
      AND rs.end_period = 2
      AND rs.start_week = 1
      AND rs.end_week = 15
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E205' LIMIT 1),
    7, 3, 5, 1, 15, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E205' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 3
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 15
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '02', '841464 - Group 02 - DCT1241',
       (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10991@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '02'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E503' LIMIT 1),
    6, 1, 3, 1, 15, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E503' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 1
      AND rs.end_week = 15
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E503' LIMIT 1),
    6, 4, 5, 1, 15, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E503' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 15
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '03', '841464 - Group 03 - DKP1241',
       (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10991@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '03'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E503' LIMIT 1),
    5, 6, 7, 1, 15, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E503' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 6
      AND rs.end_period = 7
      AND rs.start_week = 1
      AND rs.end_week = 15
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E503' LIMIT 1),
    5, 8, 10, 1, 15, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E503' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 8
      AND rs.end_period = 10
      AND rs.start_week = 1
      AND rs.end_week = 15
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '04', '841464 - Group 04 - DCT1243',
       (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10991@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '04'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E503' LIMIT 1),
    7, 1, 3, 1, 15, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E503' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 1
      AND rs.end_week = 15
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E503' LIMIT 1),
    7, 4, 5, 1, 15, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841464' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '04' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E503' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 1
      AND rs.end_week = 15
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841476 - Group 01 - DCT1221',
       (SELECT c.id FROM courses c WHERE c.course_code = '841476' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10631@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       80, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841476' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841476' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A412' LIMIT 1),
    7, 1, 3, 4, 18, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841476' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A412' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 1
      AND rs.end_period = 3
      AND rs.start_week = 4
      AND rs.end_week = 18
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841476' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A412' LIMIT 1),
    7, 4, 5, 4, 18, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841476' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.A412' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 4
      AND rs.end_period = 5
      AND rs.start_week = 4
      AND rs.end_week = 18
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '02', '841476 - Group 02 - DCT1222',
       (SELECT c.id FROM courses c WHERE c.course_code = '841476' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11383@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       80, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841476' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '02'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841476' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C102' LIMIT 1),
    3, 6, 7, 4, 18, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841476' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C102' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 6
      AND rs.end_period = 7
      AND rs.start_week = 4
      AND rs.end_week = 18
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841476' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C202' LIMIT 1),
    3, 8, 10, 4, 18, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841476' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C202' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 8
      AND rs.end_period = 10
      AND rs.start_week = 4
      AND rs.end_week = 18
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '03', '841476 - Group 03 - DKP1221',
       (SELECT c.id FROM courses c WHERE c.course_code = '841476' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10601@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       80, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841476' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '03'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841476' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C105' LIMIT 1),
    3, 6, 7, 4, 18, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841476' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C105' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 6
      AND rs.end_period = 7
      AND rs.start_week = 4
      AND rs.end_week = 18
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841476' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C105' LIMIT 1),
    3, 8, 10, 4, 18, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841476' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '03' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.C105' LIMIT 1)
      AND rs.day_of_week = 3
      AND rs.start_period = 8
      AND rs.end_period = 10
      AND rs.start_week = 4
      AND rs.end_week = 18
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841480 - Group 01 - DCT1231',
       (SELECT c.id FROM courses c WHERE c.course_code = '841480' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10011@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841480' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841480' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1),
    6, 6, 8, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841480' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841480' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1),
    6, 9, 10, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841480' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '02', '841480 - Group 02 - DCT1232',
       (SELECT c.id FROM courses c WHERE c.course_code = '841480' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10011@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841480' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '02'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841480' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1),
    5, 6, 8, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841480' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841480' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1),
    5, 9, 10, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841480' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E403' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841481 - Group 01 - DCT1231',
       (SELECT c.id FROM courses c WHERE c.course_code = '841481' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11426@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841481' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841481' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E603' LIMIT 1),
    5, 6, 8, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841481' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E603' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841481' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1),
    5, 9, 10, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841481' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1)
      AND rs.day_of_week = 5
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '02', '841481 - Group 02 - DKP1231',
       (SELECT c.id FROM courses c WHERE c.course_code = '841481' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11426@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       90, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841481' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '02'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841481' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E603' LIMIT 1),
    6, 6, 8, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841481' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E603' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841481' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1),
    6, 9, 10, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841481' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E402' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 9
      AND rs.end_period = 10
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01', '841482 - Group 01 - DCT1222',
       (SELECT c.id FROM courses c WHERE c.course_code = '841482' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '10600@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       120, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841482' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '01'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841482' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E502' LIMIT 1),
    7, 6, 8, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841482' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '01' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'C.E502' LIMIT 1)
      AND rs.day_of_week = 7
      AND rs.start_period = 6
      AND rs.end_period = 8
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '02', '841482 - Group 02 - DKP1221',
       (SELECT c.id FROM courses c WHERE c.course_code = '841482' LIMIT 1),
       (SELECT l.id FROM lecturers l WHERE l.email = '11453@sgu.local' LIMIT 1),
       (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1),
       120, 'OPEN', NOW(), b'0'
WHERE NOT EXISTS (
    SELECT 1 FROM course_sections cs
    WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841482' LIMIT 1)
      AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1)
      AND cs.section_code = '02'
);

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT
    (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841482' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1),
    (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.A202' LIMIT 1),
    6, 9, 11, 4, 21, b'0', NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM recurring_schedules rs
    WHERE rs.section_id = (SELECT cs.id FROM course_sections cs WHERE cs.course_id = (SELECT c.id FROM courses c WHERE c.course_code = '841482' LIMIT 1) AND cs.semester_id = (SELECT sem.id FROM semesters sem WHERE sem.semester_number = 2 AND sem.academic_year = '2025-2026' LIMIT 1) AND cs.section_code = '02' LIMIT 1)
      AND rs.room_id = (SELECT cr.id FROM classrooms cr WHERE cr.room_name = '1.A202' LIMIT 1)
      AND rs.day_of_week = 6
      AND rs.start_period = 9
      AND rs.end_period = 11
      AND rs.start_week = 4
      AND rs.end_week = 21
      AND rs.deleted = b'0'
);
