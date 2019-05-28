package com.courseSite.dao;

import com.courseSite.pojo.Student;

import java.util.List;

public interface StudentDao extends BaseDao<Student>{
    Student getByStudentID(Long studentID);

    void deleteByStudentID(Long studentID);

    void updatePassword(Long studentID,String password);

    void updateInfo(Long studentID,String name,String sex);

    List<Student> getAllStudentByTeacherID(Long teacherID);
}
