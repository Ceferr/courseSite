package com.courseSite.dao;

import com.courseSite.pojo.Teacher;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TeacherDaoImpl extends BaseDaoImpl<Teacher> implements TeacherDao {

    @Override
    public Teacher getByTeacherID(Long teacherID) {
        String hql = "From Teacher where teacherID=:id";
        Map map = new HashMap();
        map.put("id",teacherID);
        Query query = getQuery(hql,map);
        Teacher teacher = (Teacher)query.uniqueResult();
        if (teacher == null) return null;
        else return teacher;
    }
}
