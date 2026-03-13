package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.reponse.LecturerResponse;
import com.G5C.EduMS.model.Lecturer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LecturerMapper {

    LecturerResponse toResponse(Lecturer lecturer);
}
