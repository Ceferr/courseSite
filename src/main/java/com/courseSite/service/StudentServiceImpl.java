package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import com.courseSite.dao.HomeWorkDao;
import com.courseSite.dao.ReportDao;
import com.courseSite.dao.StudentDao;
import com.courseSite.dao.TeacherDao;
import com.courseSite.pojo.HomeWork;
import com.courseSite.pojo.Report;
import com.courseSite.pojo.Student;
import com.courseSite.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDaoImpl;
    @Autowired
    private TeacherDao teacherDaoImpl;
    @Autowired
    private HomeWorkDao homeWorkDaoImpl;
    @Autowired
    private ReportDao reportDaoImpl;
    @Autowired
    private HomeWorkorReportService homeWorkorReportServiceImpl;

    private Result result = new Result();

    @Override
    public Result addStudent(Long studentID,String name,String password,String sex,Long teacherID) {
        result.clear();
        Teacher teacher = teacherDaoImpl.getByTeacherID(teacherID);
        Student querystudent = studentDaoImpl.getByStudentID(studentID);
        if (studentID == null){
            result.setFail(401,"参数不全");
        } else if (teacher == null) {
            result.setFail(400,"没有该老师");
        } else if(querystudent != null){
            result.setFail(135,"该学号已存在");
        } else{
            Student student1 = new Student(studentID, name, password, sex,teacherID);
            result.setOK("添加成功",student1);
            studentDaoImpl.save(student1);
        }
        return result;
    }

    @Override
    public Result getByStudentID(Long studentID) {
        result.clear();
        //List students = studentDaoImpl.findAll();
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
        System.out.println(studentID);
        Student student = studentDaoImpl.getByStudentID(studentID);
        System.out.println(student.toString());
        if (student == null){
            result.setFail(102,"该学号不存在");
        }else {
            //studentDaoImpl.delete(student);
            List<HomeWork> homeWorks = homeWorkDaoImpl.get(studentID,"studentID");
            if (homeWorks != null) {
                for (HomeWork homeWork:homeWorks){
                    homeWorkorReportServiceImpl.removefile(homeWork.getName(),homeWork.getPath().replaceAll("/"+homeWork.getName(),""),"homework");
                }
            }
            List<Report> reports = reportDaoImpl.get(studentID,"studentID");
            if (reports!= null) {
                for (Report report:reports){
                    homeWorkorReportServiceImpl.removefile(report.getName(),report.getPath().replaceAll("/"+report.getName(),""),"report");
                }
            }
            //studentDaoImpl.delete(student.getId());
            //studentDaoImpl.flush();
            studentDaoImpl.deleteByStudentID(studentID);
            result.setOK("删除成功",student);
        }
        return result;
    }
}
