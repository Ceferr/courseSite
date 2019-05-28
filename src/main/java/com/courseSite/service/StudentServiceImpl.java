package com.courseSite.service;

import com.courseSite.ResponseResult.Result;
import com.courseSite.dao.*;
import com.courseSite.pojo.HomeWork;
import com.courseSite.pojo.Report;
import com.courseSite.pojo.Student;
import com.courseSite.pojo.Teacher;
import com.courseSite.util.ReadExcel;
import com.courseSite.util.WriteExcel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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
        //Student student = new Student(studentID,name,password,sex,teacherID);
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
        List<Student> students = studentDaoImpl.findAll("studentID");
        Long count = studentDaoImpl.getCount();
        result.setOK(count.toString(),students);
        return result;
    }

    @Override
    public Result getAllStudentByPage(Integer start, Integer size) {
        result.clear();
        List<Student> students = studentDaoImpl.findAllByPage("studentID",start,size);
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
            if (homeWork_publish_downloadDaoImpl.get(studentID,"studentID")!=null) homeWork_publish_downloadDaoImpl.deleteRecord(studentID);
            if (report_publish_downloadDaoImpl.get(studentID,"studentID")!=null) report_publish_downloadDaoImpl.deleteRecord(studentID);
            if (courseWare_downloadDaoImpl.get(studentID,"studentID")!=null) courseWare_downloadDaoImpl.deleteRecord(studentID);
            if (homeWork_uploadDaoImpl.get(studentID,"studentID")!=null) homeWork_uploadDaoImpl.deleteRecord(studentID);
            if (report_uploadDaoImpl.get(studentID,"studentID")!=null)report_uploadDaoImpl.deleteRecord(studentID);
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

    @Override
    public Result updateInfo(Long studentID,String name, String sex) {
        result.clear();
        studentDaoImpl.updateInfo(studentID,name,sex);
        result.setOK("信息修改成功",name+" "+sex);
        return result;
    }

    @Override
    public Result importFromExcel(MultipartFile file) {
        result.clear();
        ReadExcel readExcel = new ReadExcel();
        if (file==null) {
            result.setFail(150,"文件不存在");
        }
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        long size = file.getSize();
        if (fileName == null||fileName.equals("")&&size==0){
            result.setFail(151,"文件为空");
        }
        List<Student> students = readExcel.getExcelInfo(fileName,file);
        if (students!=null){
            for (Student student : students){
                Teacher teacher = teacherDaoImpl.getByTeacherID(student.getTeacherID());
                Student querystudent = studentDaoImpl.getByStudentID(student.getStudentID());
                if (student.getStudentID() == null){
                    result.setFail(101,"未填写学号");
                    return result;
                } else if (teacher == null) {
                    result.setFail(107,"教师号"+student.getTeacherID()+"不存在");
                    return result;
                } else if(querystudent != null){
                    result.setFail(108,"学号"+student.getStudentID()+"已存在");
                    return result;
                } else {
                    studentDaoImpl.save(student);
                    result.setOK("导入成功",students);
                }
            }
        }else {
            result.setFail(152,"导入失败");
        }
        return result;
    }

    @Override
    public Result exportToExcel(Long teacherID, OutputStream outputStream) {
        result.clear();
        List<String> title = new ArrayList<>();
        title.add("学号");
        title.add("姓名");
        title.add("性别");
        title.add("教师号");

        List<Student> students = studentDaoImpl.getAllStudentByTeacherID(teacherID);
        result.setOK("导出成功",students);
        String sheetName = "学生表";

        HSSFWorkbook workbook = WriteExcel.getWorkBook(sheetName,title,students);

        try {
            OutputStream os = outputStream;
            workbook.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
