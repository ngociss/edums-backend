package com.G5C.EduMS.validator;

import com.G5C.EduMS.common.enums.RegistrationPeriodStatus;
import com.G5C.EduMS.dto.request.RegistrationPeriodRequest;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.model.RegistrationPeriod;
import com.G5C.EduMS.repository.RegistrationPeriodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RegistrationPeriodValidator {

    private final RegistrationPeriodRepository registrationPeriodRepository;

    public void validateCreate(RegistrationPeriodRequest request) {
        validateTimeRange(request);
        validateSingleOpenPeriod(request.getStatus(), null);
        validateSinglePeriodPerSemester(request.getSemesterId(), null);
        validateOverlap(request.getSemesterId(), request.getStartTime(), request.getEndTime(), null);
    }

    public void validateUpdate(
            RegistrationPeriod existing,
            RegistrationPeriodRequest request,
            boolean hasRegistrations
    ) {
        validateTimeRange(request);
        validateSingleOpenPeriod(request.getStatus(), existing.getId());
        validateSinglePeriodPerSemester(request.getSemesterId(), existing.getId());
        validateOverlap(request.getSemesterId(), request.getStartTime(), request.getEndTime(), existing.getId());
        validateStatusTransition(existing.getStatus(), request.getStatus());
        validateFieldUpdates(existing, request, hasRegistrations);
    }

    private void validateSingleOpenPeriod(RegistrationPeriodStatus requestedStatus, Integer excludeId) {
        if (requestedStatus != RegistrationPeriodStatus.OPEN) {
            return;
        }

        boolean existsAnotherOpenPeriod = excludeId == null
                ? registrationPeriodRepository.existsByStatusAndDeletedFalse(RegistrationPeriodStatus.OPEN)
                : registrationPeriodRepository.existsByStatusAndIdNotAndDeletedFalse(RegistrationPeriodStatus.OPEN, excludeId);

        if (existsAnotherOpenPeriod) {
            throw new InvalidDataException("Chỉ được phép có 1 đợt đăng ký ở trạng thái OPEN");
        }
    }

    private void validateTimeRange(RegistrationPeriodRequest request) {
        if (request.getStartTime() != null
                && request.getEndTime() != null
                && !request.getStartTime().isBefore(request.getEndTime())) {
            throw new InvalidDataException("Thời gian bắt đầu phải trước thời gian kết thúc");
        }
    }

        private void validateSinglePeriodPerSemester(Integer semesterId, Integer excludeId) {
            boolean exists = excludeId == null
                    ? registrationPeriodRepository.existsBySemester_IdAndStatusAndDeletedFalse(semesterId, RegistrationPeriodStatus.OPEN)
                    : registrationPeriodRepository.existsBySemester_IdAndIdNotAndStatusAndDeletedFalse(semesterId, excludeId,RegistrationPeriodStatus.OPEN);

            if (exists) {
                throw new InvalidDataException("Mỗi học kỳ chỉ được có một đợt đăng ký môn học");
            }
        }

    private void validateOverlap(Integer semesterId, java.time.LocalDateTime startTime,
                                 java.time.LocalDateTime endTime, Integer excludeId) {
        boolean overlaps = registrationPeriodRepository.existsOverlappingPeriodExcludingId(
                semesterId,
                startTime,
                endTime,
                excludeId
        );

        if (overlaps) {
            throw new InvalidDataException("Thời gian đợt đăng ký bị chồng lấn trong cùng học kỳ");
        }
    }

    private void validateStatusTransition(RegistrationPeriodStatus currentStatus, RegistrationPeriodStatus requestedStatus) {

        try {RegistrationPeriodStatus.valueOf(requestedStatus.name());
        } catch (IllegalArgumentException e) {
            throw new InvalidDataException("Trạng thái không hợp lệ");
        }
        if (requestedStatus == null ||currentStatus == requestedStatus) {
            return;
        }

        switch (currentStatus) {
            case UPCOMING -> {
                if (requestedStatus != RegistrationPeriodStatus.OPEN
                        && requestedStatus != RegistrationPeriodStatus.CANCELLED) {
                    throw new InvalidDataException("Đợt đăng ký UPCOMING chỉ có thể chuyển sang OPEN hoặc CANCELLED");
                }
            }
            case OPEN -> {
                if (requestedStatus != RegistrationPeriodStatus.CLOSED
                        && requestedStatus != RegistrationPeriodStatus.CANCELLED) {
                    throw new InvalidDataException("Đợt đăng ký OPEN chỉ có thể chuyển sang CLOSED hoặc CANCELLED");
                }
            }
            case CLOSED, CANCELLED ->
                    throw new InvalidDataException("Không thể thay đổi trạng thái của đợt đăng ký đã đóng hoặc đã hủy");
        }
    }

    private void validateFieldUpdates(
            RegistrationPeriod existing,
            RegistrationPeriodRequest request,
            boolean hasRegistrations
    ) {
        boolean semesterChanged = !Objects.equals(existing.getSemester().getId(), request.getSemesterId());
        boolean startTimeChanged = !Objects.equals(existing.getStartTime(), request.getStartTime());
        boolean nameChanged = !Objects.equals(existing.getName(), request.getName());
        boolean endTimeChanged = !Objects.equals(existing.getEndTime(), request.getEndTime());

        if (existing.getStatus() == RegistrationPeriodStatus.UPCOMING) {
            if (semesterChanged && hasRegistrations) {
                throw new InvalidDataException("Không thể đổi học kỳ vì đợt đăng ký đã phát sinh dữ liệu đăng ký môn học");
            }
            return;
        }

        if (semesterChanged) {
            throw new InvalidDataException("Chỉ được thay đổi học kỳ khi đợt đăng ký đang ở trạng thái UPCOMING");
        }

        if (existing.getStatus() == RegistrationPeriodStatus.OPEN) {
            if (startTimeChanged) {
                throw new InvalidDataException("Không được thay đổi thời gian bắt đầu khi đợt đăng ký đang OPEN");
            }
            return;
        }

        if (existing.getStatus() == RegistrationPeriodStatus.CLOSED
                || existing.getStatus() == RegistrationPeriodStatus.CANCELLED) {
            if (nameChanged || startTimeChanged || endTimeChanged) {
                throw new InvalidDataException("Không được chỉnh sửa thông tin của đợt đăng ký đã CLOSED hoặc CANCELLED");
            }
        }
    }
}
