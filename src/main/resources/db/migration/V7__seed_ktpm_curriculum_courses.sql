-- Seed KTPM curriculum courses inferred from the provided curriculum screenshots.
-- This migration only inserts course catalog entries into `courses`.
-- It does not create course sections, schedules, grade components, or registration periods.
-- A few course names are partially inferred where the screenshot text is truncated.

INSERT INTO faculties (faculty_name, faculty_code, deleted)
SELECT 'Faculty of Information Technology', 'FIT', b'0'
WHERE NOT EXISTS (SELECT 1 FROM faculties WHERE faculty_code = 'FIT');

-- I. Khoi kien thuc giao duc dai cuong
INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '861301', 'Triet hoc Mac - Lenin', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '861301');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '861302', 'Kinh te chinh tri Mac - Lenin', 2,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '861301' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '861302');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '861303', 'Chu nghia xa hoi khoa hoc', 2,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '861302' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '861303');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '861304', 'Tu tuong Ho Chi Minh', 2,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '861303' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '861304');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '861305', 'Lich su Dang Cong san Viet Nam', 2,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '861303' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '861305');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '866401', 'Tieng Anh I', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '866401');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '866402', 'Tieng Anh II', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '866401' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '866402');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '866403', 'Tieng Anh III', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '866402' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '866403');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '865006', 'Phap luat dai cuong', 2,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '865006');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '864508', 'Xac suat thong ke', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '864508');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '864005', 'Giai tich 1', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '864005');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '864006', 'Giai tich 2', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '864005' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '864006');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '864007', 'Dai so tuyen tinh', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '864007');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '862101', 'Giao duc the chat (I)', 1,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '862101');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '862406', 'Giao duc quoc phong va an ninh II', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '862406');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '862407', 'Giao duc quoc phong va an ninh III', 2,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '862406' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '862407');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '862408', 'Giao duc quoc phong va an ninh IV', 2,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '862407' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '862408');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '862409', 'Giao duc quoc phong va an ninh IV', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '862408' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '862409');

-- Giao duc the chat tu chon
INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT 'BOBA11', 'Bong ban 1', 1,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = 'BOBA11');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT 'BOBA12', 'Bong ban 2', 1,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = 'BOBA11' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = 'BOBA12');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT 'BODA11', 'Bong da 1', 1,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = 'BODA11');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT 'BODA12', 'Bong da 2', 1,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = 'BODA11' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = 'BODA12');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT 'BOCH11', 'Bong chuyen 1', 1,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = 'BOCH11');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT 'BOCH12', 'Bong chuyen 2', 1,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = 'BOCH11' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = 'BOCH12');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT 'BORO11', 'Bong ro 1', 1,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = 'BORO11');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT 'BORO12', 'Bong ro 2', 1,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = 'BORO11' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = 'BORO12');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT 'CALO11', 'Cau long 1', 1,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = 'CALO11');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT 'CALO12', 'Cau long 2', 1,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = 'CALO11' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = 'CALO12');

-- II. Khoi kien thuc giao duc chuyen nghiep
INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841302', 'Co so lap trinh', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841302');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841303', 'Ky thuat lap trinh', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '841302' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841303');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841021', 'Kien truc may tinh', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841021');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841022', 'He dieu hanh', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '841021' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841022');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841403', 'Cau truc roi rac', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841403');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841108', 'Cau truc du lieu va giai thuat', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '841302' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841108');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841404', 'Mang may tinh', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841404');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841044', 'Phuong phap lap trinh huong doi tuong', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '841302' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841044');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841109', 'Co so du lieu', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841109');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841110', 'Co so tri tue nhan tao', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841110');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841408', 'Kiem thu phan mem', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '841047' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841408');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841047', 'Cong nghe phan mem', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841047');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841048', 'Phan tich thiet ke HTTT', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '841109' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841048');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841111', 'Phan tich thiet ke huong doi tuong', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '841044' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841111');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841501', 'Nhap mon cong nghe thong tin va truyen thong', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841501');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841598', 'Thuc tap tot nghiep', 8,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841598');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841099', 'Khoa luan tot nghiep', 10,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841099');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841482', 'Seminar chuyen de', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841482');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841072', 'Cac cong nghe lap trinh hien dai', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841072');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841476', 'Do an chuyen nganh', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841476');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841407', 'Cac he quan tri co so du lieu', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '841109' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841407');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841461', 'Nhap mon phat trien ung dung tren thiet bi di dong', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841461');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841058', 'He dieu hanh ma nguon mo', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841058');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841324', 'Phuong phap luan nghien cuu khoa hoc', 2,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841324');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841415', 'Luat phap va CNTT', 2,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841415');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841503', 'Lap trinh Web va ung dung', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '841302' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841503');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841422', 'Ngon ngu lap trinh Python', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841422');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841423', 'Ngon ngu lap trinh C#', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '841302' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841423');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841107', 'Ngon ngu lap trinh Java', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '841044' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841107');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841428', 'Nhap mon an toan thong tin', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841428');

-- Chuyen nganh 1: Lap trinh Web va Ung dung
INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841464', 'Lap trinh Web va ung dung nang cao', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '841503' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841464');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841481', 'Thiet ke giao dien', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841481');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841434', 'Thuong mai dien tu va ung dung', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '841503' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841434');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841479', 'Kien truc phan mem', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841479');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841463', 'Phat trien ung dung tren thiet bi di dong nang cao', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '841461' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841463');

-- Chuyen nganh 2: Phat trien ung dung di dong / tu chon
INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841477', 'Lap trinh Game', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841477');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841417', 'My thuat ung dung trong CNTT', 2,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841417');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841468', 'Chuyen de J2EE', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '841107' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841468');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841320', 'Cong nghe Internet of Things', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841320');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841113', 'Phat trien phan mem ma nguon mo', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841113');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841431', 'Quan ly du an phan mem', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841431');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841467', 'Cong nghe .NET', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '841044' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841467');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841470', 'Tuong tac nguoi may', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841470');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841411', 'Quan tri mang', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841411');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841410', 'An ninh mang may tinh', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841410');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841120', 'An toan va bao mat du lieu trong HTTP', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841120');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841426', 'Nguyen ly va phuong phap lap trinh', 4,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '841044' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841426');

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT '841068', 'He thong thong tin doanh nghiep', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = '841048' LIMIT 1),
       'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM courses c WHERE c.course_code = '841068');
