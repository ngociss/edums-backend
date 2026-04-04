package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.RegistrationPeriodStatus;
import com.G5C.EduMS.dto.request.RegistrationPeriodRequest;
import com.G5C.EduMS.dto.response.RegistrationPeriodResponse;
import com.G5C.EduMS.exception.CannotDeleteException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.RegistrationPeriodMapper;
import com.G5C.EduMS.model.RegistrationPeriod;
import com.G5C.EduMS.model.Semester;
import com.G5C.EduMS.repository.CourseRegistrationRepository;
import com.G5C.EduMS.repository.RegistrationPeriodRepository;
import com.G5C.EduMS.repository.SemesterRepository;
import com.G5C.EduMS.service.RegistrationPeriodService;
import com.G5C.EduMS.validator.RegistrationPeriodValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationPeriodServiceImpl implements RegistrationPeriodService {

    private final RegistrationPeriodRepository registrationPeriodRepository;
    private final SemesterRepository semesterRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final RegistrationPeriodMapper registrationPeriodMapper;
    private final RegistrationPeriodValidator registrationPeriodValidator;

    @Override
    public List<RegistrationPeriodResponse> getAll(Integer semesterId) {
        List<RegistrationPeriod> periods = semesterId == null
                ? registrationPeriodRepository.findAllByDeletedFalse()
                : registrationPeriodRepository.findAllBySemester_IdAndDeletedFalse(semesterId);

        return periods.stream()
                .map(registrationPeriodMapper::toResponse)
                .toList();
    }

    @Override
    public RegistrationPeriodResponse getById(Integer id) {
        return registrationPeriodMapper.toResponse(findOrThrow(id));
    }

    @Override
    @Transactional
    public RegistrationPeriodResponse create(RegistrationPeriodRequest request) {
        Semester semester = findSemesterOrThrow(request.getSemesterId());
        registrationPeriodValidator.validateCreate(request);

        RegistrationPeriod registrationPeriod = registrationPeriodMapper.toEntity(request);
        registrationPeriod.setSemester(semester);

        return registrationPeriodMapper.toResponse(registrationPeriodRepository.save(registrationPeriod));
    }

    @Override
    @Transactional
    public RegistrationPeriodResponse update(Integer id, RegistrationPeriodRequest request) {
        RegistrationPeriod registrationPeriod = findOrThrow(id);
        Semester semester = findSemesterOrThrow(request.getSemesterId());
        boolean hasRegistrations = courseRegistrationRepository.existsByRegistrationPeriod_IdAndDeletedFalse(id);
        registrationPeriodValidator.validateUpdate(registrationPeriod, request, hasRegistrations);

        registrationPeriodMapper.updateEntity(request, registrationPeriod);
        registrationPeriod.setSemester(semester);

        return registrationPeriodMapper.toResponse(registrationPeriodRepository.save(registrationPeriod));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        RegistrationPeriod registrationPeriod = findOrThrow(id);
        if (registrationPeriod.getStatus() != RegistrationPeriodStatus.UPCOMING) {
            throw new CannotDeleteException("Chỉ có thể xóa đợt đăng ký ở trạng thái sắp diễn ra");
        }

        if (courseRegistrationRepository.existsByRegistrationPeriod_IdAndDeletedFalse(id)) {
            throw new CannotDeleteException("Không thể xóa đợt đăng ký vì đã có đăng ký môn học liên quan");
        }

        registrationPeriod.setDeleted(true);
        registrationPeriodRepository.save(registrationPeriod);
    }

    private RegistrationPeriod findOrThrow(Integer id) {
        return registrationPeriodRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy đợt đăng ký với id: " + id));
    }

    private Semester findSemesterOrThrow(Integer semesterId) {
        return semesterRepository.findByIdAndDeletedFalse(semesterId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy học kỳ với id: " + semesterId));
    }
}
