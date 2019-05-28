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

    @Override
    public void updatePassword(Long teacherID, String password) {
        String hql="update Teacher t set t.password=:password where t.teacherID=:teacherID";
        Map map = new HashMap();
        map.put("password",password);
        map.put("teacherID",teacherID);
        Query query = getQuery(hql,map);
        query.executeUpdate();
    }

    @Override
    public void updateInfo(Long teacherID, String name, String sex) {
        String hql="update Teacher t set t.name=:name, t.sex =:sex where t.teacherID=:teacherID";
        Map map = new HashMap();
        map.put("name",name);
        map.put("sex",sex);
        map.put("teacherID",teacherID);
        Query query = getQuery(hql,map);
        query.executeUpdate();
    }
}
