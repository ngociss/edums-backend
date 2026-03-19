package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.response.LecturerResponse;
import com.G5C.EduMS.dto.response.StudentResponse;

public interface ProfileService {

    StudentResponse getStudentById(Integer id);

    LecturerResponse getLecturerById(Integer id);

}
