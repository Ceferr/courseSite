package com.courseSite.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.courseSite.pojo.Student;

import java.util.HashMap;
import java.util.Map;

@Repository("studentDaoImpl")
public class StudentDaoImpl extends BaseDaoImpl<Student> implements StudentDao{

    @Override
    public Student getByStudentID(Long studentID) {
        String hql = "From Student where studentID = :studentID";
        Map map = new HashMap();
        map.put("studentID",studentID);
        Query query = getQuery(hql,map);
        Student student = (Student) query.uniqueResult();
        return student;
    }

    @Override
    public void deleteByStudentID(Long studentID) {
        String hql = "delete Student where studentID = :studentID";
        Map map = new HashMap();
        map.put("studentID",studentID);
        Query query = getQuery(hql,map);
        query.executeUpdate();
    }
}
