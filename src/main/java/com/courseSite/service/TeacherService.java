package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import com.courseSite.pojo.Teacher;

public interface TeacherService {
    Result addTeacher(Teacher teacher);

    Result deleteByTeacherID(Long teacherID);

    Result getByTeacherID(Long teacherID);

    Result updatePassword(Long teacherID,String originalPassword,String password);

    Result updateInfo(Long teacherID,String name,String sex);
}
