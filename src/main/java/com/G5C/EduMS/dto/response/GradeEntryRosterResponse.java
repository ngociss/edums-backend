package com.G5C.EduMS.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeEntryRosterResponse {

    private Integer sectionId;
    private String sectionCode;
    private String displayName;
    private Integer courseId;
    private String courseCode;
    private String courseName;
    private List<GradeEntryComponentResponse> components;
    private List<GradeEntryRosterRowResponse> rows;
}
