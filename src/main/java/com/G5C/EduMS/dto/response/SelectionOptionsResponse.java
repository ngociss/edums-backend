package com.G5C.EduMS.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

// Trong thư mục dto.response
@Data
@Builder
public class SelectionOptionsResponse {
    private List<Map<String, Object>> majors; // id, name, code
    private List<Map<String, Object>> blocks; // id, name
    private List<Map<String, Object>> periods; // id, name
}