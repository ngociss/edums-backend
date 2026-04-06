package com.G5C.EduMS;

import com.G5C.EduMS.common.enums.ApplicationStatus;
import com.G5C.EduMS.model.*;
import com.G5C.EduMS.repository.*;
import com.G5C.EduMS.service.impl.AdmissionOnboardingServiceImpl;
import com.G5C.EduMS.service.impl.MailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OnboardingServiceTest {

    @Mock private AdmissionPeriodRepository admissionPeriodRepository;
    @Mock private AdmissionApplicationRepository applicationRepository;
    @Mock private StudentRepository studentRepository;
    @Mock private CohortRepository cohortRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private LecturerRepository lecturerRepository;
    @Mock private AccountRepository accountRepository;
    @Mock private GuardianRepository guardianRepository;
    @Mock private AdministrativeClassRepository classRepository; // Hoặc administrativeClassRepository tùy tên bạn đặt
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private MailServiceImpl mailService;

    @InjectMocks
    private AdmissionOnboardingServiceImpl onboardingService;

    @Captor private ArgumentCaptor<List<Student>> studentListCaptor;
    @Captor private ArgumentCaptor<List<Guardian>> guardianListCaptor;
    @Captor private ArgumentCaptor<List<AdministrativeClass>> classListCaptor;

    private AdmissionPeriod mockPeriod;
    private Major mockMajor;
    private Role mockStudentRole, mockGuardianRole, mockLecturerRole;
    private Cohort mockCohort;
    private Lecturer mockLecturer;

    @BeforeEach
    void setUp() {
        mockPeriod = new AdmissionPeriod();
        mockPeriod.setId(1);
        mockPeriod.setStartTime(LocalDateTime.of(2026, 5, 1, 0, 0));

        mockMajor = new Major();
        mockMajor.setId(10);
        mockMajor.setMajorCode("IT");

        mockStudentRole = new Role(); mockStudentRole.setRoleName("STUDENT");
        mockGuardianRole = new Role(); mockGuardianRole.setRoleName("GUARDIAN");
        mockLecturerRole = new Role(); mockLecturerRole.setRoleName("LECTURER");

        mockCohort = new Cohort(); mockCohort.setCohortName("K26");
        mockLecturer = new Lecturer(); mockLecturer.setFullName("Dummy");
    }

    // =========================================================================
    // TEST CASE 1: Dữ liệu hợp lệ, chia lớp đúng (Happy Path)
    // =========================================================================
    @Test
    void testProcessOnboarding_Success() {
        Integer periodId = 1;

        // Truyền đầy đủ: ID, CCCD, SĐT SV, SĐT Phụ huynh, Email, Ngành, Giới tính
        AdmissionApplication app1 = createMockApp(1, "123456789012", "0901112223", "0988111222", "a@test.com", mockMajor, true);
        AdmissionApplication app2 = createMockApp(2, "987654321098", "0909998887", "0988333444", "b@test.com", mockMajor, false);

        when(admissionPeriodRepository.findByIdAndDeletedFalse(periodId)).thenReturn(Optional.of(mockPeriod));
        when(applicationRepository.findAllByAdmissionPeriodIdAndStatusAndDeletedFalse(periodId, ApplicationStatus.APPROVED))
                .thenReturn(Arrays.asList(app1, app2));

        when(studentRepository.findNationalIdsInAndDeletedFalse(any())).thenReturn(Collections.emptySet());
        when(studentRepository.findEmailsInAndDeletedFalse(any())).thenReturn(Collections.emptySet());

        when(cohortRepository.findByCohortNameAndDeletedFalse(anyString())).thenReturn(Optional.of(mockCohort));
        when(roleRepository.findByRoleNameAndDeletedFalse("LECTURER")).thenReturn(Optional.of(mockLecturerRole));
        when(roleRepository.findByRoleNameAndDeletedFalse("STUDENT")).thenReturn(Optional.of(mockStudentRole));
        when(roleRepository.findByRoleNameAndDeletedFalse("GUARDIAN")).thenReturn(Optional.of(mockGuardianRole));
        when(lecturerRepository.findByEmailAndDeletedFalse(anyString())).thenReturn(Optional.of(mockLecturer));
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_pass");

        // Thực thi
        onboardingService.processOnboarding(periodId);

        // Verify
        verify(classRepository).saveAll(classListCaptor.capture());
        verify(studentRepository).saveAll(studentListCaptor.capture());
        verify(guardianRepository).saveAll(guardianListCaptor.capture());

        List<AdministrativeClass> savedClasses = classListCaptor.getValue();
        List<Student> savedStudents = studentListCaptor.getValue();
        List<Guardian> savedGuardians = guardianListCaptor.getValue();

        assertEquals(1, savedClasses.size());
        assertEquals("IT-K26-01", savedClasses.get(0).getClassName());

        assertEquals(2, savedStudents.size());
        assertEquals(2, savedGuardians.size());
        assertEquals(ApplicationStatus.ENROLLED, app1.getStatus());
    }

    // =========================================================================
    // TEST CASE 2: Lọc trùng lặp sinh viên (Tất cả đã tồn tại)
    // =========================================================================
    @Test
    void testProcessOnboarding_AllStudentsExist_ShouldSkip() {
        Integer periodId = 1;
        AdmissionApplication app1 = createMockApp(1, "123456789012", "0901112223", "0988111222", "a@test.com", mockMajor, true);

        when(admissionPeriodRepository.findByIdAndDeletedFalse(periodId)).thenReturn(Optional.of(mockPeriod));
        when(applicationRepository.findAllByAdmissionPeriodIdAndStatusAndDeletedFalse(periodId, ApplicationStatus.APPROVED))
                .thenReturn(Collections.singletonList(app1));

        // Mock trùng CCCD
        when(studentRepository.findNationalIdsInAndDeletedFalse(any())).thenReturn(Set.of("123456789012"));
        when(studentRepository.findEmailsInAndDeletedFalse(any())).thenReturn(Collections.emptySet());

        onboardingService.processOnboarding(periodId);

        // Đảm bảo không có lệnh Save nào chạy
        verify(classRepository, never()).saveAll(any());
        verify(studentRepository, never()).saveAll(any());
        verify(accountRepository, never()).saveAll(any());
    }

    // =========================================================================
    // TEST CASE 3: Gộp trùng lặp Phụ Huynh (Siblings - Anh/Chị em ruột)
    // =========================================================================
    @Test
    void testProcessOnboarding_DuplicateGuardians_ShouldShareProfile() {
        Integer periodId = 1;

        // Hai hồ sơ có CÙNG MỘT SỐ ĐIỆN THOẠI PHỤ HUYNH: "0999999999"
        AdmissionApplication app1 = createMockApp(1, "111", "090111", "0999999999", "a@t.com", mockMajor, true);
        AdmissionApplication app2 = createMockApp(2, "222", "090222", "0999999999", "b@t.com", mockMajor, false);

        when(admissionPeriodRepository.findByIdAndDeletedFalse(periodId)).thenReturn(Optional.of(mockPeriod));
        when(applicationRepository.findAllByAdmissionPeriodIdAndStatusAndDeletedFalse(periodId, ApplicationStatus.APPROVED))
                .thenReturn(Arrays.asList(app1, app2));

        when(studentRepository.findNationalIdsInAndDeletedFalse(any())).thenReturn(Collections.emptySet());
        when(studentRepository.findEmailsInAndDeletedFalse(any())).thenReturn(Collections.emptySet());

        when(cohortRepository.findByCohortNameAndDeletedFalse(anyString())).thenReturn(Optional.of(mockCohort));
        when(roleRepository.findByRoleNameAndDeletedFalse("LECTURER")).thenReturn(Optional.of(mockLecturerRole));
        when(roleRepository.findByRoleNameAndDeletedFalse("STUDENT")).thenReturn(Optional.of(mockStudentRole));
        when(roleRepository.findByRoleNameAndDeletedFalse("GUARDIAN")).thenReturn(Optional.of(mockGuardianRole));
        when(lecturerRepository.findByEmailAndDeletedFalse(anyString())).thenReturn(Optional.of(mockLecturer));
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_pass");

        onboardingService.processOnboarding(periodId);

        verify(guardianRepository).saveAll(guardianListCaptor.capture());
        verify(studentRepository).saveAll(studentListCaptor.capture());

        List<Guardian> savedGuardians = guardianListCaptor.getValue();
        List<Student> savedStudents = studentListCaptor.getValue();

        assertEquals(2, savedStudents.size());

        // Chỉ tạo 1 Guardian duy nhất do trùng số điện thoại phụ huynh
        assertEquals(1, savedGuardians.size());

        // Cả 2 sinh viên đều trỏ về chung 1 Object Guardian
        assertSame(savedStudents.get(0).getGuardian(), savedStudents.get(1).getGuardian());
    }

    // =========================================================================
    // HÀM TRỢ GIÚP
    // =========================================================================
    private AdmissionApplication createMockApp(Integer id, String nid, String studentPhone, String guardianPhone, String email, Major major, Boolean gender) {
        AdmissionApplication app = new AdmissionApplication();
        app.setId(id);
        app.setFullName("Test Thí Sinh " + id);
        app.setNationalId(nid);
        app.setPhone(studentPhone);
        app.setGuardianPhone(guardianPhone); // Thêm sđt phụ huynh
        app.setEmail(email);
        app.setGender(gender); // Thêm giới tính
        app.setDateOfBirth(LocalDate.of(2008, 1, 1));
        app.setMajor(major);
        app.setStatus(ApplicationStatus.APPROVED);
        return app;
    }
}