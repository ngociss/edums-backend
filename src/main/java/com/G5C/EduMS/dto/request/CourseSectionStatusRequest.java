package com.G5C.EduMS.dto.request;

import com.G5C.EduMS.common.enums.CourseSectionStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseSectionStatusRequest {

    private CourseSectionStatus status;

}
