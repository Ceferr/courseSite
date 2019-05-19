package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import com.courseSite.pojo.Student;

import java.util.Map;

public interface StudentService {
    Result addStudent(Long studentID,String name,String password,String sex,Long teacherID);

    Result getByStudentID(Long studentID);

    Result deleteByStudentID(Long studentID);
}
