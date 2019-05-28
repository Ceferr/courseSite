package com.courseSite.dao;

import com.courseSite.pojo.Teacher;

public interface TeacherDao extends BaseDao<Teacher> {
    Teacher getByTeacherID(Long teacherID);

    void updatePassword(Long teacherID, String password);

    void updateInfo(Long teacherID, String name, String sex);
}
