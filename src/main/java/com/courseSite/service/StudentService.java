package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import com.courseSite.pojo.Student;

import java.util.Map;

public interface StudentService {
    Result addStudent(Student student);

    Result getByStudentID(Long studentID);

    Result deleteByStudentID(Long studentID);
}
