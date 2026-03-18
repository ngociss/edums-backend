package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.reponse.LecturerResponse;
import com.G5C.EduMS.dto.reponse.StudentResponse;

public interface ProfileService {

    StudentResponse getStudentById(Integer id);

    LecturerResponse getLecturerById(Integer id);

}
