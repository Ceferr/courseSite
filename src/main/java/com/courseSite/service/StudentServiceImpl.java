package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import com.courseSite.dao.StudentDao;
import com.courseSite.dao.TeacherDao;
import com.courseSite.pojo.Student;
import com.courseSite.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDaoImpl;
    @Autowired
    private TeacherDao teacherDaoImpl;

    private Result result = new Result();

    @Override
    public Result addStudent(Student student) {
        result.clear();
        Teacher teacher = teacherDaoImpl.getByTeacherID(student.getTeacherID());
        Long studentID = student.getStudentID();
        Student querystudent = studentDaoImpl.getByStudentID(studentID);
        if (studentID == null){
            result.setFail(401,"参数不全");
        } else if (teacher == null) {
            result.setFail(400,"没有该老师");
        } else if(querystudent != null){
            result.setFail(135,"该学号已存在");
        } else{
            result.setOK("添加成功",student);
            studentDaoImpl.save(student);
        }
        return result;
    }

    @Override
    public Result getByStudentID(Long studentID) {
        result.clear();
        Student student = studentDaoImpl.getByStudentID(studentID);
        if (student == null){
            result.setFail(102,"该学号不存在");
        }else {
            result.setOK("查询成功",student);
        }
        return result;
    }

    @Override
    public Result deleteByStudentID(Long studentID) {
        result.clear();
        Student student = studentDaoImpl.getByStudentID(studentID);
        if (student == null){
            result.setFail(102,"该学号不存在");
        }else {
            studentDaoImpl.deleteByStudentID(studentID);
            result.setOK("删除成功",student);
        }
        return result;
    }
}
