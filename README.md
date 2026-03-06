# 📚 EduMS — Education Management System

> Backend REST API cho hệ thống quản lý giáo dục, xây dựng bằng **Spring Boot 3**  
> Nhóm **G5C** | Java 21 | MySQL 8 | March 2026

---

## 📋 Mục lục

1. [Project Overview](#1-project-overview)
2. [Project Structure](#2-project-structure)
3. [Setup Environment](#3-setup-environment)
4. [Setup Project](#4-setup-project)
5. [API Example](#5-api-example)
6. [Coding Convention](#6-coding-convention)
7. [Git Workflow](#7-git-workflow)
8. [Commit Convention](#8-commit-convention)

---

## 1. Project Overview

EduMS là hệ thống quản lý giáo dục dành cho trường đại học, hỗ trợ các nghiệp vụ:

| Module | Mô tả |
|---|---|
| 👤 **Account / Role** | Quản lý tài khoản, phân quyền theo vai trò |
| 🎓 **Student** | Thông tin sinh viên, lớp hành chính, chuyên ngành |
| 👨‍🏫 **Lecturer** | Thông tin giảng viên, học hàm học vị |
| 👨‍👩‍👦 **Guardian** | Thông tin phụ huynh / người giám hộ |
| 🏛️ **Faculty / Major / Specialization** | Khoa → Ngành → Chuyên ngành |
| 🎒 **Cohort / Administrative Class** | Khoá học, lớp hành chính |
| 📘 **Course / CourseSection** | Môn học và lớp học phần theo học kỳ |
| 🗓️ **Schedule** | Thời khoá biểu định kỳ, buổi học thực tế |
| ✅ **Attendance** | Điểm danh theo buổi học |
| 📝 **Grade** | Thành phần điểm, bảng điểm, kết quả học tập |
| 📋 **Admission** | Hồ sơ tuyển sinh, điểm chuẩn |

### Tech Stack

| Thư viện | Phiên bản | Mục đích |
|---|---|---|
| **Spring Boot** | 3.4.3 | Framework chính |
| **Spring Data JPA** | 3.4.3 | ORM — giao tiếp DB |
| **Spring Validation** | 3.4.3 | Validate request DTO |
| **Spring Actuator** | 3.4.3 | Health check, monitoring |
| **Spring DevTools** | 3.4.3 | Hot reload khi dev |
| **MySQL Connector** | 9.x | Driver kết nối MySQL 8 |
| **Hibernate** | 6.6.x | JPA Implementation |
| **Lombok** | latest | Giảm boilerplate code |
| **MapStruct** | 1.6.3 | Mapping Entity ↔ DTO |
| **SpringDoc OpenAPI** | 2.8.5 | Swagger UI tự động |
| **spring-dotenv** | 4.0.0 | Đọc biến từ file `.env` |

---

## 2. Project Structure

```
EduMS/
├── src/
│   └── main/
│       ├── java/com/G5C/EduMS/
│       │   │
│       │   ├── EduMsApplication.java          # Entry point
│       │   │
│       │   ├── config/                        # Cấu hình Spring
│       │   │   └── OpenApiConfig.java         # Cấu hình Swagger UI
│       │   │
│       │   ├── controller/                    # Tầng nhận request HTTP
│       │   │   └── FacultyController.java     # ← Xem làm mẫu
│       │   │── enums/                     # Enum dùng trong Entity
│       │   │       ├── AccountStatus.java     # ACTIVE, INACTIVE, LOCKED
│       │   │       ├── StudentStatus.java     # ACTIVE, SUSPENDED, GRADUATED, DROPPED_OUT
│       │   │       ├── CohortStatus.java      # ACTIVE, GRADUATED
│       │   │       ├── CourseStatus.java      # ACTIVE, INACTIVE
│       │   │       ├── CourseSectionStatus.java # OPEN, CLOSED, ONGOING, CANCELLED, FINISHED
│       │   │       ├── RegistrationStatus.java  # PENDING, CONFIRMED, CANCELLED, DROPPED
│       │   │       ├── SessionStatus.java     # NORMAL, CANCELLED, RESCHEDULED
│       │   │       ├── AttendanceStatus.java  # PRESENT, ABSENT, LATE, EXCUSED
│       │   │       ├── ApplicationStatus.java # PENDING, APPROVED, REJECTED
│       │   │       └── GradeStatus.java       # DRAFT, PUBLISHED, LOCKED
│       │   │
│       │   ├── service/                       # Tầng xử lý business logic
│       │   │   ├── FacultyService.java        # Interface định nghĩa contract
│       │   │   └── impl/
│       │   │       └── FacultyServiceImpl.java # Implementation
│       │   │
│       │   ├── repository/                    # Tầng giao tiếp với DB (JPA)
│       │   │   └── FacultyRepository.java
│       │   │
│       │   ├── model/                         # JPA Entity — ánh xạ bảng DB
│       │   │   ├── Account.java
│       │   │   ├── Student.java
│       │   │   ├── Lecturer.java
│       │   │   ├── Guardian.java
│       │   │   ├── Faculty.java
│       │   │   ├── Major.java
│       │   │   ├── Specialization.java
│       │   │   ├── Cohort.java
│       │   │   ├── AdministrativeClass.java
│       │   │   ├── Course.java
│       │   │   ├── CourseSection.java
│       │   │   ├── Semester.java
│       │   │   ├── Classroom.java
│       │   │   ├── ClassSession.java
│       │   │   ├── RecurringSchedule.java
│       │   │   ├── CourseRegistration.java
│       │   │   ├── Attendance.java
│       │   │   ├── GradeReport.java
│       │   │   ├── GradeDetail.java
│       │   │   ├── GradeComponent.java
│       │   │   ├── AdmissionApplication.java
│       │   │   ├── BenchmarkScore.java
│       │   │   ├── Role.java
│       │   │   ├── RolePermission.java
│       │   │   
│       │   │
│       │   ├── dto/
│       │   │   ├── request/                   # DTO nhận dữ liệu từ client (input)
│       │   │   │   └── FacultyRequest.java
│       │   │   └── reponse/                   # DTO trả dữ liệu về client (output)
│       │   │       ├── ResponseData.java      # Wrapper bọc mọi response
│       │   │       └── FacultyResponse.java
│       │   │
│       │   ├── mapper/                        # MapStruct — convert Entity ↔ DTO
│       │   │   └── FacultyMapper.java
│       │   │
│       │   └── exception/                     # Xử lý lỗi tập trung
│       │       ├── BaseException.java         # Exception gốc
│       │       ├── NotFoundResourcesException.java  # 404
│       │       ├── ExistingResourcesException.java  # 409
│       │       ├── InvalidDataException.java        # 400
│       │       └── GlobalExceptionHandler.java      # @RestControllerAdvice
│       │
│       └── resources/
│           └── application.yml                # Cấu hình ứng dụng
│
├── .env                                       # Biến môi trường (KHÔNG commit)
├── .env.example                               # File mẫu biến môi trường
├── docker-compose.yml                         # Chạy MySQL + App bằng Docker
├── Dockerfile                                 # Build Docker image
└── pom.xml                                    # Maven dependencies
```

### Giải thích luồng xử lý request

```
Client Request
     │
     ▼
 Controller          ← Nhận request, validate input (@Valid), trả ResponseData<T>
     │
     ▼
  Service            ← Xử lý business logic, throw exception nếu có lỗi
     │
     ▼
 Repository          ← Query DB thông qua JPA/Hibernate
     │
     ▼
   Model             ← Entity ánh xạ với bảng DB
     │
     ▼
  Database (MySQL)
```

---

## 3. Setup Environment

### Yêu cầu bắt buộc

| Tool | Phiên bản | Ghi chú |
|---|---|---|
| **JDK** | 21+ | [Adoptium](https://adoptium.net/) — chọn JDK 21 LTS |
| **Maven** | 3.9+ | Hoặc dùng `mvnw` có sẵn trong project |
| **MySQL** | 8.0+ | Nếu không dùng Docker |
| **Git** | latest | |

### Tuỳ chọn (khuyên dùng)

| Tool | Ghi chú |
|---|---|
| **Docker Desktop** | Chạy MySQL không cần cài thủ công |
| **IntelliJ IDEA** | IDE tốt nhất cho Spring Boot |
| **DBeaver / TablePlus** | GUI quản lý MySQL |
| **Postman** | Test API ngoài Swagger |

### Kiểm tra môi trường

Mở terminal, chạy lần lượt:

```bash
java -version
# java version "21.x.x"

mvn -version
# Apache Maven 3.x.x

git --version
# git version 2.x.x

docker --version
# Docker version 24.x.x  (nếu dùng Docker)
```

### Cấu hình biến môi trường

Tạo file **`.env`** ở thư mục gốc (cùng cấp `pom.xml`):

```env
# ============================
# Database Configuration
# ============================
DATABASE_HOST=localhost
DATABASE_PORT=3306
DATABASE_NAME=edums
DATABASE_USERNAME=user
DATABASE_PASSWORD=your_password
```

> ⚠️ **Tuyệt đối không commit file `.env` lên Git!**  
> File `.env` đã được thêm vào `.gitignore`.  
> Tham khảo cấu trúc từ file `.env.example` nếu có.

---

## 4. Setup Project

### Cách 1 — Chạy Local (không cần Docker)

> Yêu cầu: MySQL đang chạy trên máy

**Bước 1** — Clone project
```bash
git clone <repository-url>
cd EduMS
```

**Bước 2** — Tạo file `.env` (xem mục 3)

**Bước 3** — Tạo database
```sql
CREATE DATABASE edums
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
```

**Bước 4** — Chạy ứng dụng
```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

**Bước 5** — Kiểm tra
```
✅ App running : http://localhost:8080
✅ Swagger UI  : http://localhost:8080/swagger-ui/index.html
✅ Health check: http://localhost:8080/actuator/health
```

---

### Cách 2 — Chạy bằng Docker Compose ✅ (khuyên dùng)

> Không cần cài MySQL. Docker tự tạo MySQL + App.

**Bước 1** — Clone project và tạo file `.env`

**Bước 2** — Build file JAR
```bash
# Windows
.\mvnw.cmd clean package -DskipTests

# Linux / macOS
./mvnw clean package -DskipTests
```

**Bước 3** — Khởi động toàn bộ hệ thống
```bash
docker-compose up -d
```

**Bước 4** — Kiểm tra trạng thái
```bash
# Xem các container đang chạy
docker-compose ps

# Xem log realtime
docker-compose logs -f edums
```

**Bước 5** — Truy cập
```
✅ Swagger UI: http://localhost:8080/swagger-ui/index.html
```

**Các lệnh Docker thường dùng**
```bash
# Dừng tất cả
docker-compose down

# Dừng và xoá toàn bộ data (reset DB)
docker-compose down -v

# Restart app (sau khi build lại)
docker-compose up -d --build edums

# Xem log MySQL
docker-compose logs -f mysql
```

---

### Lỗi thường gặp khi chạy

| Lỗi | Nguyên nhân | Cách xử lý |
|---|---|---|
| `Communications link failure` | MySQL chưa chạy | Khởi động MySQL hoặc `docker-compose up -d mysql` |
| `Access denied for user` | Sai username/password | Kiểm tra lại file `.env` |
| `Unknown database 'edums'` | Chưa tạo DB | Chạy lệnh `CREATE DATABASE edums` |
| `Port 8080 already in use` | Cổng bị chiếm | Đổi `server.port` trong `application.yml` hoặc tắt app khác |
| `MapStruct not generating` | Chưa compile | Chạy `mvnw clean compile` |

---

## 5. API Example

> Tham khảo **`Faculty`** — đây là API mẫu chuẩn cho toàn dự án.  
> Khi viết API mới, làm theo đúng thứ tự và cấu trúc này.

### Danh sách endpoint

| Method | Endpoint | Mô tả | Status Code |
|---|---|---|---|
| `GET` | `/api/v1/faculties` | Lấy tất cả khoa | 200 |
| `GET` | `/api/v1/faculties/{id}` | Lấy khoa theo ID | 200 |
| `POST` | `/api/v1/faculties` | Tạo khoa mới | 201 |
| `PUT` | `/api/v1/faculties/{id}` | Cập nhật khoa | 200 |
| `DELETE` | `/api/v1/faculties/{id}` | Xoá mềm khoa | 200 |

### Response format chuẩn

Mọi API đều bọc trong `ResponseData<T>`:

```json
// ✅ Success — GET /api/v1/faculties/1
{
    "status": 200,
    "code": "SUCCESS",
    "message": "Success",
    "data": {
        "id": 1,
        "facultyName": "Công nghệ thông tin"
    }
}

// ✅ Created — POST /api/v1/faculties
{
    "status": 201,
    "code": "SUCCESS",
    "message": "Faculty created successfully",
    "data": {
        "id": 2,
        "facultyName": "Kinh tế"
    }
}

// ❌ Not Found — GET /api/v1/faculties/99
{
    "status": 404,
    "code": "NOT_FOUND",
    "message": "Faculty not found with id: 99",
    "path": "/api/v1/faculties/99"
}

// ❌ Validation Error — POST với body rỗng
{
    "status": 400,
    "code": "VALIDATION_FAILED",
    "message": "Validation failed",
    "path": "/api/v1/faculties",
    "data": {
        "facultyName": "Faculty name is required"
    }
}

// ❌ Conflict — POST tên đã tồn tại
{
    "status": 409,
    "code": "ALREADY_EXISTS",
    "message": "Faculty already exists with name: Công nghệ thông tin",
    "path": "/api/v1/faculties"
}
```

### Hướng dẫn viết API mới (7 bước)

**Bước 1 — DTO Request** `dto/request/XxxRequest.java`
```java
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class XxxRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;
}
```

**Bước 2 — DTO Response** `dto/reponse/XxxResponse.java`
```java
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class XxxResponse {
    private Integer id;
    private String name;
}
```

**Bước 3 — Mapper** `mapper/XxxMapper.java`
```java
@Mapper(componentModel = "spring")
public interface XxxMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    // @Mapping(target = "relationList", ignore = true) — ignore các List<> quan hệ ngược
    XxxEntity toEntity(XxxRequest request);

    XxxResponse toResponse(XxxEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateEntity(XxxRequest request, @MappingTarget XxxEntity entity);
}
```

**Bước 4 — Repository** `repository/XxxRepository.java`
```java
@Repository
public interface XxxRepository extends JpaRepository<XxxEntity, Integer> {
    List<XxxEntity> findAllByDeletedFalse();
    Optional<XxxEntity> findByIdAndDeletedFalse(Integer id);
    boolean existsByNameAndDeletedFalse(String name);
}
```

**Bước 5 — Service Interface** `service/XxxService.java`
```java
public interface XxxService {
    List<XxxResponse> getAll();
    XxxResponse getById(Integer id);
    XxxResponse create(XxxRequest request);
    XxxResponse update(Integer id, XxxRequest request);
    void delete(Integer id);
}
```

**Bước 6 — Service Implementation** `service/impl/XxxServiceImpl.java`
```java
@Service
@RequiredArgsConstructor
public class XxxServiceImpl implements XxxService {

    private final XxxRepository xxxRepository;
    private final XxxMapper xxxMapper;

    @Override
    public List<XxxResponse> getAll() {
        return xxxRepository.findAllByDeletedFalse()
                .stream().map(xxxMapper::toResponse).toList();
    }

    @Override
    public XxxResponse getById(Integer id) {
        return xxxMapper.toResponse(
            xxxRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Xxx not found with id: " + id))
        );
    }

    @Override
    @Transactional
    public XxxResponse create(XxxRequest request) {
        if (xxxRepository.existsByNameAndDeletedFalse(request.getName()))
            throw new ExistingResourcesException("Xxx already exists: " + request.getName());
        return xxxMapper.toResponse(xxxRepository.save(xxxMapper.toEntity(request)));
    }

    @Override
    @Transactional
    public XxxResponse update(Integer id, XxxRequest request) {
        XxxEntity entity = xxxRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Xxx not found with id: " + id));
        xxxMapper.updateEntity(request, entity);
        return xxxMapper.toResponse(xxxRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        XxxEntity entity = xxxRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Xxx not found with id: " + id));
        entity.setDeleted(true);
        xxxRepository.save(entity);
    }
}
```

**Bước 7 — Controller** `controller/XxxController.java`
```java
@RestController
@RequestMapping("/api/v1/xxxs")
@RequiredArgsConstructor
@Tag(name = "Xxx", description = "APIs for managing xxx")
public class XxxController {

    private final XxxService xxxService;

    @GetMapping
    @Operation(summary = "Get all")
    public ResponseEntity<ResponseData<List<XxxResponse>>> getAll() {
        return ResponseEntity.ok(ResponseData.success(xxxService.getAll()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get by ID")
    public ResponseEntity<ResponseData<XxxResponse>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ResponseData.success(xxxService.getById(id)));
    }

    @PostMapping
    @Operation(summary = "Create new")
    public ResponseEntity<ResponseData<XxxResponse>> create(@Valid @RequestBody XxxRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseData.success("Created successfully", xxxService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update")
    public ResponseEntity<ResponseData<XxxResponse>> update(
            @PathVariable Integer id, @Valid @RequestBody XxxRequest request) {
        return ResponseEntity.ok(ResponseData.success("Updated successfully", xxxService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable Integer id) {
        xxxService.delete(id);
        return ResponseEntity.ok(ResponseData.success("Deleted successfully", null));
    }
}
```

---

## 6. Coding Convention

### Đặt tên

| Loại | Quy ước | Ví dụ |
|---|---|---|
| Class / Interface / Enum | `PascalCase` | `FacultyService`, `StudentStatus` |
| Method / Variable / Field | `camelCase` | `getFacultyById`, `facultyName` |
| Constant | `UPPER_SNAKE_CASE` | `MAX_PAGE_SIZE` |
| Package | `lowercase` | `com.g5c.edums.service` |
| API endpoint | `kebab-case` | `/api/v1/course-sections` |
| DB table / column | `snake_case` | `faculty_name`, `created_at` |

### Quy tắc tổ chức code

```
✅ Mỗi class chỉ làm 1 việc (Single Responsibility)
✅ Controller KHÔNG chứa business logic
✅ Service KHÔNG gọi trực tiếp Repository của module khác
✅ Luôn dùng Interface cho Service (FacultyService + FacultyServiceImpl)
✅ Dùng @Transactional cho các method thay đổi dữ liệu (create, update, delete)
✅ Soft delete — không xoá cứng, chỉ set deleted = true
✅ Mọi Entity có field: id, deleted
✅ Dùng Enum thay Integer cho các trường status
✅ Validate input bằng @Valid + annotation trong DTO Request
```

### Exception

```java
throw new NotFoundResourcesException("Faculty not found with id: " + id);  // 404
throw new ExistingResourcesException("Name already exists: " + name);       // 409
throw new InvalidDataException("Score must be between 0 and 10");           // 400
throw new BaseException(HttpStatus.FORBIDDEN, "ACCESS_DENIED", "...");      // custom
```

### Không được làm

```
❌ Trả về Entity trực tiếp từ Controller (luôn dùng DTO)
❌ Bắt Exception rỗng: catch(Exception e) {}
❌ Hardcode thông tin DB, password trong code
❌ Commit file .env, file chứa password lên Git
❌ Đặt tên biến vô nghĩa: a, b, x1, temp2
❌ Method dài hơn 50 dòng — tách nhỏ ra
```

---

## 7. Git Workflow

### Mô hình branch

```

main
 │   └─ Nhánh tích hợp chính. Tất cả feature merge vào đây.
 │      KHÔNG commit trực tiếp.
 │
feature/<ten-tinh-nang>
 │   └─ Phát triển tính năng mới
 │      Ví dụ: feature/student-api, feature/attendance-module
 │
fix/<ten-loi>
     └─ Sửa bug
        Ví dụ: fix/guardian-foreign-key, fix/grade-calculation
```

### Quy trình làm việc hàng ngày

```bash
# 1. Luôn bắt đầu từ develop mới nhất
git checkout main
git pull origin main

# 2. Tạo branch cho task của bạn
git checkout -b feature/ten-tinh-nang

# 3. Code & commit thường xuyên (ít nhất mỗi task nhỏ 1 commit)
git add .
git commit -m "feat: add student list API"

# 4. Trước khi push, pull develop mới nhất để tránh conflict
git pull origin develop --rebase

# 5. Push branch lên remote
git push origin feature/ten-tinh-nang

# 6. Tạo Pull Request trên GitHub/GitLab
#    - Title: rõ ràng, ngắn gọn
#    - Description: mô tả đã làm gì, test thế nào
#    - Assign reviewer: trưởng nhóm

# 7. Sau khi được approve → Merge vào develop → Xoá branch
```

### Quy tắc bắt buộc

```
⛔ KHÔNG push trực tiếp lên main 
⛔ KHÔNG merge Pull Request của chính mình (cần ít nhất 1 người review)
⛔ KHÔNG để branch tồn tại quá 3 ngày mà không có commit
✅ Xoá branch sau khi merge
✅ Resolve conflict trước khi tạo Pull Request
✅ Test kỹ trước khi tạo Pull Request
```

---

## 8. Commit Convention

Tuân theo chuẩn **[Conventional Commits](https://www.conventionalcommits.org/)**

### Cú pháp

```
<type>(<scope>): <mô tả ngắn>

[body - tuỳ chọn]
```

### Các type

| Type | Dùng khi | Ví dụ |
|---|---|---|
| `feat` | Thêm tính năng mới | `feat: add student CRUD API` |
| `fix` | Sửa bug | `fix: correct guardian_id foreign key` |
| `refactor` | Cải thiện code, không thay đổi chức năng | `refactor: simplify grade calculation logic` |
| `docs` | Cập nhật tài liệu | `docs: update README setup guide` |
| `style` | Format, thụt đầu dòng, không đổi logic | `style: format Faculty controller` |
| `chore` | Cập nhật config, dependencies | `chore: add mapstruct dependency` |
| `test` | Thêm / sửa test | `test: add faculty service unit test` |
| `perf` | Cải thiện hiệu năng | `perf: optimize student query with index` |

### Scope (tuỳ chọn — tên module)

```
feat(student): add search by student code
fix(grade): fix null pointer in grade calculation
refactor(auth): simplify token validation
```

### Ví dụ thực tế

```bash
# ✅ Đúng
git commit -m "feat: add faculty CRUD API"
git commit -m "feat(student): add filter by class and specialization"
git commit -m "fix: handle null guardian in student response"
git commit -m "refactor(grade): extract grade calculation to helper method"
git commit -m "docs: add API example section to README"
git commit -m "chore: upgrade spring boot to 3.4.3"

# ❌ Sai — không rõ nghĩa
git commit -m "update"
git commit -m "fix bug"
git commit -m "done"
git commit -m "wip"
git commit -m "asdasd"
```

### Quy tắc viết commit message

```
✅ Dùng tiếng Anh
✅ Chữ thường, không viết hoa chữ đầu (trừ danh từ riêng)
✅ Không dấu chấm (.) ở cuối
✅ Ngắn gọn, rõ nghĩa — đọc là hiểu ngay đã làm gì
✅ Mỗi commit chỉ làm 1 việc — không gộp nhiều thứ vào 1 commit
```

---

> 📅 Last updated: March 2026 — G5C Team
