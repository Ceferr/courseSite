package com.courseSite.dao;

import com.courseSite.pojo.Student;

public interface StudentDao extends BaseDao<Student,Long>{
    Student getByStudentID(Long studentID);

    void deleteByStudentID(Long studentID);
}
