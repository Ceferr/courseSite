package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import com.courseSite.dao.*;
import com.courseSite.pojo.HomeWork;
import com.courseSite.pojo.Report;
import com.courseSite.pojo.Student;
import com.courseSite.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Autowired
    private HomeWork_publish_downloadDao homeWork_publish_downloadDaoImpl;
    @Autowired
    private Report_publish_downloadDao report_publish_downloadDaoImpl;
    @Autowired
    private CourseWare_downloadDao courseWare_downloadDaoImpl;
    @Autowired
    private HomeWork_uploadDao homeWork_uploadDaoImpl;
    @Autowired
    private Report_uploadDao report_uploadDaoImpl;

    private Result result = new Result();

    @Override
    public Result addStudent(Long studentID,String name,String password,String sex,Long teacherID) {
        result.clear();
        Teacher teacher = teacherDaoImpl.getByTeacherID(teacherID);
        Student querystudent = studentDaoImpl.getByStudentID(studentID);
        if (studentID == null){
            result.setFail(101,"未填写学号");
        } else if (teacher == null) {
            result.setFail(102,"该教师不存在");
        } else if(querystudent != null){
            result.setFail(103,"该学号已存在");
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
            result.setFail(104,"该学号不存在");
        }else {
            result.setOK("查询成功",student);
        }
        return result;
    }

    @Override
    public Result getAllStudent() {
        result.clear();
        List<Student> students = studentDaoImpl.findAll();
        Long count = studentDaoImpl.getCount();
        result.setOK(count.toString(),students);
        return result;
    }

    @Override
    public Result getAllStudentByPage(Integer start, Integer size) {
        result.clear();
        List<Student> students = studentDaoImpl.findAllByPage(start,size);
        Long count = studentDaoImpl.getCount();
        result.setOK(count.toString(),students);
        return result;
    }

    @Override
    public Result deleteByStudentID(Long studentID) {
        result.clear();
        System.out.println(studentID);
        Student student = studentDaoImpl.getByStudentID(studentID);
        System.out.println(student.toString());
        if (student == null){
            result.setFail(104,"该学号不存在");
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
            if (homeWork_publish_downloadDaoImpl.get(studentID,"studentID")!=null) homeWork_publish_downloadDaoImpl.delete(studentID);
            if (report_publish_downloadDaoImpl.get(studentID,"studentID")!=null) report_publish_downloadDaoImpl.delete(studentID);
            if (courseWare_downloadDaoImpl.get(studentID,"studentID")!=null) courseWare_downloadDaoImpl.delete(studentID);
            if (homeWork_uploadDaoImpl.get(studentID,"studentID")!=null) homeWork_uploadDaoImpl.delete(studentID);
            if (report_uploadDaoImpl.get(studentID,"studentID")!=null)report_uploadDaoImpl.delete(studentID);
            studentDaoImpl.deleteByStudentID(studentID);
            result.setOK("删除成功",student);
        }
        return result;
    }

    @Override
    public Result updatePassword(Long studentID, String originalPassword, String password) {
        result.clear();
        Student student = studentDaoImpl.getByStudentID(studentID);
        if (!originalPassword.equals(student.getPassword())){
            result.setFail(105,"原密码错误");
            return result;
        }else {
            studentDaoImpl.updatePassword(studentID,password);
            result.setOK("密码修改成功",password);
        }
        return result;
    }
}
