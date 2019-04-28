package com.courseSite.dao;

import com.courseSite.pojo.Teacher;

public interface TeacherDao extends BaseDao<Teacher,Long> {
    Teacher getByTeacherID(Long teacherID);
}
