package com.G5C.EduMS.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassroomResponse {

    private Integer id;
    private String roomName;
    private Integer capacity;
    private String roomType;
}

